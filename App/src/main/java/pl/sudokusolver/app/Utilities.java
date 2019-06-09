package pl.sudokusolver.app;

import javafx.geometry.Rectangle2D;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class containing publicly used functions
 */
public class Utilities {
    final static private Logger logger = Logger.getLogger("Logger");

    /**
     * Checks if the java version is equals to 8
     * @return true if is, otherwise false
     */
    static boolean isJavaValid(){
        return getVersion() == 8;
    }

    /**
     * Function to read java function
     * @return java function
     */
    private static int getVersion() {
        String version = System.getProperty("java.version");
        if(version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf(".");
            if(dot != -1) { version = version.substring(0, dot); }
        } return Integer.parseInt(version);
    }

    /**
     * Function to check if rectangle can be used in cutting the image
     * @param rectangle2D rectangle selected by user
     * @return true if it is, otherwise false
     */
    public static boolean isValid(Rectangle2D rectangle2D){
        return rectangle2D.getWidth() > 0 && rectangle2D.getHeight() > 0;
    }

    /**
     * Function to get error string from error code
     * @param error error code
     * @return error string
     */
    public static String getError(int error){
        switch (error){
            case 1:
                return Values.E001;
            case 2:
                return Values.E002;
            case 3:
                return Values.E003;
            case 4:
                return Values.E004;
            case 5:
                return Values.E005;
            case 6:
                return Values.E006;
            case 7:
                return Values.E007;
            case 8:
                return Values.E008;
            case 9:
                return Values.E009;
        }
        return null;
    }

    /**
     * Function to save file
     * @param input what to save
     */
    public static void saveFile(String input){
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(Values.SAVE_FILE), StandardCharsets.UTF_8));
            writer.write(input);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Error");
        }
        finally {
            try {
                writer.close();
            }
            catch (Exception ex) {
                logger.log(Level.WARNING, "Error");
            }
        }
    }

    /**
     * Function to read file containing information about theme chosen
     */
    static void readFile(){
        File file = new File(Values.SAVE_FILE);
        try{
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()){
                String temp = sc.nextLine();
                //logger.log(Level.INFO, temp);

                if (temp.startsWith("Theme:")){
                    if (temp.lastIndexOf("LIGHT") != -1){
                        Values.THEME = Theme.LIGHT;
                    }
                    else{
                        Values.THEME = Theme.DARK;
                    }
                }
            }
        }
        catch (Exception e){
            Utilities.saveFile("Theme: LIGHT");
            Values.THEME = Theme.LIGHT;
        }
    }

    /**
     * Function to get file extension
     * @param file file to check
     * @return extension fo the file
     */
    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

    /**
     * Function to log what error occured
     * @param error error string
     */
    public static void log(String error){
        logger.log(Level.SEVERE, error);
    }

}
