package com.semicolon.ds.core;

import com.semicolon.ds.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.StringTokenizer;
import java.net.*;
import java.util.logging.Logger;

public class BSClient {

    private final Logger LOG = Logger.getLogger(BSClient.class.getName());

    private String BS_IPAddress;
    private int BS_Port;
    private DatagramSocket sendingSocket;


    DatagramSocket datagramSocket;

    public BSClient() throws IOException{

        datagramSocket = new DatagramSocket();

        this.BS_IPAddress = Constants.BS_IP;
        this.BS_Port = Constants.BS_PORT;
    }

    public void register(String userName, String ipAddress, int port) throws IOException {

        String request = String.format(Constants.REG_FORMAT, ipAddress, port, userName);

        DatagramPacket datagramPacket = new DatagramPacket(request.getBytes(),
                request.length(), InetAddress.getByName(ipAddress), port);

        datagramSocket.send(datagramPacket);

        byte[] buffer = new byte[65536];

        DatagramPacket received = new DatagramPacket(buffer, buffer.length);

        datagramSocket.receive(received);

        String response = new String(received.getData(), 0, received.getLength());

        processResponse(response);

    }

    public void unregister() {

    }

    private void processResponse(String response){
        StringTokenizer st = new StringTokenizer(response, " ");


    }
}
