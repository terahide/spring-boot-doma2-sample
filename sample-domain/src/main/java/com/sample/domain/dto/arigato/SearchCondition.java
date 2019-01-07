package com.sample.domain.dto.arigato;

import lombok.Getter;
import lombok.Setter;

public class SearchCondition {
    public enum Mode {ALL, FROM_ME, TO_ME};

    private Mode mode;
    private long mineId;

    public SearchCondition(Mode mode, long mineId) {
        this.mode = mode;
        this.mineId = mineId;
    }

    public String getMode() {
        return mode.name();
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public long getMineId() {
        return mineId;
    }
}
