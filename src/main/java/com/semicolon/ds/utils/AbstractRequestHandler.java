package com.semicolon.ds.utils;

import com.semicolon.ds.comms.ChannelMessage;

public interface AbstractRequestHandler extends AbstractMessageHandler {

    void sendRequest(ChannelMessage message);
}
