package com.semicolon.ds;

import com.semicolon.ds.core.GNode;

import java.io.IOException;

/**
 * Main App
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        if (args.length != 2){
            System.out.println("Argument size should be 2, <username> <ipAddress> <portNo>");
            System.exit(0);
        }

        GNode node = new GNode(args[0], args[1]);
        node.register();
    }
}
