package com.sample.web.front.controller.arigato;

import com.sample.web.base.controller.html.BaseForm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ArigatoForm extends BaseForm {
    private static final long serialVersionUID = 6867085450325771381L;

    @NotNull
    Long toId;
    @NotEmpty
    String subject;
    @NotEmpty
    String body;

    @Override
    public Long getId() {
        return null;
    }
}
