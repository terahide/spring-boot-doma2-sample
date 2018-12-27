package com.sample.web.front.controller.api.v1.arigato;

import com.sample.domain.dto.user.User;
import com.sample.domain.dto.user.UserCriteria;
import com.sample.domain.exception.NoDataFoundException;
import com.sample.domain.service.arigato.ArigatoService;
import com.sample.domain.service.users.UserService;
import com.sample.web.base.controller.api.AbstractRestController;
import com.sample.web.base.util.WebSecurityUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/arigato")
@Slf4j
public class ArigatoRestController extends AbstractRestController {
    @Autowired
    ArigatoService arigatoService;
    @Autowired
    UserService userService;


    @PutMapping("/{arigatoId}/fav")
    public ResponseEntity<FavResult> fav(@PathVariable long arigatoId,
                                 @RequestParam(required = true) boolean check){
        String fromEmail = WebSecurityUtils.getLoginId();
        val mineUserId = findUserBy(fromEmail).getId();

        if(check) {
            arigatoService.fav(arigatoId, mineUserId);
        }else{
            arigatoService.releaseFav(arigatoId, mineUserId);
        }

        val favCounts = arigatoService.countFav(arigatoId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new FavResult(arigatoId, favCounts), headers, HttpStatus.OK);
    }

    private User findUserBy(String email) {
        val criteria = new UserCriteria();
        criteria.setEmail(email);
        return userService.findById(criteria).orElseThrow(() -> new NoDataFoundException("ユーザが見つかりません。email:"+email));
    }

    @Override
    public String getFunctionName() {
        return "F_API_Arigato";
    }
}
