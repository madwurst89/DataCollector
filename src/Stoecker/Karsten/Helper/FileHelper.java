package Stoecker.Karsten.Helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Karsten Stoecker
 * @date 18.01.2016
 * @version 0.1
 *
 */
public class FileHelper
{
    /**
     *
     * @param text Text of the file, which should be written.
     * @param filePath Path and filename of the file, which should be written.
     * @return Boolean indicating whether the writing process has been successful .
     * @version 0.1
     *
     */
    public static boolean writeStringToFile(String text, String filePath)
    {
        File file = new File(filePath);

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(text);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
