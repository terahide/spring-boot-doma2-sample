package com.sample.web.admin.controller.html.arigato;

import com.sample.domain.dto.arigato.AdminSearchCondition;
import com.sample.domain.dto.arigato.SearchCondition;
import com.sample.domain.dto.common.Pageable;
import com.sample.domain.service.arigato.ArigatoService;
import com.sample.web.base.controller.html.AbstractHtmlController;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/arigato")
public class AtigatoHtmlController extends AbstractHtmlController {

    @Autowired
    ArigatoService arigatoService;

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
    }
}
