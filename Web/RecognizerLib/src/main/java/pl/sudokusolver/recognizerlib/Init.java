package pl.sudokusolver.recognizerlib;

import org.opencv.core.Core;

import java.nio.file.Paths;
import java.util.Locale;

/**
 * Init class.<br>
 * Help in loading opencv.
 */
public class Init {
    /**
     * all os types
     */
    public enum OSType {
        Windows, MacOS, Linux, Other
    }

    /**
     * currently detected os
     */
    private static OSType detectedOS;


    /**
     * Load opencv dll.<br>
     * Java looking for dll file in system files.
     */
    public static void init(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     ** Load opencv dll from path (parent folder url).<br>
     * @param url absolute path to opancv, but without file and extension.
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
            default:
                throw new IllegalArgumentException("Your system is not supported");
        }
        System.load(libPath);
    }

    /**
     * @return currently running os type
     */
    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if (OS.contains("mac")
                || OS.contains("darwin")) {
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
