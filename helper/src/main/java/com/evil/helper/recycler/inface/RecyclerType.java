package com.evil.helper.recycler.inface;

public enum RecyclerType {
    NORMAL(0),
    EMPTY(1),
    LOADING(2),
    ERROR(2);

    int type;

    RecyclerType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
