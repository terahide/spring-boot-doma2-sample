package com.sample.web.front.controller.account;

import com.sample.web.base.controller.html.BaseForm;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class PasswordForm extends BaseForm {
    private static final long serialVersionUID = 4832911209189109694L;

    @NotEmpty
    String password;
    @NotEmpty
    @Length(min=8, max=20)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9!#&\\\\@?\\-=\\*\\+]*$", message = "{validator.password.pattern.message}")
    String passwordNew;
    @NotEmpty
    @Length(min=8, max=20)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9!#&\\\\@?\\-=\\*\\+]*$", message = "{validator.password.pattern.message}")
    String passwordConfirm;

    @Override
    public Long getId() {
        return null;
    }
}
