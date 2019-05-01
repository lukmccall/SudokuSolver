package pl.sudokusolver.server.web;

import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@ContextConfiguration(classes = {WebConfig.class})
public class MainControllerTest {
    @Autowired
    private Logger LOGGER;

    @Test
    void loggerIsSet(){
        Assert.assertNotNull("Logger isn't set",LOGGER);
    }

    @Test
    void testAdd() {
        int a = 1, b = 1;
        Assertions.assertEquals(2, a + b);
    }
}