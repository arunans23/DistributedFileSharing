package com.semicolon.ds;

import com.semicolon.ds.core.GNode;

import java.util.UUID;

/**
 * Main App
 *
 */
public class App 
{
  public static void main(String[] args) {

    for (int i = 0; i < 1; i++) {
      try {
        String uniqueID = UUID.randomUUID().toString();
        GNode node = new GNode("node" + uniqueID);
        node.init();

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}