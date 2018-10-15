package com.semicolon.ds.utils;

import com.semicolon.ds.Constants;
import com.semicolon.ds.comms.ChannelMessage;
import com.semicolon.ds.core.MessageBroker;
import com.semicolon.ds.core.RoutingTable;
import com.sun.imageio.spi.RAFImageOutputStreamSpi;

import javax.security.auth.callback.CallbackHandler;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class PingHandler implements AbstractRequestHandler, AbstractResponseHandler{

    private final Logger LOG = Logger.getLogger(PingHandler.class.getName());

    private BlockingQueue<ChannelMessage> channelIn;
    private BlockingQueue<ChannelMessage> channelOut;

    private RoutingTable routingTable;

    private static PingHandler pingHandler;

    private PingHandler() {

    }

    public synchronized static PingHandler getInstance() {
        if (pingHandler == null){
            pingHandler = new PingHandler();
        }
        return pingHandler;
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
        LOG.info("Received PING : " + message.getMessage()
                + " from: " + message.getAddress()
                + " port: " + message.getPort());

        if (routingTable.getCount() < Constants.MAX_NEIGHBOURS) {
            this.routingTable.addNeighbour(message.getAddress(), message.getPort());

            String payload = String.format(Constants.PONG_FORMAT,
                    this.routingTable.getAddress(), this.routingTable.getPort());

            String rawMessage = String.format(Constants.MSG_FORMAT, payload.length() + 5,payload);
            ChannelMessage outGoingMsg = new ChannelMessage(message.getAddress(),
                    message.getPort(), rawMessage);

            try {
                this.channelOut.put(outGoingMsg);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.routingTable.print();
    }

    public int sendPing(String address, int port) {
        if (routingTable.getCount() < Constants.MIN_NEIGHBOURS) {
            String payload = String.format(Constants.PING_FORMAT, address, port);
            String rawMessage = String.format(Constants.MSG_FORMAT, payload.length() + 5,payload);
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
