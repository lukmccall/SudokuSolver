package pl.sudokusolver.recognizerlib.utility;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.sudokusolver.recognizerlib._INIT_;

import java.io.File;

@ExtendWith({_INIT_.class})
class ResourceManagerTest {

    @Test
    void extractFileFromJar(){
        String path = ResourceManager.extract("/ann.xml");
        Assert.assertTrue(new File(path).exists());
    }

}