package com.semicolon.ds.utils;

import com.semicolon.ds.Constants;
import com.semicolon.ds.comms.ChannelMessage;
import com.semicolon.ds.core.RoutingTable;
import com.sun.imageio.spi.RAFImageOutputStreamSpi;

import javax.security.auth.callback.CallbackHandler;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class PingPongMessageHandler implements AbstractRequestHandler {

    private BlockingQueue<ChannelMessage> channelOut;
    private RoutingTable routingTable;

    @Override
    public AbstractMessageHandler init(BlockingQueue<ChannelMessage> chanelOut, RoutingTable routingTable) {
        this.channelOut = chanelOut;
        this.routingTable = routingTable;
        return this;

    }

    @Override
    public void handleRequest(ChannelMessage message) {
    }

    public int sendPing(String address, int port) {
        if (routingTable.getCount() < Constants.MIN_NEIGHBOURS) {
            String payload = String.format(Constants.PING_FORMAT, address, port);
            String rawMessage = String.format(Constants.MSG_FORMAT,payload.length() + 5,payload);
            System.out.println(rawMessage);
            ChannelMessage message = new ChannelMessage(address, port,rawMessage);
            try {
                channelOut.put(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return routingTable.getCount();
    }


}
