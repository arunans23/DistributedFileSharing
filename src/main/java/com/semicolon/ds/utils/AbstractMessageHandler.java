package com.semicolon.ds.utils;

import com.semicolon.ds.core.MessageBroker;

interface AbstractMessageHandler {
    void init (MessageBroker messageBroker);
}
