package pl.sudokusolver.recognizerlib;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class _INIT_ implements BeforeAllCallback {
    private static boolean load = false;
    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        if(!load) {
            Init.init("C:\\opencv4.0.1\\opencv\\build\\java\\x64");
            load = true;
        }
        //C:\opencv4.0.1\opencv\build\java\x64 - łukasza
        //C:\sudokuSolver\opencv\build\java\x64 - moje

    }
}
