package com.semicolon.ds.utils;

import com.semicolon.ds.comms.ChannelMessage;
import com.semicolon.ds.core.MessageBroker;
import com.semicolon.ds.core.RoutingTable;

import java.util.concurrent.BlockingQueue;

interface AbstractMessageHandler {
    void init (RoutingTable routingTable, BlockingQueue<ChannelMessage> channelOut);
}
