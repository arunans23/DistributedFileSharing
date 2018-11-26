package com.semicolon.ds.gui;

import com.semicolon.ds.core.GNode;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class Controller implements Initializable {


    public GNode node;

    public Button buttonSearch;
    public Button leaveButton;
    public Button buttonDownload;

    public Label labelAddress;
    public Label labelPort;


    public TextArea areaAvailable;
    public TextArea areaSearch;
    public TextArea areaTable;
    public TextArea areaDownload;

    public TextField textSearch;
    public TextField textDownload;

    public void searchAction() {
        String data = textSearch.getText();
        areaSearch.setText(String.valueOf(node.doSearch(data)));

    }

    public void downloadAction() {
        // write donwload method
        this.setDownloadLog();


    }

    public void setData(String ipaddress, String port) {
        labelAddress.setText(ipaddress);
        areaAvailable.setText(getAvailableFiles());
        labelPort.setText(port);

    }

    public void leaveAction() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you leaving ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
            node.unRegister();
            System.exit(0);
        }


    }

    public void setRoutingTable(String table) {
        areaTable.setText(table);

    }

    private String getRoutingTable() {
        return "**RoutingTable**";
    }

    private String getAvailableFiles() {
        return "**available**";
    }


    //use this method to get the file no
    private int getDowloadFileNO() {
        return Integer.valueOf(textDownload.getText());
    }

    private String getDownloadLog() {
        return "this is the log";
    }

    private void setDownloadLog() {
        this.areaDownload.setText(getDownloadLog());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String uniqueID = UUID.randomUUID().toString();
        try {
            node = new GNode("node" + uniqueID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        node.init();

        this.setData(node.getIpAddress(), String.valueOf(node.getPort()));
        this.setRoutingTable(this.getRoutingTable());

    }
}
