package com.sample.domain.service.arigato;

import com.sample.domain.dto.arigato.Arigato;
import com.sample.domain.dto.common.Page;
import com.sample.domain.dto.common.Pageable;
import com.sample.domain.repository.arigato.ArigatoRepository;
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

    public void say(Arigato arigato) {
        Assert.notNull(arigato,"arigato must not be null");
        arigatoRepository.create(arigato);
    }

    @Transactional(readOnly = true)
    public Page<Arigato> search(Pageable pageable) {
        val page = arigatoRepository.findBy(pageable);
        page.getData().stream().forEach(this::populateUser);
        return page;
    }

    private void populateUser(Arigato a) {
        a.setFrom(userRepository.findById(a.getFromId()));
        a.setTo(userRepository.findById(a.getToId()));
    }
}
