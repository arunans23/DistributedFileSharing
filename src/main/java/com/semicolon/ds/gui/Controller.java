package com.semicolon.ds.gui;

import com.semicolon.ds.core.GNode;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class Controller extends Thread implements Initializable {


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
        if (data.equals(null) || data.isEmpty()) {
            areaSearch.setText("select a number to download");
        } else {
            areaSearch.setText(String.valueOf(node.doSearch(data)));
            textSearch.setText("");
        }


    }

    public void downloadAction() {
        // write donwload method
        String data = textDownload.getText();
        if (data.equals(null) || data.isEmpty()) {
            setDownloadLog("type something to search");
        } else {
            this.setDownloadLog("some log");
            textDownload.setText("");
        }


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
        return node.getRoutingTable();
    }

    private String getAvailableFiles() {
        return node.getFileNames();
    }


    //use this method to get the file no
    private String getDowloadFileNO() {
        return textDownload.getText();
    }

    private String getDownloadLog() {
        return "this is the log";
    }

    private void setDownloadLog(String log) {
        this.areaDownload.setText(log);
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

        Thread thread = new Thread("New Thread") {
            public void run() {
                int count = 0;
                while (true)
                    try {
                        this.sleep(1000);
                        setRoutingTable(getRoutingTable());
                        count++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }

        };
        thread.start();

    }


}
