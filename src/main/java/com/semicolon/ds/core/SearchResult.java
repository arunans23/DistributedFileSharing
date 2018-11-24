package com.semicolon.ds.core;

public class SearchResult {

    private String fileName;
    private String address;
    private int tcpPort;
    private int hops;
    private long timeElapsed;

    public SearchResult(String fileName, String address, int tcpPort, int hops, long timeElapsed) {
        this.fileName = fileName;
        this.address = address;
        this.tcpPort = tcpPort;
        this.hops = hops;
        this.timeElapsed = timeElapsed;
    }

    public String getFileName() {
        return fileName;
    }

    public String getAddress() {
        return address;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public int getHops() {
        return hops;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }
}
