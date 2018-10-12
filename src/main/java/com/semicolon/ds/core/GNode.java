package com.semicolon.ds.core;

import java.io.IOException;
import java.util.logging.Logger;

public class GNode {

    private final Logger LOG = Logger.getLogger(GNode.class.getName());

    private BSClient bsClient;

    private String userName;
    private String ipAddress;
    private String port;

    public GNode (String userName, String ipAddress, String port) throws IOException {
        this.userName = userName;
        this.ipAddress = ipAddress;
        this.port = port;

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

    public String getPort(){
        return port;
    }
}
