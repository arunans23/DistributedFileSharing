package com.semicolon.ds;

import com.semicolon.ds.core.GNode;

import java.io.Console;
import java.util.Scanner;
import java.util.UUID;

/**
 * Main App
 *
 */
public class App 
{
    public static void main(String[] args) {
        try {
            String uniqueID = UUID.randomUUID().toString();
            GNode node = new GNode("node" + uniqueID);
            node.init();

            Scanner scanner = new Scanner(System.in);

            while(true){
                System.out.println("Enter your search query : ");
                String searchQuery = scanner.nextLine();
                node.doSearch(searchQuery);
                Thread.sleep(5000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
