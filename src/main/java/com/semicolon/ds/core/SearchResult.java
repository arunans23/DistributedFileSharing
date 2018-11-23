package com.semicolon.ds.core;

public class SearchResult {

    private String fileName;
    private String address;
    private int port;
    private int hops;
    private long timeElapsed;

    public SearchResult(String fileName, String address, int port, int hops, long timeElapsed) {
        this.fileName = fileName;
        this.address = address;
        this.port = port;
        this.hops = hops;
        this.timeElapsed = timeElapsed;
    }

    public String getFileName() {
        return fileName;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getHops() {
        return hops;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }
}
