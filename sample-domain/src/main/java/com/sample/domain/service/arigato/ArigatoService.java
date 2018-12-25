package com.sample.domain.service.arigato;

import com.sample.domain.dto.arigato.Arigato;
import com.sample.domain.repository.arigato.ArigatoRepository;
import com.sample.domain.service.BaseTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ArigatoService extends BaseTransactionalService {

    @Autowired
    ArigatoRepository arigatoRepository;

    public void say(Arigato arigato) {
        Assert.notNull(arigato,"arigato must not be null");
        arigatoRepository.create(arigato);
    }
}
