package com.sample.domain.dto.arigato;

import lombok.Getter;
import lombok.Setter;

public class SearchCondition {
    public enum Mode {ALL, FROM, TO};

    private Mode mode;
    private long userId;
    private long mineId;

    public SearchCondition(Mode mode, long userId, long mineId) {
        this.mode = mode;
        this.userId = userId;
        this.mineId = mineId;
    }

    public String getMode() {
        return mode.name();
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public long getUserId() {
        return userId;
    }

    public long getMineId() {
        return mineId;
    }
}
