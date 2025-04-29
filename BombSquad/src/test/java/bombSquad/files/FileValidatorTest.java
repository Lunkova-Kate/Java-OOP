package bombSquad.files;


import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static org.junit.jupiter.api.Assertions.*;

class FileValidatorTest {

    @Test
    void testValidFileTrue() throws IOException {
        File tempFile = Files.createTempFile("test", ".txt").toFile();
        assertTrue(FileValidator.isValidFile(tempFile.getAbsolutePath()));
        tempFile.delete();
    }

    @Test
    void testInvalidFileFalse() {
        assertFalse(FileValidator.isValidFile("nonexistent.txt"));
    }
}