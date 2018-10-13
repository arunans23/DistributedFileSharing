package com.semicolon.ds.core;

import com.semicolon.ds.Constants;

import java.io.IOException;
import java.net.*;
import java.util.logging.Logger;

public class GNode {

    private final Logger LOG = Logger.getLogger(GNode.class.getName());

    private BSClient bsClient;

    private String userName;
    private String ipAddress;
    private int port;

    public GNode (String userName) throws IOException {

        this.userName = userName;
        this.ipAddress = Constants.LOCALHOST;
        this.port = getFreePort();

        this.bsClient = new BSClient();

        LOG.info("Gnode initiated on IP :" + ipAddress + " and Port :" + port);
    }

    public void register() throws IOException {

        try{
            this.bsClient.register(this.userName, this.ipAddress, this.port);

        } catch (IOException e) {
            LOG.info("Registering Gnode failed");
            e.printStackTrace();
        }
    }

    public void unRegister(){
        this.bsClient.unregister();
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

    public int getFreePort() {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(0);
            socket.setReuseAddress(true);
            int port = socket.getLocalPort();
            try {
                socket.close();
            } catch (IOException e) {
                // Ignore IOException on close()
            }
            return port;
        } catch (IOException e) {
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
        throw new IllegalStateException("");
    }
}
