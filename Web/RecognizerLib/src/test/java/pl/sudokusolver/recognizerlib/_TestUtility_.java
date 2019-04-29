package pl.sudokusolver.recognizerlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class _TestUtility_ {
    public static String getPathToResource(String res){
        return _TestUtility_.class.getResource(res).getPath().substring(1);
    }

    public static List<String> getResourceFiles(String path) throws IOException {
        List<String> filenames = new ArrayList<>();

        try (
                InputStream in = getResourceAsStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;

            while ((resource = br.readLine()) != null) {
                filenames.add(resource);
            }
        }

        return filenames;
    }

    public static List<String> getAllImages() throws IOException{
        String pathToDir = _TestUtility_.getPathToResource("/sudokuimages/");
        List<String> resourceFiles =_TestUtility_.getResourceFiles("/sudokuimages/");

        for(int i = 0; i < resourceFiles.size(); i++)
            resourceFiles.set(i, pathToDir+resourceFiles.get(i));

        return resourceFiles;
    }

    private static InputStream getResourceAsStream(String resource) {
        final InputStream in
                = _TestUtility_.class.getResourceAsStream(resource);

        return in == null ? _TestUtility_.class.getResourceAsStream(resource) : in;
    }


}
