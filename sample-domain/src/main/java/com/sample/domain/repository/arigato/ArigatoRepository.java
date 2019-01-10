package com.sample.domain.repository.arigato;

import com.sample.domain.dao.arigato.ArigatoDao;
import com.sample.domain.dao.arigato.ArigatoImageDao;
import com.sample.domain.dao.arigato.FavDao;
import com.sample.domain.dao.system.UploadFileDao;
import com.sample.domain.dto.arigato.Arigato;
import com.sample.domain.dto.arigato.ArigatoImage;
import com.sample.domain.dto.arigato.Fav;
import com.sample.domain.dto.arigato.SearchCondition;
import com.sample.domain.dto.common.Page;
import com.sample.domain.dto.common.Pageable;
import com.sample.domain.dto.system.UploadFile;
import com.sample.domain.exception.NoDataFoundException;
import com.sample.domain.service.BaseRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sample.domain.util.DomaUtils.createSelectOptions;
import static java.util.stream.Collectors.toList;

@Repository
public class ArigatoRepository extends BaseRepository {

    @Autowired
    ArigatoDao arigatoDao;

    @Autowired
    UploadFileDao uploadFileDao;

    @Autowired
    ArigatoImageDao arigatoImageDao;

    @Autowired
    FavDao favDao;

    public void create(Arigato arigato) {
        arigatoDao.insert(arigato);
        arigato.getUploadFile().stream().forEach(u -> create(arigato.getId(), u));
    }

    public void update(Arigato arigato) {
        arigatoDao.update(arigato);
        arigato.getPrevUploadFileId().stream().forEach(i -> findImageById(i).ifPresent(u -> delete(arigato.getId(), u))); //TODO 複数イメージアップのときに直してね
        arigato.getUploadFile().stream().forEach(u -> create(arigato.getId(), u));
    }

    public void delete(Arigato arigato) {
        arigato.getPrevUploadFileId().stream().forEach(i -> findImageById(i).ifPresent(u -> delete(arigato.getId(), u))); //TODO 複数イメージアップのときに直してね
        arigatoDao.delete(arigato);
    }

    public Page<Arigato> findBy(Pageable pageable, SearchCondition condition) {
        val options = createSelectOptions(pageable).count();
        val data = arigatoDao.findBy(condition, options, toList());
        return pageFactory.create(data, pageable, options.getCount());
    }

    public Arigato findById(long arigatoId) {
        return arigatoDao.findById(arigatoId)
            .orElseThrow(() -> new NoDataFoundException(String.format("ありがとが見つかりませんでした id=%d", arigatoId)));
    }

    public void create(long arigatoId, UploadFile uploadFile) {
        uploadFileDao.insert(uploadFile);
        ArigatoImage image = new ArigatoImage();
        image.setArigatoId(arigatoId);
        image.setUploadFileId(uploadFile.getId());
        arigatoImageDao.insert(image);
    }

    public void delete(long arigatoId, UploadFile uploadFile) {
        //TODO delete fav
        arigatoImageDao.delete(arigatoId, uploadFile.getId());
        uploadFileDao.delete(uploadFile);
    }

    public List<UploadFile> findImageByArigatoId(long arigatoId) {
        return arigatoImageDao.findByArigatoId(arigatoId);
    }

    private Optional<UploadFile> findImageById(long id) {
        return arigatoImageDao.findById(id);
    }


    public void create(Fav fav) {
        favDao.insert(fav);
    }

    public int countFav(long arigatoId) {
        return favDao.countFav(arigatoId);
    }

    public void delete(Fav fav) {
        favDao.delete(fav);
    }

    public Optional<Fav> findFavBy(long arigatoId, long userId) {
        return favDao.findFavBy(arigatoId, userId);
    }

}
