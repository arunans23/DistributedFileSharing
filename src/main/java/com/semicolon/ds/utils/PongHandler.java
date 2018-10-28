package com.semicolon.ds.utils;

import com.semicolon.ds.comms.ChannelMessage;
import com.semicolon.ds.core.MessageBroker;
import com.semicolon.ds.core.RoutingTable;

import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class PongHandler implements AbstractRequestHandler, AbstractResponseHandler{

    private final Logger LOG = Logger.getLogger(PongHandler.class.getName());

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
    public void sendRequest(ChannelMessage message) {

    }

    @Override
    public void handleResponse(ChannelMessage message) {
        LOG.info("Received PONG : " + message.getMessage()
                + " from: " + message.getAddress()
                + " port: " + message.getPort());

        StringTokenizer stringToken = new StringTokenizer(message.getMessage(), " ");

        String length = stringToken.nextToken();
        String keyword = stringToken.nextToken();
        String address = stringToken.nextToken().trim();
        int port = Integer.parseInt(stringToken.nextToken().trim());

        this.routingTable.addNeighbour(address, port);

        this.routingTable.print();
    }

    @Override
    public void init(RoutingTable routingTable, BlockingQueue<ChannelMessage> channelOut) {
        this.routingTable = routingTable;
        this.channelOut = channelOut;
    }
}
