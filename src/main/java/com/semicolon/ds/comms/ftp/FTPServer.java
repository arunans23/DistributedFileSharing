package com.semicolon.ds.comms.ftp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer {

    ServerSocket serverSocket;
    Socket clientsocket;

    public FTPServer() throws Exception {
        String str = new String("hello");
        // create socket
        serverSocket = new ServerSocket(13267);

        while (true) {
            System.out.println("Waiting...");

            clientsocket = serverSocket.accept();
            Thread t = new Thread(new DataSendingOperation(clientsocket));
            t.start();
        }

    }

    public static void main(String[] args) throws Exception {
        FTPServer ftpServer = new FTPServer();
    }

}
