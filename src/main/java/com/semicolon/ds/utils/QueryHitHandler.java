package com.semicolon.ds.utils;

import com.semicolon.ds.Constants;
import com.semicolon.ds.comms.ChannelMessage;
import com.semicolon.ds.core.RoutingTable;
import com.semicolon.ds.core.TimeoutManager;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class QueryHitHandler implements AbstractResponseHandler {

    private static final Logger LOG = Logger.getLogger(QueryHitHandler.class.getName());

    private RoutingTable routingTable;

    private BlockingQueue<ChannelMessage> channelOut;

    private TimeoutManager timeoutManager;

    private static QueryHitHandler queryHitHandler;

    private Map<String, Set<String>> searchResutls;

    private QueryHitHandler(){

    }

    public static synchronized QueryHitHandler getInstance(){
        if (queryHitHandler == null){
            queryHitHandler = new QueryHitHandler();
        }

        return queryHitHandler;
    }

    @Override
    public synchronized void handleResponse(ChannelMessage message) {
        LOG.fine("Received SEROK : " + message.getMessage()
                + " from: " + message.getAddress()
                + " port: " + message.getPort());

        StringTokenizer stringToken = new StringTokenizer(message.getMessage(), " ");

        String length = stringToken.nextToken();
        String keyword = stringToken.nextToken();
        int filesCount = Integer.parseInt(stringToken.nextToken());
        String address = stringToken.nextToken().trim();
        int port = Integer.parseInt(stringToken.nextToken().trim());

        String addressKey = String.format(Constants.ADDRESS_KEY_FORMAT, address, port);

        int hops = Integer.parseInt(stringToken.nextToken());

        while(filesCount > 0){
//            System.out.println(address + ":" + port + "\t"
//                    + StringEncoderDecoder.decode(stringToken.nextToken()));
            if (this.searchResutls != null){
                if(this.searchResutls.keySet().contains(addressKey)){

                    this.searchResutls.get(addressKey)
                            .add(StringEncoderDecoder.decode(stringToken.nextToken()));

                } else {
                    Set<String> files = new HashSet<String>();
                    files.add(StringEncoderDecoder.decode(stringToken.nextToken()));
                    this.searchResutls.put(addressKey, files);
                }
            }

            filesCount--;
        }
    }

    @Override
    public void init(RoutingTable routingTable, BlockingQueue<ChannelMessage> channelOut, TimeoutManager timeoutManager) {
        this.routingTable = routingTable;
        this.channelOut = channelOut;
        this.timeoutManager = timeoutManager;
    }

    public void setSearchResutls(Map<String, Set<String>> searchResutls) {
        this.searchResutls = searchResutls;
    }
}
