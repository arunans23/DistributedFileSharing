package com.semicolon.ds.comms.ftp;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class FTPServer implements Runnable{

    private ServerSocket serverSocket;
    private Socket clientsocket;

    private String[] fileNames = {
            "Adventures of Tintin", "Jack and Jill", "Glee", "The Vampire Diarie", "King Arthur", "Windows XP",
            "Harry Potter", "Kung Fu Panda", "Lady Gaga", "Twilight", "Windows 8", "Mission Impossible",
            "Turn Up The Music", "Super Mario", "American Pickers", "Microsoft Office 2010", "Happy Feet",
            "Modern Family", "American Idol", "Hacking for Dummies"};

    public FTPServer(int port) throws Exception {
        // create socket
        serverSocket = new ServerSocket(port);
        this.createFiles();
    }

    public int getPort(){
        return serverSocket.getLocalPort();
    }

    public void createFiles() throws IOException {
        for (int i =0; i<5; i++) {
            int rnd = new Random().nextInt(this.fileNames.length);
            String fileSeparator = System.getProperty("file.separator");
            String absoluteFilePath = fileSeparator + "home/smtt/Desktop" + fileSeparator + fileNames[rnd];
            File file = new File(absoluteFilePath);
            System.out.print(absoluteFilePath);
            if(file.createNewFile()){
                System.out.println(absoluteFilePath+" File Created");
            }else System.out.println("File "+absoluteFilePath+" already exists");
        }
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
