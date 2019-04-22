package pl.sudokusolver.recognizerlib;

import org.opencv.core.Core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class Init {
    public enum OSType {
        Windows, MacOS, Linux, Other
    }

    // cached result of OS detection
    private static OSType detectedOS;


    public static void init(){
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
    }

    public static void init(String url){
        String libPath = Paths.get(url,Core.NATIVE_LIBRARY_NAME).toString();
        switch (getOperatingSystemType()){
            case Windows:
                libPath += ".dll";
            break;
            //todo: add linux support
        }
        System.load(libPath);
    }

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
