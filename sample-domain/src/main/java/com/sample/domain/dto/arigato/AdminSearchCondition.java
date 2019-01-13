package com.sample.domain.dto.arigato;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class AdminSearchCondition {
    String subject;
    String body;
    String fromName;
    String toName;
    LocalDate dateFrom;
    LocalDate dateTo;

    private static final DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu/MM/dd");

    public void setDateFrom(String dateFrom) {
        if(dateFrom == null)return;
        this.dateFrom = LocalDate.parse(dateFrom, f);
    }

    public void setDateTo(String dateTo) {
        if(dateTo == null)return ;
        this.dateTo = LocalDate.parse(dateTo, f);
    }
}
