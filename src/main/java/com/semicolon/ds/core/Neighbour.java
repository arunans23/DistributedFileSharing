package com.semicolon.ds.core;

public class Neighbour {
    private final String address;
    private final int port;
    private int pingPongs;

    public Neighbour(String address, int port) {
        this.address = address;
        this.port = port;
        this.pingPongs = 0;
    }

    public boolean equals (Neighbour newN) {
        return newN.getPort() == this.port & this.address.equals(newN.getAddress());
    }

    public boolean eaquals (String address, int port) {
        return this.address.equals(address) & this.port == port;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getPingPongs() {
        return pingPongs;
    }

    public void Ping() {
        this.pingPongs++;
    }

    public String toString() {
        return this.address + ":" + this.port;
    }
}
