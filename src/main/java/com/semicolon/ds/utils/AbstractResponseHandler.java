package com.semicolon.ds.utils;

import com.semicolon.ds.comms.ChannelMessage;
import com.semicolon.ds.core.RoutingTable;

import java.util.concurrent.BlockingQueue;

public interface AbstractResponseHandler extends AbstractMessageHandler {

    void handleResponse(ChannelMessage message);
}
