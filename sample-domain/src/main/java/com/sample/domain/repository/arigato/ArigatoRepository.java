package com.sample.domain.repository.arigato;

import com.sample.domain.dao.arigato.ArigatoDao;
import com.sample.domain.dto.arigato.Arigato;
import com.sample.domain.dto.common.Page;
import com.sample.domain.dto.common.Pageable;
import com.sample.domain.service.BaseRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.sample.domain.util.DomaUtils.createSelectOptions;
import static java.util.stream.Collectors.toList;

@Repository
public class ArigatoRepository extends BaseRepository {

    @Autowired
    ArigatoDao arigatoDao;

    public void create(Arigato arigato) {
        arigatoDao.insert(arigato);
    }

    public Page<Arigato> findBy(Pageable pageable) {
        val options = createSelectOptions(pageable).count();
        val data = arigatoDao.findBy(options, toList());
        return pageFactory.create(data, pageable, options.getCount());
    }
}
