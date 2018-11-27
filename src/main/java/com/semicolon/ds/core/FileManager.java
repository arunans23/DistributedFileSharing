package com.semicolon.ds.core;

import com.semicolon.ds.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class FileManager {

    private static FileManager fileManager;

    private Map<String, String> files;

    private FileManager() {
        files = new HashMap<>();

        ArrayList<String> fullList = readFileNamesFromResources();

        Random r = new Random();

        for (int i = 0; i < 5; i++){
            files.put(fullList.get(r.nextInt(fullList.size())), "");
        }

        printFileNames();
    }

    public static synchronized FileManager getInstance() {
        if (fileManager == null) {
            fileManager = new FileManager();

        }
        return fileManager;
    }

    public boolean addFile(String fileName, String filePath) {
        this.files.put(fileName, filePath);
        return true;
    }

    public Set<String> searchForFile(String query) {
        String[] querySplit = query.split(" ");

        Set<String> result = new HashSet<String>();

        for (String q: querySplit){
            for (String key: this.files.keySet()){
                if (key.toLowerCase().indexOf(q.toLowerCase()) != -1){
                    result.add(key);
                }
            }
        }

        return result;
    }

    private ArrayList<String> readFileNamesFromResources(){

        ArrayList<String> fileNames = new ArrayList<>();

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                (classLoader.getResourceAsStream(Constants.FILE_NAMES)));

        try {

            for (String line; (line = bufferedReader.readLine()) != null;) {
                fileNames.add(line);
            }

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileNames;
    }

    private void printFileNames(){
        System.out.println("Total files: " + files.size());
        System.out.println("++++++++++++++++++++++++++");
        for (String s: files.keySet()) {
            System.out.println(s);
        }
    }
}
