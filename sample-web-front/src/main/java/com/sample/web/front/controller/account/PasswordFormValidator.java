package com.sample.web.front.controller.account;

import com.sample.domain.dao.users.UserDao;
import com.sample.domain.dto.user.User;
import com.sample.domain.dto.user.UserCriteria;
import com.sample.domain.exception.NoDataFoundException;
import com.sample.domain.service.users.UserService;
import com.sample.domain.validator.AbstractValidator;
import com.sample.web.base.util.WebSecurityUtils;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static com.sample.common.util.ValidateUtils.isNotEquals;

@Component
public class PasswordFormValidator  extends AbstractValidator<PasswordForm> {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void doValidate(PasswordForm form, Errors errors) {
        val me = findUserBy(WebSecurityUtils.getLoginId());

        //現在のパスワードと一致するか
        if( ! auth(me, form.password)){
            errors.rejectValue("password", "users.unmatchPassword");
            return;
        }


        // 確認用パスワードと突き合わせる
        if (isNotEquals(form.getPasswordNew(), form.getPasswordConfirm())) {
            errors.rejectValue("passwordNew", "users.unmatchPassword");
            errors.rejectValue("passwordConfirm", "users.unmatchPassword");
        }
    }

    private boolean auth(User me, String password) {
        return passwordEncoder.matches(password, me.getPassword());
    }

    private User findUserBy(String email) {//TODO DRYにしたい
        val criteria = new UserCriteria();
        criteria.setEmail(email);
        return userService.findById(criteria).orElseThrow(() -> new NoDataFoundException("ユーザが見つかりません。email:"+email));
    }
}
