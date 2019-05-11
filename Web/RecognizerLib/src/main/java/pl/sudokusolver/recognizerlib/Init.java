package pl.sudokusolver.recognizerlib;

import javafx.scene.input.KeyCode;
import org.opencv.core.Core;

import java.nio.file.Paths;
import java.util.Locale;

/**
 * Klasa odpowiedzialna za poprawną inicjalizację openCV
 */
public class Init {
    /**
     * Dostępne systemy operacyjne
     */
    public enum OSType {
        Windows, MacOS, Linux, Other
    }

    /**
     * System operacyjny komputera, na którym odpalony jest progrem
     */
    private static OSType detectedOS;


    /**
     * Fukncja wczytująca openCV.<br>
     * Dll jest szukany w plikach systemowych.
     */
    public static void init(){
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Fukncja wczytująca openCV z podanej lokalizacji
     * @param url absolutna ścieżka do folderu, gdzie znajduje się openCV
     */
    public static void init(String url){
        String libPath = "";
        switch (getOperatingSystemType()){
            case Windows:
                libPath = Paths.get(url,Core.NATIVE_LIBRARY_NAME).toString();
                libPath += ".dll";
            break;
            case Linux:
                libPath = Paths.get(url,"lib"+Core.NATIVE_LIBRARY_NAME).toString();
                libPath += ".so";
            break;
            case MacOS:
                libPath = Paths.get(url,"lib"+Core.NATIVE_LIBRARY_NAME).toString();
                libPath += ".dylib";
            break;
//            default:
                //todo throw exception
//            break;
        }
        System.load(libPath);
    }

    /**
     * @return system operacyjny
     */
    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((OS.contains("mac")) || (OS.contains("darwin"))) {
                detectedOS = OSType.MacOS;
            } else if (OS.contains("win")) {
                detectedOS = OSType.Windows;
            } else if (OS.contains("nux")) {
                detectedOS = OSType.Linux;
            } else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }
}
