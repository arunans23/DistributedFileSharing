package com.semicolon.ds.core;

import com.semicolon.ds.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.net.*;
import java.util.logging.Logger;

public class BSClient {

    private final Logger LOG = Logger.getLogger(BSClient.class.getName());

    private String BS_IPAddress;
    private int BS_Port;

    DatagramSocket datagramSocket;

    public BSClient() throws IOException{

        datagramSocket = new DatagramSocket();

        this.BS_IPAddress = Constants.BS_IP;
        this.BS_Port = Constants.BS_PORT;
    }

    public List<InetSocketAddress> register(String userName, String ipAddress, int port) throws IOException {

        String request = String.format(Constants.REG_FORMAT, ipAddress, port, userName);

        DatagramPacket datagramPacket = new DatagramPacket(request.getBytes(),
                request.length(), InetAddress.getByName(Constants.BS_IP), port);

        datagramSocket.send(datagramPacket);

        byte[] buffer = new byte[65536];

        DatagramPacket received = new DatagramPacket(buffer, buffer.length);

        datagramSocket.receive(received);

        String response = new String(received.getData(), 0, received.getLength());

        return  processResponse(response);

    }

    public void unregister() {

    }

    private List<InetSocketAddress> processResponse(String response){

        StringTokenizer st = new StringTokenizer(response, " ");
        String status = st.nextToken();

        if (!Constants.REGOK.equals(status)) {
            throw new IllegalStateException(Constants.REGOK + " not received");
        }

        int nodesCount = Integer.parseInt(st.nextToken());

        List<InetSocketAddress> gNodes = null;

        switch (nodesCount) {
            case 0:
                LOG.info("Successful - No other nodes in the network");
                gNodes = new ArrayList<>();
                break;

            case 1:

            case 2:
                LOG.info("Successful - Found 1/2 other nodes in the network");

                gNodes = new ArrayList<>();

                while (st.hasMoreTokens()) {
                    gNodes.add(new InetSocketAddress(st.nextToken(), Integer.parseInt(st.nextToken())));
                }
                break;

            case 9999:
                LOG.severe("Failed. There are errors in your command");
                break;
            case 9998:
                LOG.severe("Failed, already registered to you, unregister first");
                break;
            case 9997:
                LOG.severe("Failed, registered to another user, try a different IP and port");
                break;
            case 9996:
                LOG.severe("Failed, can’t register. BS full.");
                break;
            default:
                throw new IllegalStateException("No proper status code returned");
        }

        return gNodes;


    }
}
