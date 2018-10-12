package com.semicolon.ds.core;

import java.io.IOException;
import java.net.*;
import java.util.logging.Logger;

public class GNode {

    private final Logger LOG = Logger.getLogger(GNode.class.getName());

    private BSClient bsClient;

    private String userName;
    private String ipAddress;
    private int port;

    public GNode (String userName, String port) throws IOException {
        this.userName = userName;
        this.ipAddress = InetAddress.getLocalHost().toString();
        this.port = getFreePort();

        this.bsClient = new BSClient();
    }

    public void register() throws IOException {
        this.bsClient.register(this.userName, this.ipAddress, this.port);
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
