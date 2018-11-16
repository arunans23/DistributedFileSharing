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
            System.out.println("Accepted connection : " + clientsocket);
            OutputStream os = clientsocket.getOutputStream();
            InputStream is = clientsocket.getInputStream();
            DataInputStream dIn = new DataInputStream(clientsocket.getInputStream());
            String fileName = dIn.readUTF();
            this.send(os, fileName);
            clientsocket.close();
        }

    }

    public void send(OutputStream os, String fileName) throws Exception {
        // sendfile
        File myFile = new File(fileName);
        byte[] mybytearray = new byte[(int) myFile.length() + 1];
        FileInputStream fis = new FileInputStream(myFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(mybytearray, 0, mybytearray.length);
        System.out.println("Sending...");
        os.write(mybytearray, 0, mybytearray.length);
        os.flush();
    }
}
