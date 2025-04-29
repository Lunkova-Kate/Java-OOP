package bombSquad.files;


import java.io.File;

public class FileValidator {
    public static boolean isValidFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile() && file.canRead();
    }
}
