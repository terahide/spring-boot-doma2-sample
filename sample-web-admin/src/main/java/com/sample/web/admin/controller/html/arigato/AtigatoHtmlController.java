package com.sample.web.admin.controller.html.arigato;

import com.sample.domain.dto.arigato.AdminSearchCondition;
import com.sample.domain.dto.arigato.SearchCondition;
import com.sample.domain.dto.common.Pageable;
import com.sample.domain.service.arigato.ArigatoService;
import com.sample.domain.service.system.UploadFileService;
import com.sample.web.base.controller.html.AbstractHtmlController;
import lombok.val;
import org.apache.commons.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/arigato")
public class AtigatoHtmlController extends AbstractHtmlController {

    @Autowired
    ArigatoService arigatoService;

    @Autowired
    UploadFileService uploadFileService;
    @GetMapping
    public String index(Model model){
        val condition = new AdminSearchCondition();
        val page = arigatoService.search(Pageable.DEFAULT, condition);
        model.addAttribute("page", page);
        return "modules/arigato/find";
    }

    @Override
    public String getFunctionName() {
        return "A_ARIGATO";
    @GetMapping("/{id}/image/{uploadFileId}")
    public ResponseEntity<byte[]> image(@PathVariable long id, @PathVariable long uploadFileId) {
        //TODO arigatoIdとぶつけて違ったら 404
        val file = uploadFileService.getUploadFile(uploadFileId);

        byte[] body = Base64.decodeBase64(file.getContent().toBase64());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(Optional.ofNullable(file.getContentType()).orElse(MediaType.IMAGE_JPEG_VALUE)));
        headers.add("content-disposition", "inline;filename=" + file.getFilename());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity(body, headers, HttpStatus.OK);
    }
}
