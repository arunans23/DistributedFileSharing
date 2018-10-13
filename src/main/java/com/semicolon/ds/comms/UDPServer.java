package com.semicolon.ds.comms;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.BlockingDeque;

public class UDPServer extends Thread {
    private final BlockingDeque<ChannelMessage> channelIn;
    private final DatagramSocket socket;
    private volatile boolean process = true;
    public UDPServer(BlockingDeque<ChannelMessage> channelIn, DatagramSocket socket) {
        this.channelIn = channelIn;
        this.socket = socket;
    }

    @Override
    public void run() {
        while (process) {

            try {
                byte[] response = new byte[65536];
                DatagramPacket packet = new DatagramPacket(response, response.length);
                    socket.receive(packet);
                System.out.println(new String(packet.getData(), 0, packet.getLength()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
    public void stopProcessing() {
        this.process = false;
    }
}
