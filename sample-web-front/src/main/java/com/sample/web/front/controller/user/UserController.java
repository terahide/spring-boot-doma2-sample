package com.sample.web.front.controller.user;

import com.sample.domain.dto.arigato.SearchCondition;
import com.sample.domain.service.system.UploadFileService;
import com.sample.domain.service.users.UserService;
import com.sample.web.base.controller.html.AbstractHtmlController;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.codec.binary.Base64;
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

import java.util.Optional;

@Controller
@RequestMapping(path = "/user")
@Slf4j
public class UserController extends AbstractHtmlController {
    @Autowired
    UsersArigatoHelper usersArigatoHelper;

    @Autowired
    UserService userService;

    @Autowired
    UploadFileService uploadFileService;

    @GetMapping("/{id}")
    public String index(@PathVariable long id, Model model) {
        val myself = findMe();
        if(id == myself.getId()){
            return "redirect:/account";
        }
        val user = userService.findById(id);
        return usersArigatoHelper.index(user, myself.getId(), String.format("/user/%d/more", id), model);
    }

    @GetMapping("/{id}/more/from/me/{p}")
    public String moreFromMe(@PathVariable long id, @PathVariable int p, Model model) {
        val myself = findMe();
        val user = userService.findById(id);
        return usersArigatoHelper.more(user, myself.getId(), SearchCondition.Mode.FROM, p, model);
    }

    @GetMapping("/{id}/more/to/me/{p}")
    public String moreToMe(@PathVariable long id, @PathVariable int p, Model model) {
        val myself = findMe();
        val user = userService.findById(id);
        return usersArigatoHelper.more(user, myself.getId(), SearchCondition.Mode.TO, p, model);
    }

    @GetMapping("/{userId}/image")
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

    @Override
    public String getFunctionName() {
        return "F_USER";
    }
}
