package com.sample.web.front.controller.api.v1.arigato;

import com.sample.web.base.controller.api.AbstractRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/arigato")
@Slf4j
public class ArigatoRestController extends AbstractRestController {
    @GetMapping("/{arigatoId}/fav")
    public ResponseEntity<FavResult> fav(@PathVariable long arigatoId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new FavResult(arigatoId, 0), headers, HttpStatus.OK);
    }

    @Override
    public String getFunctionName() {
        return "F_API_Arigato";
    }
}
