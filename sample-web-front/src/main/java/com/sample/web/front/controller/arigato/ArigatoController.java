package com.sample.web.front.controller.arigato;

import com.sample.domain.dto.arigato.Arigato;
import com.sample.domain.dto.common.Pageable;
import com.sample.domain.dto.user.User;
import com.sample.domain.dto.user.UserCriteria;
import com.sample.domain.exception.NoDataFoundException;
import com.sample.domain.service.arigato.ArigatoService;
import com.sample.domain.service.users.UserService;
import com.sample.web.base.controller.html.AbstractHtmlController;
import com.sample.web.base.util.WebSecurityUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/arigato")
@Slf4j
public class ArigatoController extends AbstractHtmlController {

    @Autowired
    UserService userService;

    @Autowired
    ArigatoService arigatoService;

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

        arigatoService.say(arigato);

        return "redirect:/";
    }

    private User findUserBy(String email) {
        val criteria = new UserCriteria();
        criteria.setEmail(email);
        return userService.findById(criteria).orElseThrow(() -> new NoDataFoundException("ユーザが見つかりません。email:"+email));
    }
}
