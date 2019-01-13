package com.sample.web.admin.controller.html.arigato;

import com.sample.domain.dto.common.Pageable;
import com.sample.web.base.controller.html.BaseSearchForm;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class SearchArigatoForm extends BaseSearchForm implements Pageable {
    @Override
    public Long getId() {
        return null;
    }
}
