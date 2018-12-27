package com.sample.web.front.controller.home;

import com.sample.domain.dto.arigato.Arigato;
import com.sample.domain.dto.common.Page;
import com.sample.domain.dto.common.Pageable;
import com.sample.domain.service.arigato.ArigatoService;
import com.sample.web.base.util.WebSecurityUtils;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sample.web.base.controller.html.AbstractHtmlController;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Controller
@RequestMapping(path = { "/", "/home" })
@Slf4j
public class HomeController extends AbstractHtmlController {

    @Autowired
    ArigatoService arigatoService;

    @Override
    public String getFunctionName() {
        return "F_Home";
    }

    /**
     * 初期表示
     *
     * @return
     */
    @GetMapping
    public String index(Model model) {
        val mine = findUserBy(WebSecurityUtils.getLoginId());
        Page<Arigato> page = arigatoService.search(Pageable.NO_LIMIT, mine.getId());//TODO Fake It
        model.addAttribute("page", page);
        return "home/index";
    }
}
