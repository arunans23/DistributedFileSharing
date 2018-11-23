package com.semicolon.ds.core;

import com.semicolon.ds.Constants;

import java.io.File;
import java.io.IOException;
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
                if (key.indexOf(q) != -1){
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
        File file = new File(classLoader.getResource(Constants.FILE_NAMES).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                fileNames.add(line);
            }

            scanner.close();

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
