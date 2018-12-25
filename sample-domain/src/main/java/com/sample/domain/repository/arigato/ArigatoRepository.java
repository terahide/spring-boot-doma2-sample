package com.sample.domain.repository.arigato;

import com.sample.domain.dao.arigato.ArigatoDao;
import com.sample.domain.dto.arigato.Arigato;
import com.sample.domain.service.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArigatoRepository extends BaseRepository {

    @Autowired
    ArigatoDao arigatoDao;

    public void create(Arigato arigato) {
        arigatoDao.insert(arigato);
    }
}
