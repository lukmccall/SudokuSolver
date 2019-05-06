package App.src.sample;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utilities {
    final static Logger logger = Logger.getLogger("Logger");

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

    static void readFile(){
        File file = new File(Values.SAVE_FILE);
        try{
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()){
                String temp = sc.nextLine();
                logger.log(Level.INFO, temp);

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
            logger.log(Level.INFO, "bump");
            Utilities.saveFile("Theme: LIGHT");
            Values.THEME = Theme.LIGHT;
        }
    }

}
