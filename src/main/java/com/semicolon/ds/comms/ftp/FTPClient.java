package com.semicolon.ds.comms.ftp;

import java.io.*;
import java.net.Socket;

public class FTPClient {

    String fileName;

    public FTPClient() throws Exception {

        long start = System.currentTimeMillis();
        // localhost for testing
        Socket serverSock = new Socket("127.0.0.1", 13267);

        System.out.println("Connecting...");
        Thread t = new Thread(new DataReceivingOperation(serverSock, "test.txt"));
        t.start();
    }

    public static void main(String[] args) throws Exception {
        FTPClient ftpClient = new FTPClient();

    }
}
