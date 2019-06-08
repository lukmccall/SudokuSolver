package pl.sudokusolver.server.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.sudokusolver.server.web.WebConfig;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@ContextConfiguration(classes = {WebConfig.class})
class MainControllerTest {

    @Test
    void homePage(){
        
    }
}