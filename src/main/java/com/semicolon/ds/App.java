package com.semicolon.ds;

import com.semicolon.ds.core.GNode;

import java.util.UUID;

/**
 * Main App
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            String uniqueID = UUID.randomUUID().toString();
            GNode node = new GNode("node" + uniqueID);
            node.register();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
