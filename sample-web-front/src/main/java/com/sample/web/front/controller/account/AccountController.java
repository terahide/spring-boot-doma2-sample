package com.sample.web.front.controller.account;

import com.sample.domain.dto.arigato.Arigato;
import com.sample.domain.dto.arigato.SearchCondition;
import com.sample.domain.dto.common.DefaultPageable;
import com.sample.domain.dto.common.Page;
import com.sample.domain.dto.common.Pageable;
import com.sample.domain.dto.system.UploadFile;
import com.sample.domain.service.arigato.ArigatoService;
import com.sample.domain.service.users.UserService;
import com.sample.web.base.controller.html.AbstractHtmlController;
import com.sample.web.base.util.MultipartFileUtils;
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
        Page<Arigato> page = arigatoService.search(Pageable.DEFAULT, condition);
        model.addAttribute("page_from", page);

        condition = new SearchCondition(SearchCondition.Mode.TO_ME, mine.getId());
        page = arigatoService.search(Pageable.DEFAULT, condition);//TODO Paging
        model.addAttribute("user", mine);
        model.addAttribute("page_to", page);
        return "account/index";
    }

    @GetMapping("/more/from/me/{p}")
    public String moreFromMe(@PathVariable int p, Model model) {
        return more(SearchCondition.Mode.FROM_ME, p, model);
    }

    @GetMapping("/more/to/me/{p}")
    public String moreToMe(@PathVariable int p, Model model) {
        return more(SearchCondition.Mode.TO_ME, p, model);
    }

    private String more(SearchCondition.Mode mode, int p, Model model) {
        val mine = findMe();
        SearchCondition condition = new SearchCondition(mode, mine.getId());
        Page<Arigato> page = arigatoService.search(page(p), condition);
        model.addAttribute("page", page);
        return "arigato/more";
    }

    @GetMapping("/profile")
    public String showProfile(@ModelAttribute("profileForm")ProfileForm form, Model model) {
        val mine = findMe();
        modelMapper.map(mine, form);
        model.addAttribute("user", mine);
        return "account/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Validated @ModelAttribute("profileForm")ProfileForm form, BindingResult br, RedirectAttributes attributes) {
        if(br.hasErrors()){
            setFlashAttributeErrors(attributes, br);
            return "redirect:/account/profile";
        }

        val mine = findMe();
        modelMapper.map(form, mine);

        val image = form.getUserImage();
        if (image != null && !image.isEmpty()) {
            val uploadFile = new UploadFile();
            MultipartFileUtils.convert(image, uploadFile);
            mine.setUploadFile(uploadFile);
        }

        userService.update(mine);

        return "redirect:/account";
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
    private Pageable page(int page){
        return new DefaultPageable(page, Pageable.DEFAULT.getPerpage());
    }
}
