package utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {
    
    public void copieWebConfig(String pathSource, String pathDestination)
    {
        File sourceFile = new File(pathSource);
        File destinationDirectory = new File(pathDestination);

        if (sourceFile.exists()) {
            if (!destinationDirectory.exists()) {
                destinationDirectory.mkdirs();
            }

            String destinationFilePath = pathDestination + File.separator + sourceFile.getName();

            try {
                Path sourcePath = Paths.get(pathSource);
                Path destinationPath = Paths.get(destinationFilePath);
                Files.copy(sourcePath, destinationPath);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
