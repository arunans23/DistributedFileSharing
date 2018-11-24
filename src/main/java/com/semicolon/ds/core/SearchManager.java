package com.semicolon.ds.core;

import com.semicolon.ds.Constants;
import com.semicolon.ds.utils.ConsoleTable;
import com.semicolon.ds.handlers.QueryHitHandler;

import java.util.*;

class SearchManager {

    private MessageBroker messageBroker;

    private Map<Integer, String> fileDownloadOptions;

    SearchManager(MessageBroker messageBroker){
        this.messageBroker = messageBroker;
    }

    int doSearch(String keyword){

        Map<String, SearchResult> searchResults
                = new HashMap<String, SearchResult>();

        QueryHitHandler queryHitHandler = QueryHitHandler.getInstance();
        queryHitHandler.setSearchResutls(searchResults);
        queryHitHandler.setSearchInitiatedTime(System.currentTimeMillis());

        this.messageBroker.doSearch(keyword);

        System.out.println("Please be patient till the file results are returned ...");

        try{
            Thread.sleep(Constants.SEARCH_TIMEOUT);

        } catch (InterruptedException e){
            e.printStackTrace();
        }

        printSearchResults(searchResults);
        this.clearSearchResults();
        return fileDownloadOptions.size();
    }

    private void clearSearchResults(){
        QueryHitHandler queryHitHandler = QueryHitHandler.getInstance();

        queryHitHandler.setSearchResutls(null);
    }

    private void printSearchResults(Map<String, SearchResult> searchResults){

        System.out.println("\nFile search results : ");

        ArrayList<String> headers = new ArrayList<String>();
        headers.add("Option No");
        headers.add("FileName");
        headers.add("Source");
        headers.add("QueryHit time (ms)");
        headers.add("Hop count");

        ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();

        int fileIndex = 1;

        this.fileDownloadOptions = new HashMap<Integer, String>();

        for (String s : searchResults.keySet()){
            SearchResult searchResult = searchResults.get(s);

            this.fileDownloadOptions.put(fileIndex, searchResult.getAddress() + ":"
                    + searchResult.getPort() + ":" + searchResult.getFileName());

            ArrayList<String> row1 = new ArrayList<String>();
            row1.add("" + fileIndex);
            row1.add(searchResult.getFileName());
            row1.add(searchResult.getAddress() + ":" + searchResult.getPort());
            row1.add("" + searchResult.getTimeElapsed());
            row1.add("" + searchResult.getHops());

            content.add(row1);

            fileIndex++;
        }

        if (fileDownloadOptions.size() == 0){
            System.out.println("Sorry. No files are found!!!");

            return;
        }

        ConsoleTable ct = new ConsoleTable(headers,content);
        ct.printTable();

    }

    public String getFileDetails(int fileIndex){
        return this.fileDownloadOptions.get(fileIndex);
    }
}
