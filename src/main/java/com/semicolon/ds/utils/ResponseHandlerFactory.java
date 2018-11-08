package com.semicolon.ds.utils;

import com.semicolon.ds.core.MessageBroker;

import java.util.logging.Logger;

public class ResponseHandlerFactory {

    private static final Logger LOG = Logger.getLogger(ResponseHandlerFactory.class.getName());

    public static AbstractResponseHandler getResponseHandler(String keyword,
                                                             MessageBroker messageBroker){
        switch (keyword){
            case "PING":
                AbstractResponseHandler pingHandler = PingHandler.getInstance();
                pingHandler.init(
                        messageBroker.getRoutingTable(),
                        messageBroker.getChannelOut(),
                        messageBroker.getTimeoutManager()
                );
                return pingHandler;

            case "PONG":
                AbstractResponseHandler pongHandler = PongHandler.getInstance();
                pongHandler.init(
                        messageBroker.getRoutingTable(),
                        messageBroker.getChannelOut(),
                        messageBroker.getTimeoutManager()
                );
                return pongHandler;

            default:
                LOG.severe("Unknown keyword received in Response Handler : " + keyword);
                return null;
        }
    }
}
