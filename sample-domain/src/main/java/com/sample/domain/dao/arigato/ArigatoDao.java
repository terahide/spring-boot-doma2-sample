package com.sample.domain.dao.arigato;

import com.sample.domain.dto.arigato.Arigato;
import com.sample.domain.dto.arigato.SearchCondition;
import org.seasar.doma.*;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.Optional;
import java.util.stream.Collector;

@ConfigAutowireable
@Dao
public interface ArigatoDao {
    @Insert
    int insert(Arigato arigato);

    @Update
    int update(Arigato arigato);

    @Select(strategy = SelectType.COLLECT)
    <R> R findBy(final SearchCondition condition, final SelectOptions options, final Collector<Arigato, ?, R> collector);

    @Select
    Optional<Arigato> findById(long arigatoId);

}
