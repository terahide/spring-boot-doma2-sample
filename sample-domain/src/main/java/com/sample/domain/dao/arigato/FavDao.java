package com.sample.domain.dao.arigato;

import com.sample.domain.dto.arigato.Fav;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

@ConfigAutowireable
@Dao
public interface FavDao {
    @Insert
    int insert(Fav fav);

    @Select
    int countFav(long arigatoId);
}
