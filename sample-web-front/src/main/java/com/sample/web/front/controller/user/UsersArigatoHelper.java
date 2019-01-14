package com.sample.web.front.controller.user;

import com.sample.domain.dto.arigato.Arigato;
import com.sample.domain.dto.arigato.SearchCondition;
import com.sample.domain.dto.common.DefaultPageable;
import com.sample.domain.dto.common.Page;
import com.sample.domain.dto.common.Pageable;
import com.sample.domain.dto.user.User;
import com.sample.domain.service.arigato.ArigatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class UsersArigatoHelper {
    @Autowired
    ArigatoService arigatoService;

    public String index(User user, long mineId, String moreUrl, Model model) {
        SearchCondition condition = new SearchCondition(SearchCondition.Mode.FROM, user.getId(), mineId);
        Page<Arigato> page = arigatoService.search(Pageable.DEFAULT, condition);
        model.addAttribute("page_from", page);

        condition = new SearchCondition(SearchCondition.Mode.TO, user.getId(), mineId);
        page = arigatoService.search(Pageable.DEFAULT, condition);//TODO Paging
        model.addAttribute("user", user);
        model.addAttribute("isMyAccount", user.getId() == mineId);
        model.addAttribute("moreUrl", moreUrl);
        model.addAttribute("page_to", page);
        return "account/index";
    }
    public String more(User user, long mineId, SearchCondition.Mode mode, int p, Model model) {
        SearchCondition condition = new SearchCondition(mode, user.getId(), mineId);
        Page<Arigato> page = arigatoService.search(page(p), condition);
        model.addAttribute("page", page);
        return "arigato/more";
    }

    private Pageable page(int page){
        return new DefaultPageable(page, Pageable.DEFAULT.getPerpage());
    }
}
