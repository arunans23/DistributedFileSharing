package com.semicolon.ds;

import com.semicolon.ds.core.GNode;

/**
 * Main App
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        if (args.length != 3){
            System.out.println("Argument size should be 3, <username> <ipAddress> <portNo>");
            System.exit(0);
        }

        GNode node = new GNode(args[0], args[1], args[2]);
        node.register();
    }
}
