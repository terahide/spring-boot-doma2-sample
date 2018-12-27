package com.sample.domain.service.arigato;

import com.sample.domain.dto.arigato.Arigato;
import com.sample.domain.dto.arigato.Fav;
import com.sample.domain.dto.common.Page;
import com.sample.domain.dto.common.Pageable;
import com.sample.domain.dto.system.UploadFile;
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

    @Transactional(readOnly = true)
    public Page<Arigato> search(Pageable pageable) {
        val page = arigatoRepository.findBy(pageable);
        page.getData().stream().forEach(this::populateUser);
        page.getData().stream().forEach(this::populateImage);
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
}
