package com.semicolon.ds;

import com.semicolon.ds.core.GNode;

import java.io.IOException;
import java.util.UUID;

/**
 * Main App
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        String uniqueID = UUID.randomUUID().toString();

        try {
            GNode node = new GNode("node" + uniqueID);
            node.register();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
