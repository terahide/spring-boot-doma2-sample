package com.sample.domain.service.arigato;

import com.sample.domain.dto.arigato.AdminSearchCondition;
import com.sample.domain.dto.arigato.Arigato;
import com.sample.domain.dto.arigato.Fav;
import com.sample.domain.dto.arigato.SearchCondition;
import com.sample.domain.dto.common.Page;
import com.sample.domain.dto.common.Pageable;
import com.sample.domain.dto.system.UploadFile;
import com.sample.domain.exception.NoDataFoundException;
import com.sample.domain.repository.arigato.ArigatoRepository;
import com.sample.domain.repository.system.UploadFileRepository;
import com.sample.domain.repository.users.UserRepository;
import com.sample.domain.service.BaseTransactionalService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ArigatoService extends BaseTransactionalService {

    @Autowired
    ArigatoRepository arigatoRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UploadFileRepository uploadFileRepository;

    public void say(Arigato arigato) {
        Assert.notNull(arigato,"arigato must not be null");
        arigatoRepository.create(arigato);
    }

    public void update(Arigato arigato) {
        Assert.notNull(arigato,"arigato must not be null");
        arigatoRepository.update(arigato);
    }

    public void delete(long mineId, long arigatoId) {
        Assert.isTrue(mineId != 0,"mineId must be zero");
        Assert.isTrue(arigatoId != 0,"arigatoId must be zero");
        try {
            val arigato = getMine(mineId, arigatoId);
            arigatoRepository.delete(arigato);
        }catch(NoDataFoundException e){
            //成功あつかい
        }

    }

    public void deleteImage(Long mineId, long arigatoId, long uploadFileId) {
        Assert.isTrue(mineId != 0,"mineId must be zero");
        Assert.isTrue(arigatoId != 0,"arigatoId must be zero");
        Assert.isTrue(uploadFileId != 0,"uploadFileId must be zero");

        //TODO ありがとの画像かどうかのバリデーション

        try{
            val uploadFile = uploadFileRepository.findById(uploadFileId);
            arigatoRepository.delete(arigatoId, uploadFile);
        }catch(NoDataFoundException e){
            //成功あつかい
        }
    }

    @Transactional(readOnly = true)
    public Page<Arigato> search(Pageable pageable, SearchCondition condition) {
        Assert.notNull(pageable,"pageable must be null");
        Assert.notNull(condition,"condition must be null");
        Assert.isTrue(condition.getMineId() != 0,"condition.mineId must be zero");

        val page = arigatoRepository.findBy(pageable, condition);
        page.getData().stream().forEach(a -> populateMetaInfo(a, condition.getMineId()));
        return page;
    }

    @Transactional(readOnly = true)
    public Page<Arigato> search(Pageable pageable, AdminSearchCondition condition) {
        Assert.notNull(pageable,"pageable must be null");
        Assert.notNull(condition,"condition must be null");

        val page = arigatoRepository.findBy(pageable, condition);
        page.getData().stream().forEach(a -> populateMetaInfo(a));
        return page;
    }

    private void populateUser(Arigato a) {
        a.setFrom(userRepository.findById(a.getFromId()));
        a.setTo(userRepository.findById(a.getToId()));
    }

    private void populateImage(Arigato arigato) {
        val images = arigatoRepository.findImageByArigatoId(arigato.getId());
        images.stream().forEach(i -> arigato.getUploadFileId().add(i.getId()));
    }
    private void populateFav(Arigato arigato) {
        arigato.setFavCounts(arigatoRepository.countFav(arigato.getId()));
    }

    private void populateMine(Arigato arigato, long mineId) {
        arigato.setFav(arigatoRepository.findFavBy(arigato.getId(),mineId).isPresent());
        arigato.setFromMe(arigato.getFromId() == mineId);
        arigato.setToMe(arigato.getToId() == mineId);
    }

    @Transactional(readOnly = true)
    public UploadFile getImage(long uploadFileId) {
        return uploadFileRepository.findById(uploadFileId);
    }

    public void fav(long arigatoId, long userId) {
        val fav = new Fav();
        fav.setArigatoId(arigatoId);
        fav.setUserId(userId);
        arigatoRepository.create(fav);
    }

    public void releaseFav(long arigatoId, long userId) {
        val fav = arigatoRepository.findFavBy(arigatoId, userId);
        arigatoRepository.delete(fav.orElseThrow(() -> new NoDataFoundException(String.format("Favが見つかりませんでした。arigato_id=%d, user_id=%d", arigatoId, userId))));
    }

    public int countFav(long arigatoId) {
        return arigatoRepository.countFav(arigatoId);
    }

    @Transactional(readOnly = true)
    public Arigato getMine(long mineId, long arigatoId) {
        val arigato = arigatoRepository.findById(arigatoId);
        if( arigato.getFromId() != mineId ){
            //TODO 403
        }
        populateMetaInfo(arigato, mineId);
        return arigato;
    }

    private void populateMetaInfo(Arigato arigato, long mineId){
        populateMetaInfo(arigato);
        populateMine(arigato, mineId);
    }

    private void populateMetaInfo(Arigato arigato) {
        populateUser(arigato);
        populateImage(arigato);
        populateFav(arigato);
    }
}
