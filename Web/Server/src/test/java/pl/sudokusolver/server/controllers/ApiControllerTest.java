package pl.sudokusolver.server.controllers;

import com.google.gson.Gson;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;
import pl.sudokusolver.recognizerlib.filters.MedianBlur;
import pl.sudokusolver.recognizerlib.sudoku.Sudoku;
import pl.sudokusolver.server.data.NotOkResponse;
import pl.sudokusolver.server.data.OkResponse;
import pl.sudokusolver.server.web.WebConfig;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@ContextConfiguration(classes = {WebConfig.class})
class ApiControllerTest {

    @Autowired
    ApiController controller;

    MockMvc mockMvc;

    @Autowired
    private Logger LOGGER;

    @Test
    void loggerIsSet(){
        Assert.assertNotNull("Logger isn't set",LOGGER);
    }

    @Test
    void solveExceptions() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller)
                .build();

        mockMvc.perform(post("/api/solve").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
        mockMvc.perform(post("/api/solve")).andExpect(status().is(415));
    }

    @Test
    void solveOk() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller)
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(new Sudoku());
        MvcResult mvcResult = mockMvc.perform(post("/api/solve").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        OkResponse okResponse = gson.fromJson(mvcResult.getResponse().getContentAsString(), OkResponse.class);
        Assert.assertEquals(1, okResponse.status);

        for (int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++)
                Assert.assertNotEquals(0, okResponse.sudoku[i][j]);
    }

    @Test
    void photoNoOk() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller)
                .build();

        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(post("/api/extractfromimg")).andExpect(status().is(400));
        });

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/extractfromimg")).andExpect(status().is(400));
    }

    @Test
    void photoOk() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller)
                .build();

        File file = new File(ApiControllerTest.class.getResource("/sudoku.jpg").getFile());
        Assert.assertTrue("Path doesn't exist.",file.exists());
        FileInputStream fileStteam = new FileInputStream(file);
        MockMultipartFile sudoku = new MockMultipartFile("sudoku", "sudoku.jpg","image/jpg",fileStteam);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/extractfromimg")
                                              .file(sudoku)).andExpect(status().is(200)).andReturn();
        Gson gson = new Gson();
        OkResponse okResponse = gson.fromJson(result.getResponse().getContentAsString(), OkResponse.class);

        Assert.assertEquals(1, okResponse.status);
        Assert.assertNotNull(okResponse.sudoku);
    }
}