package com.sample.web.front.controller.account;

import com.sample.domain.dto.arigato.SearchCondition;
import com.sample.domain.dto.system.UploadFile;
import com.sample.domain.service.users.UserService;
import com.sample.web.base.controller.html.AbstractHtmlController;
import com.sample.web.base.util.MultipartFileUtils;
import com.sample.web.front.controller.user.UsersArigatoHelper;
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
    UserService userService;

    @Autowired
    PasswordFormValidator passwordFormValidator;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsersArigatoHelper usersArigatoHelper;

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
        return usersArigatoHelper.index(mine, mine.getId(), "/account/more",  model);
    }

    @GetMapping("/more/from/me/{p}")
    public String moreFromMe(@PathVariable int p, Model model) {
        val mine = findMe();
        return usersArigatoHelper.more(mine, mine.getId(), SearchCondition.Mode.FROM, p, model);
    }

    @GetMapping("/more/to/me/{p}")
    public String moreToMe(@PathVariable int p, Model model) {
        val mine = findMe();
        return usersArigatoHelper.more(mine, mine.getId(), SearchCondition.Mode.TO, p, model);
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
}
