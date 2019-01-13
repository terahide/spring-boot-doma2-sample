package com.sample.web.admin.controller.api.v1.arigato;

import com.sample.domain.exception.NoDataFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sample.domain.dto.user.User;
import com.sample.domain.service.arigato.ArigatoService;
import com.sample.domain.service.users.UserService;
import com.sample.web.base.controller.api.AbstractRestController;
import com.sample.web.base.util.WebSecurityUtils;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/arigato")
@Slf4j
public class ArigatoRestController extends AbstractRestController {
    @Autowired
    ArigatoService arigatoService;
    @Autowired
    UserService userService;

    @DeleteMapping("/{arigatoId}")
    public ResponseEntity<String> delete(@PathVariable long arigatoId){//TODO ホントは削除するのではなくて、「運営によって削除されました」と表示する方が正しい
        try {
            val arigato = arigatoService.get(arigatoId);
            arigatoService.delete(arigato.getFromId(), arigatoId);
        }catch(NoDataFoundException e){
            //成功
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>("", headers, HttpStatus.NO_CONTENT);
    }

    @Override
    public String getFunctionName() {
        return "A_API_Arigato";
    }
}
