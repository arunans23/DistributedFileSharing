package com.semicolon.ds.utils;

public interface TimeoutCallback {
    void onTimeout(String messageId);
    void onResponse(String messageId);
}
