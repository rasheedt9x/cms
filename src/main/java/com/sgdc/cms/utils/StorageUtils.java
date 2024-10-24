package com.sgdc.cms.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * StorageUtils
 */
public class StorageUtils {
    public static final String UPLOAD_DIR = "images/";
    public static final Logger logger = LoggerFactory.getLogger(StorageUtils.class);

    public static String saveImageToStorage(byte[] imageBytes, String folder, String fileName) {
         Path dirPath = Paths.get(UPLOAD_DIR + folder);
         File dir = dirPath.toFile();

        if (!dir.exists()) {
        	dir.mkdirs();
        } 

        Path filePath = Paths.get(UPLOAD_DIR,folder,fileName);
        File file = filePath.toFile();

        try (FileOutputStream fos = new FileOutputStream(file)) {
        	fos.write(imageBytes);
        	fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to store image");
        }

        return filePath.toString();
    }

    public static byte[] getImageBytes(String filePath) {
        logger.info("Fetching file of " + filePath);
        File imagePath = Paths.get(filePath).toFile();
        if (!imagePath.exists()) {
        	throw new RuntimeException("File not found" + imagePath);
        }
        try {
            return Files.readAllBytes(imagePath.toPath());    
        } catch(Exception e) {
            throw new RuntimeException("Unable to read bytes");
        }
    }
}
