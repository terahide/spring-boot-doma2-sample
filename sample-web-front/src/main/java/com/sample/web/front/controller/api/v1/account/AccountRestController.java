package com.sample.web.front.controller.api.v1.account;

import com.sample.domain.service.users.UserService;
import com.sample.web.base.controller.api.AbstractRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@Slf4j
public class AccountRestController extends AbstractRestController {
    @Autowired
    UserService userService;

    @DeleteMapping("/image/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable long id){
        userService.deleteImage(findMe().getId(), id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>("", headers, HttpStatus.NO_CONTENT);
    }




    @Override
    public String getFunctionName() {
        return "F_API_ACCOUNT";
    }
}
