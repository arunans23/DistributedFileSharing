package com.semicolon.ds.utils;

import com.semicolon.ds.comms.ChannelMessage;

import java.util.concurrent.BlockingQueue;

public abstract class AbstractRequestHandler implements AbstractMessageHandler {
    public abstract void handleRequest(BlockingQueue<ChannelMessage> chanelOut, ChannelMessage message);
}
