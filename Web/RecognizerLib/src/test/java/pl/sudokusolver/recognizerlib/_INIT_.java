package pl.sudokusolver.recognizerlib;

import org.junit.Assert;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class _INIT_ implements BeforeAllCallback {
    private static boolean load = false;
    @Override
    public void beforeAll(ExtensionContext extensionContext) throws IOException {
        if(!load) {
            InputStream input = _INIT_.class.getClassLoader().getResourceAsStream("config.properties");
            Properties prop = new Properties();
            //load a properties file from class path, inside static method
            prop.load(input);
            Init.init(prop.getProperty("opencv"));
            load = true;
        }

    }
}
