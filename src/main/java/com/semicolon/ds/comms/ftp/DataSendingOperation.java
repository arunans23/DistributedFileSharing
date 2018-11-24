package com.semicolon.ds.comms.ftp;

import java.io.*;
import java.net.Socket;

public class DataSendingOperation implements Runnable {

    private Socket clientSocket;
    private BufferedReader in = null;

    public DataSendingOperation(Socket client) {
        this.clientSocket = client;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
            DataInputStream dIn = new DataInputStream(clientSocket.getInputStream());
            String fileName = dIn.readUTF();
            if (fileName != null) {
                sendFile(createFiles(fileName));
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendFile(File file) {
        try {
            //handle file read
            File myFile = file;
            byte[] mybytearray = new byte[(int) myFile.length()];

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(mybytearray, 0, mybytearray.length);

            //handle file send over socket
            OutputStream os = clientSocket.getOutputStream();

            //Sending file name and file size to the server
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(myFile.getName());
            dos.writeLong(mybytearray.length);
            dos.write(mybytearray, 0, mybytearray.length);
            dos.flush();
            System.out.println("File " + file.getName() + " sent to client.");
        } catch (Exception e) {
            System.err.println("File does not exist!");
            e.printStackTrace();
        }
    }

    public File createFiles(String fileName) throws IOException {
        String fileSeparator = System.getProperty("file.separator");
        String absoluteFilePath = "E:\\" + fileName;
        File file = new File(absoluteFilePath);
        System.out.print(absoluteFilePath);
        if(file.createNewFile()){
            System.out.println(absoluteFilePath+" File Created");
        }else System.out.println("File "+absoluteFilePath+" already exists");
        return file;
    }
}
