package com.example.metadata_reader;
import java.io.File;
import java.util.List;

public class reader {
    public static void resortFolder(String path, List<String> filePaths) {

        File rootFolder = new File(path);

        File[] files = rootFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (isPDF(file.getAbsolutePath())){
                        filePaths.add(file.getAbsolutePath());
                    }
                } else if (file.isDirectory()) {
                    resortFolder(file.getPath(), filePaths);
                }
            }
        }
    }

    public static boolean isPDF(String path) {
        return path.toLowerCase().endsWith(".pdf");
    }
}
