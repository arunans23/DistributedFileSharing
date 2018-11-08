package com.semicolon.ds.core;

import java.util.HashMap;
import java.util.Map;

public class FileManager {

    private static FileManager fileManager;

    private Map<String, String> files;

    private FileManager() {
        files = new HashMap<>();
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

    public boolean searchForFile(String query) {
        return this.files.keySet().contains(query);
    }
}
