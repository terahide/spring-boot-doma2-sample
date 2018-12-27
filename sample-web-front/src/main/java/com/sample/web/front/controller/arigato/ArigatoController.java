package com.sample.web.front.controller.arigato;

import com.sample.domain.dto.arigato.Arigato;
import com.sample.domain.dto.common.Pageable;
import com.sample.domain.dto.system.UploadFile;
import com.sample.domain.dto.user.User;
import com.sample.domain.dto.user.UserCriteria;
import com.sample.domain.exception.NoDataFoundException;
import com.sample.domain.service.arigato.ArigatoService;
import com.sample.domain.service.system.UploadFileService;
import com.sample.domain.service.users.UserService;
import com.sample.web.base.controller.html.AbstractHtmlController;
import com.sample.web.base.util.MultipartFileUtils;
import com.sample.web.base.util.WebSecurityUtils;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping(path = "/arigato")
@Slf4j
public class ArigatoController extends AbstractHtmlController {

    @Autowired
    UserService userService;

    @Autowired
    ArigatoService arigatoService;

    @Autowired
    UploadFileService uploadFileService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public String getFunctionName() {
        return "F_Arigato";
    }

    @GetMapping
    public String showForm(Model model){
        val criteria = new UserCriteria();
        val users = userService.findAll(criteria, Pageable.NO_LIMIT);
        model.addAttribute("users", users.getData());
        model.addAttribute("arigatoForm", new ArigatoForm());
        return "arigato/new";
    }

    @PostMapping
    public String save(@Validated @ModelAttribute("arigatoForm")  ArigatoForm form, BindingResult br, RedirectAttributes attributes){
        if(br.hasErrors()){
            setFlashAttributeErrors(attributes, br);
            return "redirect:/arigato";
        }

        String fromEmail = WebSecurityUtils.getLoginId();
        val arigato = modelMapper.map(form, Arigato.class);
        arigato.setFromId(findUserBy(fromEmail).getId());

        val image = form.getImage();
        if (image != null && !image.isEmpty()) {
            val uploadFile = new UploadFile();
            MultipartFileUtils.convert(image, uploadFile);
            arigato.getUploadFile().add(uploadFile);
        }

        arigatoService.say(arigato);

        return "redirect:/";
    }

    @GetMapping("/image/{uploadFileId}")
    public ResponseEntity<byte[]> image(@PathVariable long uploadFileId) {
        val file = uploadFileService.getUploadFile(uploadFileId);

        byte[] body = Base64.decodeBase64(file.getContent().toBase64());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(Optional.ofNullable(file.getContentType()).orElse(MediaType.IMAGE_JPEG_VALUE)));
        headers.add("content-disposition", "inline;filename=" + file.getFilename());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity(body, headers, HttpStatus.OK);
    }
}
