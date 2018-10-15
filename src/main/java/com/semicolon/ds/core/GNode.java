package com.semicolon.ds.core;

import com.semicolon.ds.Constants;
import com.semicolon.ds.comms.BSClient;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.logging.Logger;

public class GNode {

    private final Logger LOG = Logger.getLogger(GNode.class.getName());

    private BSClient bsClient;

    private String userName;
    private String ipAddress;
    private int port;
    private MessageBroker messageBroker;

    public GNode (String userName) throws IOException {

        this.userName = userName;
        this.ipAddress = Constants.LOCALHOST;
        this.port = getFreePort();

        this.bsClient = new BSClient();
        this.messageBroker = new MessageBroker(port);
        messageBroker.start();
        LOG.info("Gnode initiated on IP :" + ipAddress + " and Port :" + port);

    }

    public void init() {

    }

    public void register() {

        try{
            List<InetSocketAddress> targets= this.bsClient.register(this.userName, this.ipAddress, this.port);
            for (InetSocketAddress target: targets) {
                messageBroker.sendPing(target.getAddress().toString().substring(1), target.getPort());
            }
        } catch (IOException e) {
            LOG.info("Registering Gnode failed");
            e.printStackTrace();
        }
    }

    public void unRegister() {
        try{
            this.bsClient.unRegister(this.userName, this.ipAddress, this.port);

        } catch (IOException e) {
            LOG.info("Un-Registering Gnode failed");
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort(){
        return port;
    }

    private int getFreePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            int port = socket.getLocalPort();
            try {
                socket.close();
            } catch (IOException e) {
                // Ignore IOException on close()
            }
            return port;
        } catch (IOException e) {
            LOG.severe("Getting free port failed");
            throw new RuntimeException("Getting free port failed");
        }
    }
}
