package com.statslookup.views;

public enum ListViewOption {
    DEFAULT(0),
    COMPACT(1);

    private final int value;

    ListViewOption(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }

}
