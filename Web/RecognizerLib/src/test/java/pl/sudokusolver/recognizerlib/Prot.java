package pl.sudokusolver.recognizerlib;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.tensorflow.Graph;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Server;

import java.io.File;
import java.io.IOException;

@ExtendWith(_INIT_.class)
public class Prot {

    @Test
    void newFunc(){
        try (Graph graph = new Graph()) {
            graph.importGraphDef(FileUtils.readFileToByteArray(new File("tensorflow/output_graph.pb")));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
