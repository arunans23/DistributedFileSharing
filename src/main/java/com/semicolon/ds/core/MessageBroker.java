package com.semicolon.ds.core;

import com.semicolon.ds.comms.ChannelMessage;
import com.semicolon.ds.comms.UDPServer;

import java.net.DatagramSocket;
import java.net.SocketException;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

public class MessageBroker {
    private final Logger LOG = Logger.getLogger(MessageBroker.class.getName());
    private final UDPServer server;
    private BlockingDeque<ChannelMessage> chanelIn = new LinkedBlockingDeque<ChannelMessage>();
    public MessageBroker() throws SocketException {
        DatagramSocket socket = new DatagramSocket(9876);
        this.server = new UDPServer(chanelIn, socket);
        LOG.info("starting server");
        server.start();
    }




}
