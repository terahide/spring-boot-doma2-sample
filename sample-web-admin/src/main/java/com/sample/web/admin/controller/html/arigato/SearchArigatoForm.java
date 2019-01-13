package com.sample.web.admin.controller.html.arigato;

import com.sample.domain.dto.common.Pageable;
import com.sample.web.base.controller.html.BaseSearchForm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Setter
@Getter
public class SearchArigatoForm extends BaseSearchForm implements Pageable {
    String subject;
    String body;
    String fromName;
    String toName;
    @Pattern(regexp = "^(\\d{4})/(\\d{2})/(\\d{2})$")
    String dateFrom;
    @Pattern(regexp = "^(\\d{4})/(\\d{2})/(\\d{2})$")
    String dateTo;

    @Override
    public Long getId() {
        return null;
    }
}
