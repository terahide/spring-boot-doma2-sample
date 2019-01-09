package com.sample.domain.dao.arigato;

import com.sample.domain.dto.arigato.ArigatoImage;
import com.sample.domain.dto.system.UploadFile;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;
import java.util.Optional;

@ConfigAutowireable
@Dao
public interface ArigatoImageDao {
    @Select
    List<UploadFile> findByArigatoId(long arigatoId);

    @Insert
    int insert(ArigatoImage arigatoImage);

    @Delete(sqlFile = true)
    int delete(long arigatoId, long uploadFileId);

    @Select
    Optional<UploadFile> findById(long id);
}
