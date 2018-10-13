package com.semicolon.ds.core;

import com.semicolon.ds.comms.ChannelMessage;
import com.semicolon.ds.comms.UDPServer;

import java.net.DatagramSocket;
import java.net.SocketException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

public class MessageBroker extends Thread{
    private final Logger LOG = Logger.getLogger(MessageBroker.class.getName());
    private volatile boolean process = true;
    private final UDPServer server;
    private BlockingQueue<ChannelMessage> chanelIn = new LinkedBlockingDeque<ChannelMessage>();
    public MessageBroker() throws SocketException {
        DatagramSocket socket = new DatagramSocket(9876);
        this.server = new UDPServer(chanelIn, socket);
        LOG.info("starting server");

    }

    @Override
    public void run(){
        this.server.start();
        this.process();
    }

    public void process() {
        while (process) {
            try {
                ChannelMessage message = chanelIn.take();
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



}
