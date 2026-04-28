package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {
    public static File basePath = new File("src/test/java/resources/");

    public static void createFileAndAddData(File filePath, String textToAdd) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(textToAdd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFolderAndFiles(File folderPath) {
        for (File toDelete : folderPath.listFiles()) {
            if (toDelete.isFile()) toDelete.delete();
        }
        folderPath.delete();
    }
}
