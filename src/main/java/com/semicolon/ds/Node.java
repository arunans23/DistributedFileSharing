package com.semicolon.ds;

import java.io.IOException;
import java.net.*;
import java.util.Map;

public class Node {
    private Map<String, String> routingTable;
    private DatagramSocket listeningSocket;
    private DatagramSocket sendingSocket;
    private int port;

    public Node(String host, String name) throws IOException {
        InetAddress bootstrapServerIP = InetAddress.getByName(host);
        port = getFreePort();
        this.listeningSocket = new DatagramSocket(port);
        this.sendingSocket = new DatagramSocket();
        String inp = String.format("0036 REG %s %d %s", InetAddress.getLocalHost().toString(), this.port, name);
        DatagramPacket addPacket =
                new DatagramPacket( inp.getBytes(), inp.getBytes().length,
                        bootstrapServerIP,  55555);

        sendingSocket.send(addPacket);

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

    public void receivedata() throws IOException {
        boolean finished = false;
        DatagramPacket packet = null;
        while ( ! finished ) {
            listeningSocket.receive(packet);
        }
    }


}
