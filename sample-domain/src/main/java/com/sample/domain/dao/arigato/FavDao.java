package com.sample.domain.dao.arigato;

import com.sample.domain.dto.arigato.Fav;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.Optional;

@ConfigAutowireable
@Dao
public interface FavDao {
    @Insert
    int insert(Fav fav);

    @Delete
    int delete(Fav fav);

    @Select
    int countFav(long arigatoId);

    @Select
    Optional<Fav> findFavBy(long arigatoId, long userId);
}
