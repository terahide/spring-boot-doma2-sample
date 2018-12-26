package com.sample.web.front.controller.api.v1.arigato;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FavResult {
    long arigatoId;
    int favCounts;

    public FavResult(long arigatoId, int favCounts) {
        this.arigatoId = arigatoId;
        this.favCounts = favCounts;
    }
}
