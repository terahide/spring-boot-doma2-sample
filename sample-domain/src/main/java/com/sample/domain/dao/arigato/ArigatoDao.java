package com.sample.domain.dao.arigato;

import com.sample.domain.dto.arigato.Arigato;
import org.seasar.doma.*;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.stream.Collector;

@ConfigAutowireable
@Dao
public interface ArigatoDao {
    @Insert
    int insert(Arigato arigato);

    @Select(strategy = SelectType.COLLECT)
    <R> R findBy(final SelectOptions options, final Collector<Arigato, ?, R> collector);
}
