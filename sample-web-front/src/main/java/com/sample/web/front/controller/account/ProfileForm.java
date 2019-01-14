package com.sample.web.front.controller.account;

import com.sample.web.base.controller.html.BaseForm;
import com.sample.web.base.validator.annotation.ContentType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;


@Setter
@Getter
public class ProfileForm extends BaseForm {
    @NotEmpty
    String firstName;
    @NotEmpty
    String lastName;
    String tel;
    String dept;
    String info;

    @Override
    public Long getId() {
        return null;
    }

    // 添付ファイル
    @ContentType(allowed = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE })
    transient MultipartFile userImage; // serializableではないのでtransientにする
}

