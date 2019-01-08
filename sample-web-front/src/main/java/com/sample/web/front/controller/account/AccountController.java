package com.sample.web.front.controller.account;

import com.sample.domain.dto.arigato.Arigato;
import com.sample.domain.dto.arigato.SearchCondition;
import com.sample.domain.dto.common.Page;
import com.sample.domain.dto.common.Pageable;
import com.sample.domain.service.arigato.ArigatoService;
import com.sample.domain.service.users.UserService;
import com.sample.web.base.controller.html.AbstractHtmlController;
import com.sample.web.base.util.WebSecurityUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.sample.web.base.WebConst.GLOBAL_MESSAGE;

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
    @Autowired
    UserService userService;

    @Autowired
    PasswordFormValidator passwordFormValidator;

    @Autowired
    PasswordEncoder passwordEncoder;

    @ModelAttribute("passwordForm")
    public PasswordForm passwordForm() {
        return new PasswordForm();
    }

    @InitBinder("passwordForm")
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(passwordFormValidator);
    }

    /**
     * 初期表示
     *
     * @return
     */
    @GetMapping
    public String index(Model model) {
        val mine = findMe();
        SearchCondition condition = new SearchCondition(SearchCondition.Mode.FROM_ME, mine.getId());
        Page<Arigato> page = arigatoService.search(Pageable.NO_LIMIT, condition);//TODO Paging
        model.addAttribute("page_from", page);

        condition = new SearchCondition(SearchCondition.Mode.TO_ME, mine.getId());
        page = arigatoService.search(Pageable.NO_LIMIT, condition);//TODO Paging
        model.addAttribute("page_to", page);
        return "account/index";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        //TODO 実装してね
        return "account/profile";
    }

    @GetMapping("/password")
    public String showPasswordForm(@ModelAttribute("passwordForm") PasswordForm form, Model model) {
        return "account/password";
    }

    @PostMapping("/password")
    public String changePassword(@Validated  @ModelAttribute("passwordForm") PasswordForm form, BindingResult br, RedirectAttributes attributes) {
        // 入力チェックエラーがある場合は、元の画面にもどる
        if (br.hasErrors()) {
            setFlashAttributeErrors(attributes, br);
            return "redirect:/account/password";
        }

        val me = findMe();
        me.setPassword(passwordEncoder.encode(form.getPasswordNew()));
        userService.update(me);

        attributes.addFlashAttribute(GLOBAL_MESSAGE, getMessage("password.changed"));
        return "redirect:/account";
    }
}
