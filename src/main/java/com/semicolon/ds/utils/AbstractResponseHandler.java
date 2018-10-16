package com.semicolon.ds.utils;

import com.semicolon.ds.comms.ChannelMessage;

public interface AbstractResponseHandler extends AbstractMessageHandler {

    void handleResponse(ChannelMessage message);
}
