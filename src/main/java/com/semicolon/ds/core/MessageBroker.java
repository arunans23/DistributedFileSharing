package com.semicolon.ds.core;

import com.semicolon.ds.comms.ChannelMessage;
import com.semicolon.ds.comms.UDPClient;
import com.semicolon.ds.comms.UDPServer;
import com.semicolon.ds.utils.AbstractResponseHandler;
import com.semicolon.ds.utils.PingHandler;
import com.semicolon.ds.utils.ResponseHandlerFactory;

import java.net.DatagramSocket;
import java.net.SocketException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class MessageBroker extends Thread {

    private final Logger LOG = Logger.getLogger(MessageBroker.class.getName());

    private volatile boolean process = true;

    private final UDPServer server;
    private final UDPClient client;

    private BlockingQueue<ChannelMessage> channelIn;
    private BlockingQueue<ChannelMessage> channelOut;

    private RoutingTable routingTable;

    private PingHandler pingHandler;

    public MessageBroker(String address, int port) throws SocketException {
        channelIn = new LinkedBlockingQueue<ChannelMessage>();
        DatagramSocket socket = new DatagramSocket(port);
        this.server = new UDPServer(channelIn, socket);

        channelOut = new LinkedBlockingQueue<ChannelMessage>();
        this.client = new UDPClient(channelOut, new DatagramSocket());

        this.routingTable = new RoutingTable(address, port);

        this.pingHandler = PingHandler.getInstance();

        this.pingHandler.init(this.routingTable, this.channelOut);

        LOG.info("starting server");
    }

    @Override
    public void run(){
        this.server.start();
        this.client.start();
        this.process();
    }

    public void process() {
        while (process) {
            try {
                ChannelMessage message = channelIn.take();

                LOG.info("Received Message: " + message.getMessage()
                        + " from: " + message.getAddress()
                        + " port: " + message.getPort());

                AbstractResponseHandler abstractResponseHandler
                        = ResponseHandlerFactory.getResponseHandler(
                                message.getMessage().split(" ")[1],
                                this
                        );

                if (abstractResponseHandler != null){
                    abstractResponseHandler.handleResponse(message);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopProcessing() {
        this.process = false;
        server.stopProcessing();
    }

    public void sendPing(String address, int port) {
        this.pingHandler.sendPing(address, port);
    }

    public BlockingQueue<ChannelMessage> getChannelIn() {
        return channelIn;
    }

    public BlockingQueue<ChannelMessage> getChannelOut() {
        return channelOut;
    }

    public RoutingTable getRoutingTable() {
        return routingTable;
    }
}
