package com.sample.domain.dao.arigato;

import com.sample.domain.dto.arigato.Arigato;
import org.seasar.doma.*;
import org.seasar.doma.boot.ConfigAutowireable;

@ConfigAutowireable
@Dao
public interface ArigatoDao {
    @Insert
    int insert(Arigato arigato);
}
