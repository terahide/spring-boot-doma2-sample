package com.sample.web.front.controller.arigato;

import com.sample.domain.service.system.UploadFileService;
import com.sample.domain.service.users.UserService;
import lombok.val;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UploadFileService uploadFileService;

    @GetMapping("/image/{userId}")
    public ResponseEntity<byte[]> image(@PathVariable long userId) {
        val user = userService.findById(userId);
        val file = uploadFileService.getUploadFile(user.getUploadFileId());

        byte[] body = Base64.decodeBase64(file.getContent().toBase64());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(Optional.ofNullable(file.getContentType()).orElse(MediaType.IMAGE_JPEG_VALUE)));
        headers.add("content-disposition", "inline;filename=" + file.getFilename());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity(body, headers, HttpStatus.OK);
    }
}
