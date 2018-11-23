package com.semicolon.ds;

import com.semicolon.ds.core.GNode;

import java.io.Console;
import java.util.*;

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
                System.out.println("\nEnter your search query below : ");
                String searchQuery = scanner.nextLine();

                if (searchQuery != null && !searchQuery.equals("")){
                    boolean results = node.doSearch(searchQuery);

                    if (results){
                        System.out.println("\nPlease choose the file you need to download : ");
                        String fileOption = scanner.nextLine();

                        node.getFile(Integer.parseInt(fileOption));
                    }

                } else {
                    System.out.println("Please give a valid search query!!!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
