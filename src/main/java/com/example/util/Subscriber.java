package com.example.util;

public interface Subscriber {
    default int orderValue() {
        return 0;
    }
}
