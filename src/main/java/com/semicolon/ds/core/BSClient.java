package com.semicolon.ds.core;

import com.semicolon.ds.Constants;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;
import java.net.*;
import java.util.logging.Logger;

public class BSClient {

    private final Logger LOG = Logger.getLogger(BSClient.class.getName());

    private String BS_IPAddress;
    private int BS_Port;

    DatagramSocket datagramSocket;

    public BSClient() throws IOException{

        datagramSocket = new DatagramSocket();

        readProperties();
    }

    public List<InetSocketAddress> register(String userName, String ipAddress, int port) throws IOException {

        String request = String.format(Constants.REG_FORMAT, ipAddress, port, userName);

        request = String.format(Constants.MSG_FORMAT, request.length() + 5, request);

        DatagramPacket sendingPacket = new DatagramPacket(request.getBytes(),
                request.length(), InetAddress.getByName(BS_IPAddress), BS_Port);

//        datagramSocket.setSoTimeout(Constants.TIMEOUT_REG);

        datagramSocket.send(sendingPacket);

        byte[] buffer = new byte[65536];

        DatagramPacket received = new DatagramPacket(buffer, buffer.length);

        datagramSocket.receive(received);

        String response = new String(received.getData(), 0, received.getLength());

        return  processBSResponse(response);

    }

    public void unregister() {

    }

    private List<InetSocketAddress> processBSResponse(String response){

        StringTokenizer stringToken = new StringTokenizer(response, " ");

        String length = stringToken.nextToken();

        String status = stringToken.nextToken();

        if (!Constants.REGOK.equals(status)) {
            throw new IllegalStateException(Constants.REGOK + " not received");
        }

        int nodesCount = Integer.parseInt(stringToken.nextToken());

        List<InetSocketAddress> gNodes = null;

        switch (nodesCount) {
            case 0:
                LOG.info("Successful - No other nodes in the network");
                gNodes = new ArrayList<>();
                break;

            case 1:
                LOG.info("No of nodes found : 1");

                gNodes = new ArrayList<>();

                while (stringToken.hasMoreTokens()) {
                    gNodes.add(new InetSocketAddress(stringToken.nextToken(),
                            Integer.parseInt(stringToken.nextToken())));
                }
                break;

            case 2:
                LOG.info("No of nodes found : 2");

                gNodes = new ArrayList<>();

                while (stringToken.hasMoreTokens()) {
                    gNodes.add(new InetSocketAddress(stringToken.nextToken(),
                            Integer.parseInt(stringToken.nextToken())));
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
                throw new IllegalStateException("Invalid status code");
        }

        return gNodes;
    }

    private void readProperties(){
        Properties bsProperties = new Properties();
        try {
            bsProperties.load(getClass().getClassLoader().getResourceAsStream(
                    Constants.BS_PROPERTIES));

        } catch (IOException e) {
            LOG.info("Could not open " + Constants.BS_PROPERTIES);
             throw new RuntimeException("Could not open " + Constants.BS_PROPERTIES);
        } catch (NullPointerException e) {
            LOG.info("Could not find " + Constants.BS_PROPERTIES);
            throw new RuntimeException("Could not find " + Constants.BS_PROPERTIES);
        }

        for (Map.Entry<Object, Object> entry : bsProperties.entrySet()) {
            String key = (String) entry.getKey();
            String value = ((String) entry.getValue()).trim();

            if ("bootstrap.ip".equals(key) && !StringUtils.isBlank(value)){
                this.BS_IPAddress = value;
            } else if ("bootstrap.port".equals(key) && !StringUtils.isBlank(value)){
                this.BS_Port = Integer.parseInt(value);
            }
        }
    }
}
