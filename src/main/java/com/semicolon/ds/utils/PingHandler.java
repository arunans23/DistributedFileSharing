package com.semicolon.ds.utils;

import com.semicolon.ds.Constants;
import com.semicolon.ds.comms.ChannelMessage;
import com.semicolon.ds.core.RoutingTable;
import com.semicolon.ds.core.TimeoutManager;

import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class PingHandler implements AbstractRequestHandler, AbstractResponseHandler{

    private final Logger LOG = Logger.getLogger(PingHandler.class.getName());

    private BlockingQueue<ChannelMessage> channelOut;

    private RoutingTable routingTable;

    private static PingHandler pingHandler;

    private TimeoutManager timeoutManager;

    private PingHandler() {

    }

    public synchronized static PingHandler getInstance() {
        if (pingHandler == null){
            pingHandler = new PingHandler();
        }
        return pingHandler;
    }

    @Override
    public void sendRequest(ChannelMessage message) {
        try {
            channelOut.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleResponse(ChannelMessage message) {
        LOG.info("Received PING : " + message.getMessage()
                + " from: " + message.getAddress()
                + " port: " + message.getPort());

        StringTokenizer stringToken = new StringTokenizer(message.getMessage(), " ");

        String length = stringToken.nextToken();
        String keyword = stringToken.nextToken();
        String address = stringToken.nextToken().trim();
        int port = Integer.parseInt(stringToken.nextToken().trim());

        int result = this.routingTable.addNeighbour(address, port);

        if (result != 0){
            String payload = String.format(Constants.PONG_FORMAT,
                    this.routingTable.getAddress(), this.routingTable.getPort());

            String rawMessage = String.format(Constants.MSG_FORMAT, payload.length() + 5, payload);
            ChannelMessage outGoingMsg = new ChannelMessage(address,
                    port, rawMessage);

            try {
                this.channelOut.put(outGoingMsg);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.routingTable.print();
    }

    public void sendPing(String address, int port) {
        String payload = String.format(Constants.PING_FORMAT,
                this.routingTable.getAddress(),
                this.routingTable.getPort());
        String rawMessage = String.format(Constants.MSG_FORMAT, payload.length() + 5,payload);
        ChannelMessage message = new ChannelMessage(address, port,rawMessage);
        this.sendRequest(message);

    }


    @Override
    public void init(
            RoutingTable routingTable,
            BlockingQueue<ChannelMessage> channelOut,
            TimeoutManager timeoutManager) {
        this.routingTable = routingTable;
        this.channelOut = channelOut;
        this.timeoutManager = timeoutManager;
    }
}
