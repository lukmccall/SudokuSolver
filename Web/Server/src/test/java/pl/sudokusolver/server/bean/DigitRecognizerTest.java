package pl.sudokusolver.server.bean;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.sudokusolver.server.web.WebConfig;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@ContextConfiguration(classes = {WebConfig.class})
class DigitRecognizerTest {

    @Autowired
    private DigitRecognizer digitRecognizer;

    @Test
    void checkIfDigitRecognizerLoadCorrectly(){
        Assert.assertNotNull(digitRecognizer.getRecognizer("ANN", false));
        Assert.assertNotNull(digitRecognizer.getRecognizer("SVM", false));
        Assert.assertNotNull(digitRecognizer.getRecognizer("TESSERACT", false));
        Assert.assertNotNull(digitRecognizer.getRecognizer("TESSERACT", true));
    }

}