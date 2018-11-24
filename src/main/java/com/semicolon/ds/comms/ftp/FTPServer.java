package com.semicolon.ds.comms.ftp;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class FTPServer implements Runnable{

    private ServerSocket serverSocket;
    private Socket clientsocket;

    public FTPServer(int port) throws Exception {
        // create socket
        serverSocket = new ServerSocket(port);
    }

    public int getPort(){
        return serverSocket.getLocalPort();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Waiting...");

            try {
                clientsocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread t = new Thread(new DataSendingOperation(clientsocket));
            t.start();
        }
    }
}
