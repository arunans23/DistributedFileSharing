package com.semicolon.ds.core;

import com.semicolon.ds.Constants;

import java.io.IOException;
import java.net.*;
import java.util.logging.Logger;

public class BSClient {

    private final Logger LOG = Logger.getLogger(BSClient.class.getName());

    private String BS_IPAddress;
    private int BS_Port;
    private DatagramSocket sendingSocket;

    public BSClient() {
        this.BS_IPAddress = Constants.BS_IP;
        this.BS_Port = Constants.BS_PORT;
    }

    public void register(String userName, String ipAddress, int port) throws IOException {
            InetAddress bootstrapServerIP = InetAddress.getByName(BS_IPAddress);
            this.sendingSocket = new DatagramSocket();
            String inp = String.format("0036 REG %s %d %s", InetAddress.getLocalHost().toString(), port, userName);
            DatagramPacket addPacket =
                    new DatagramPacket( inp.getBytes(), inp.getBytes().length,
                            bootstrapServerIP,  BS_Port);

            sendingSocket.send(addPacket);

    }

    public void unregister() {

    }
}
