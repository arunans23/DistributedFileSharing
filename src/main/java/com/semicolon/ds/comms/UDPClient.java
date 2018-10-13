package com.semicolon.ds.comms;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.BlockingQueue;

public class UDPClient extends Thread {
    private final BlockingQueue<ChannelMessage> channelOut;
    private final DatagramSocket socket;
    private volatile boolean process = true;
    public UDPClient(BlockingQueue<ChannelMessage> channelOut, DatagramSocket socket) {
        this.channelOut = channelOut;
        this.socket = socket;
    }

    @Override
    public void run() {
        while (process) {
            byte[] response = new byte[65536];
            DatagramPacket packet = new DatagramPacket(response, response.length);
            try {
                socket.receive(packet);
                packet.getSocketAddress();
//                channelIn.put(new String(packet.getData(), 0, packet.getLength()));
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


