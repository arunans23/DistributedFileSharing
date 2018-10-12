package com.semicolon.ds.core;

import com.semicolon.ds.Constants;

import java.util.logging.Logger;

public class BSClient {

    private final Logger LOG = Logger.getLogger(BSClient.class.getName());

    private String BS_IPAddress;
    private String BS_Port;

    public BSClient() {
        this.BS_IPAddress = Constants.BS_IP;
        this.BS_Port = Constants.BS_PORT;
    }

    public void register(String userName, String ipAddress, String port) {

    }

    public void unregister() {

    }
}
