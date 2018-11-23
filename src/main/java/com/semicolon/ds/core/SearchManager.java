package com.semicolon.ds.core;

import com.semicolon.ds.Constants;
import com.semicolon.ds.utils.QueryHitHandler;

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

        int fileIndex = 1;

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

        this.fileDownloadOptions = new HashMap<Integer, String>();

        for (String s : searchResults.keySet()){
            SearchResult searchResult = searchResults.get(s);

                this.fileDownloadOptions.put(fileIndex, searchResult.getAddress() + ":"
                        + searchResult.getPort() + " -- " + searchResult.getFileName() + " -- "
                        + searchResult.getTimeElapsed() + "ms -- "  + searchResult.getHops() + "hops");

                fileIndex++;
        }

        if (fileDownloadOptions.size() == 0){
            System.out.println("Sorry. No files are found!!!");
            return 0;
        }

        printSearchResults(fileDownloadOptions);
        this.clearSearchResults();
        return fileDownloadOptions.size();
    }

    private void clearSearchResults(){
        QueryHitHandler queryHitHandler = QueryHitHandler.getInstance();

        queryHitHandler.setSearchResutls(null);
    }

    private void printSearchResults(Map<Integer, String> fileDownloadOptions){

        System.out.println("\nFile search results : ");
        for (Integer i : fileDownloadOptions.keySet()){
            System.out.println(i.toString() + ") " + fileDownloadOptions.get(i));
        }
    }

    public String getFileDetails(int fileIndex){
        return this.fileDownloadOptions.get(fileIndex);
    }
}
