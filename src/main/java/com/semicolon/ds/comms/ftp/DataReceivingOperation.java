package com.semicolon.ds.comms.ftp;

import java.io.*;
import java.net.Socket;

public class DataReceivingOperation implements Runnable {

    private Socket serverSock;
    private BufferedReader in = null;
    private String fileName;

    public DataReceivingOperation(Socket server, String fileName) {
        this.serverSock = server;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(
                    serverSock.getInputStream()));
            DataOutputStream dOut = new DataOutputStream(serverSock.getOutputStream());
            dOut.writeUTF(fileName);
            dOut.flush();
            receiveFile();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveFile() {
        try {
            int bytesRead;

            DataInputStream serverData = new DataInputStream(serverSock.getInputStream());

            String fileName = serverData.readUTF();
            OutputStream output = new FileOutputStream(fileName);
            long size = serverData.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = serverData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }

            output.close();
            serverData.close();

            System.out.println("File " + fileName + " received from server.");
        } catch (IOException ex) {
            System.err.println("server error. Connection closed.");
        }
    }
}
