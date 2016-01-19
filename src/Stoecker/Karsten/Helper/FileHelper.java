package Stoecker.Karsten.Helper;

import java.io.*;
import java.util.ArrayList;

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

    /**
     * Method to read the text of a file to an {@link String}-Array. Returns null, if file were not found or could not be read.
     *
     * @param filePath Path to the file which should be read.
     * @return {@link String}-Array which contains the text of the file. Every line is transformed to one array element.
     */
    public static String[] readFromFile(String filePath)
    {
        // based on http://javabeginners.de/Ein-_und_Ausgabe/Eine_Datei_zeilenweise_auslesen.php
        File file = new File(filePath);

        if (!file.canRead() || !file.isFile())
            System.exit(0);


        ArrayList<String> fileContentArrayList = new ArrayList<>();

        try(BufferedReader in = new BufferedReader(new FileReader(file)))
        {
            String line;
            int index = 0;

            while((line = in.readLine()) != null)
            {
                fileContentArrayList.add(index, line);
                index++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return null;
        } catch (IOException e) {
            System.out.println("Reading from file failed.");
            return null;
        }

        String[] fileContentArray = fileContentArrayList.stream().toArray(String[]::new);

        return fileContentArray;
    }
}
