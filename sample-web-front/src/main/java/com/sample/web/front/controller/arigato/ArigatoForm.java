package com.sample.web.front.controller.arigato;

import com.sample.web.base.controller.html.BaseForm;
import com.sample.web.base.validator.annotation.ContentType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ArigatoForm extends BaseForm {
    private static final long serialVersionUID = 6867085450325771381L;

    Long id;

    String toName;
    @NotNull
    Long toId;
    @NotEmpty
    String subject;
    @NotEmpty
    String body;

    // 添付ファイル
    @ContentType(allowed = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE })
    transient MultipartFile image; // serializableではないのでtransientにする
}
