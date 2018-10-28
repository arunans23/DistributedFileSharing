package com.semicolon.ds.core;


import com.semicolon.ds.Constants;

import java.util.ArrayList;
import java.util.List;


public class RoutingTable {
  private ArrayList<Neighbour> neighbours;

  public RoutingTable() {
    this.neighbours = new ArrayList<>();
  }

  /**
   * This method adds the neighbour to the routing table.
   * 
   * @param address
   * @param port
   * @return the number of neighbours.
   */
  public int addNeighbour(String address, int port) {
    if (address == null || address.isEmpty()) {
      return neighbours.size();
    }

    for (Neighbour n : neighbours) {
      if (n.equals(address, port)) {
        return neighbours.size();
      }
    }
    if (neighbours.size() >= Constants.MAX_NEIGHBOURS) {
      return neighbours.size();
    }
    neighbours.add(new Neighbour(address, port));
    return neighbours.size();
  }

  /**
   * This method removes the neighbour from the routing table.
   * 
   * @param address
   * @param port
   * @return the number of neighbours.
   */
  public int removeNeighbour(String address, int port) {
    Neighbour toRemove = null;
    for (Neighbour n : neighbours) {
      if (n.equals(address, port)) {
        toRemove = n;
      }
    }
    if (toRemove != null) {
      neighbours.remove(toRemove);
    }
    return neighbours.size();
  }

  /**
   * This method returns the count of neighbours.
   * 
   * @return count of neighbours.
   */
  public int getCount() {
    return neighbours.size();
  }

  public void print() {
    System.out.println("Total neighbours: " + neighbours.size());
    System.out.println("++++++++++++++++++++++++++");
    for (Neighbour n : neighbours) {
      System.out.println("Address: " + n.getAddress() + " Port: " + n.getPort() + "Pings: " + n.getPingPongs());
    }
  }

  /**
   * This method converts a neighbour array to list of String.
   * 
   * @return list of neighbours.
   */
  public List<String> toList() {
    ArrayList<String> list = new ArrayList<>();
    for (Neighbour n : neighbours) {
      list.add(n.toString());
    }
    return list;
  }

}
