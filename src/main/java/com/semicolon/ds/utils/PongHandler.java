package com.semicolon.ds.utils;

import com.semicolon.ds.comms.ChannelMessage;
import com.semicolon.ds.core.MessageBroker;
import com.semicolon.ds.core.RoutingTable;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class PongHandler implements AbstractRequestHandler, AbstractResponseHandler{

    private final Logger LOG = Logger.getLogger(PongHandler.class.getName());

    private BlockingQueue<ChannelMessage> channelIn;
    private BlockingQueue<ChannelMessage> channelOut;

    private RoutingTable routingTable;

    private static PongHandler pongHandler;

    private PongHandler(){

    }

    public synchronized static PongHandler getInstance(){
        if (pongHandler == null){
            pongHandler = new PongHandler();
        }

        return pongHandler;
    }

    @Override
    public void init(MessageBroker messageBroker) {
        if (this.channelIn == null || this.channelOut == null || this.routingTable == null) {
            this.channelIn = messageBroker.getChannelIn();
            this.channelOut = messageBroker.getChannelOut();
            this.routingTable = messageBroker.getRoutingTable();
        }
    }


    @Override
    public void handleRequest(ChannelMessage message) {

    }

    @Override
    public void handleResponse(ChannelMessage message) {
        LOG.info("Received PONG : " + message.getMessage()
                + " from: " + message.getAddress()
                + " port: " + message.getPort());

        this.routingTable.addNeighbour(message.getAddress(), message.getPort());
        this.routingTable.print();
    }

}
