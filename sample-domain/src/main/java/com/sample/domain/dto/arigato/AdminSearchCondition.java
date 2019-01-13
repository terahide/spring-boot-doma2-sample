package com.sample.domain.dto.arigato;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class AdminSearchCondition {
    String subject;
    String body;
    String fromName;
    String toName;
    LocalDate dateFrom;
    LocalDate dateTo;
}
