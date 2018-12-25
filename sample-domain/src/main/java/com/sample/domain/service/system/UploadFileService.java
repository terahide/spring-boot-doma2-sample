package com.sample.domain.service.system;

import com.sample.domain.dto.system.UploadFile;
import com.sample.domain.repository.system.UploadFileRepository;
import com.sample.domain.service.BaseTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UploadFileService extends BaseTransactionalService {
    @Autowired
    UploadFileRepository uploadFileRepository;

    @Transactional(readOnly = true)
    public UploadFile getUploadFile(long id){
        return uploadFileRepository.findById(id);
    }
}
