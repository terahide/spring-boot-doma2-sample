package com.sample.web.front.controller.account;

import com.sample.web.base.controller.html.BaseForm;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class PasswordForm extends BaseForm {
    private static final long serialVersionUID = 4832911209189109694L;

    @NotEmpty
    String password;
    @NotEmpty
    @Length(min=8)
    //TODO 半角の英文字と数字と大文字小文字をまぜる
    String passwordNew;
    @NotEmpty
    String passwordConfirm;

    @Override
    public Long getId() {
        return null;
    }
}
