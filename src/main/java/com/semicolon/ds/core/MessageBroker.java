package com.semicolon.ds.core;

import com.semicolon.ds.Constants;
import com.semicolon.ds.comms.ChannelMessage;
import com.semicolon.ds.comms.UDPClient;
import com.semicolon.ds.comms.UDPServer;
import com.semicolon.ds.utils.PingPongMessageHandler;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class MessageBroker extends Thread{

    private final Logger LOG = Logger.getLogger(MessageBroker.class.getName());

    private volatile boolean process = true;

    private final UDPServer server;
    private final UDPClient client;

    private BlockingQueue<ChannelMessage> channelIn;
    private BlockingQueue<ChannelMessage> channelOut;

    public RoutingTable routingTable;

    private PingPongMessageHandler pingPongMessageHandler;

    public MessageBroker(int port) throws SocketException {

        channelIn = new LinkedBlockingQueue<ChannelMessage>();
        DatagramSocket socket = new DatagramSocket(port);
        this.server = new UDPServer(channelIn, socket);

        channelOut = new LinkedBlockingQueue<ChannelMessage>();
        this.client = new UDPClient(channelOut, new DatagramSocket());

        this.routingTable = new RoutingTable(Constants.LOCALHOST, port);

        this.pingPongMessageHandler = new PingPongMessageHandler();

        this.pingPongMessageHandler.init(channelOut, routingTable);
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
        this.pingPongMessageHandler.sendPing(address, port);
    }

}
