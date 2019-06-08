package pl.sudokusolver.recognizerlib.ocr.tesseract;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.sudokusolver.recognizerlib._INIT_;

@ExtendWith({_INIT_.class})
class TesseractSingletonWrapperTest {

    @Test
    void goodLoadTesseract(){
        Assert.assertNotNull(TesseractSingletonWrapper.tesseract);
    }
}