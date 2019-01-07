package com.sample.web.front.controller.account;

import com.sample.domain.dto.arigato.Arigato;
import com.sample.domain.dto.arigato.SearchCondition;
import com.sample.domain.dto.common.Page;
import com.sample.domain.dto.common.Pageable;
import com.sample.domain.service.arigato.ArigatoService;
import com.sample.web.base.controller.html.AbstractHtmlController;
import com.sample.web.base.util.WebSecurityUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/account")
@Slf4j
public class AccountController extends AbstractHtmlController {
    @Override
    public String getFunctionName() {
        return "F_ACCOUNT";
    }
    @Autowired
    ArigatoService arigatoService;

    /**
     * 初期表示
     *
     * @return
     */
    @GetMapping
    public String index(Model model) {
        val mine = findUserBy(WebSecurityUtils.getLoginId());
        SearchCondition condition = new SearchCondition(SearchCondition.Mode.FROM_ME, mine.getId());
        Page<Arigato> page = arigatoService.search(Pageable.NO_LIMIT, condition);//TODO Paging
        model.addAttribute("page_from", page);

        condition = new SearchCondition(SearchCondition.Mode.TO_ME, mine.getId());
        page = arigatoService.search(Pageable.NO_LIMIT, condition);//TODO Paging
        model.addAttribute("page_to", page);
        return "account/index";
    }
}
