package com.semicolon.ds.core;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RoutingTableTest {

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void addNeighboursTest_oneNeighbour() {
    RoutingTable table = new RoutingTable();
    int count = table.addNeighbour("192.168.0.1", 5466);
    assertEquals(1, count);
  }

  @Test
  public void addNeighboursTest_multipleNeighbours() {
    RoutingTable table = new RoutingTable();
    assertEquals(1, table.addNeighbour("192.168.1.1", 5466));
    assertEquals(2, table.addNeighbour("192.168.1.2", 5467));
  }

  @Test
  public void addNeighboursTest_nullAddress() {
    RoutingTable table = new RoutingTable();
    assertEquals(0, table.addNeighbour(null, 5466));
  }

  @Test
  public void addNeighbourTest_sameAddress(){
    RoutingTable table = new RoutingTable();
    assertEquals(1, table.addNeighbour("192.168.1.1", 5466));
    assertEquals(1, table.addNeighbour("192.168.1.1", 5466));
  }

  @Test
  public void addNeighbourTest_maxNeighboursReached() {
    RoutingTable table = new RoutingTable();
    assertEquals(1, table.addNeighbour("192.168.1.1", 5466));
    assertEquals(2, table.addNeighbour("192.168.1.2", 5467));
    assertEquals(3, table.addNeighbour("192.168.1.3", 5468));
    assertEquals(4, table.addNeighbour("192.168.1.4", 5469));
    assertEquals(4, table.addNeighbour("192.168.1.5", 5470));
  }

  @Test
  public void removeNeighbour_oneNeighbourTest() {
    RoutingTable table = new RoutingTable();
    assertEquals(1, table.addNeighbour("192.168.1.1", 5466));
    assertEquals(0, table.removeNeighbour("192.168.1.1", 5466));
  }

  @Test
  public void removeNeighbour_nullNeighbourListTest() {
    RoutingTable table = new RoutingTable();
    assertEquals(0, table.removeNeighbour("192.168.1.1", 5466));
  }

  @Test
  public void removeNeighbour_sameAddressDifferentPortTest() {
    RoutingTable table = new RoutingTable();
    assertEquals(1, table.addNeighbour("192.168.1.1", 5466));
    assertEquals(1, table.removeNeighbour("192.168.1.1", 5467));
  }

  @Test
  public void getCount_addNeighboursTest() {
    RoutingTable table = new RoutingTable();
    assertEquals(table.addNeighbour("192.168.1.1", 5466), table.getCount());
    assertEquals(table.addNeighbour("192.168.1.2", 5466), table.getCount());
    assertEquals(table.addNeighbour("192.168.1.3", 5466), table.getCount());
    assertEquals(table.addNeighbour("192.168.1.4", 5466), table.getCount());
    assertEquals(table.addNeighbour("192.168.1.5", 5466), table.getCount());
  }

  @Test
  public void getCount_removeNeighboursTest() {
    RoutingTable table = new RoutingTable();
    assertEquals(table.addNeighbour("192.168.1.1", 5466), table.getCount());
    assertEquals(table.removeNeighbour("192.168.1.1", 5466), table.getCount());
  }

  @Test
  public void toList_addNeighboursTest() {
    RoutingTable table = new RoutingTable();
    assertEquals(table.toList().size(), table.getCount());
    table.addNeighbour("192.168.1.1", 5466);
    assertEquals(table.toList().size(), table.getCount());
  }
}
