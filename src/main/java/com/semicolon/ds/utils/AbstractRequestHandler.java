package com.semicolon.ds.utils;

import com.semicolon.ds.comms.ChannelMessage;
import com.semicolon.ds.core.RoutingTable;

import java.util.concurrent.BlockingQueue;

public interface AbstractRequestHandler extends AbstractMessageHandler {
    public AbstractMessageHandler init(BlockingQueue<ChannelMessage> chanelOut, RoutingTable routingTable);
    public void handleRequest(ChannelMessage message);
}
