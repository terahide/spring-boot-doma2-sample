package com.sample.domain.repository.system;

import com.sample.domain.dao.system.UploadFileDao;
import com.sample.domain.dto.system.UploadFile;
import com.sample.domain.exception.NoDataFoundException;
import com.sample.domain.service.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UploadFileRepository extends BaseRepository {
    @Autowired
    UploadFileDao uploadFileDao;

    public UploadFile findById(long id){
        return uploadFileDao.selectById(id).orElseThrow(() -> new NoDataFoundException("UploadFile が見つかりませんでした id:" + id));
    }
}
