package pl.sudokusolver.recognizerlib;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class _INIT_ implements BeforeAllCallback {
    private static boolean load = false;
    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        if(!load) {
            Init.init("C:\\sudokuSolver\\opencv\\build\\java\\x64");
            load = true;
        }

    }
}
