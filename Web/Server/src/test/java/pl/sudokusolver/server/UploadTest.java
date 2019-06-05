package pl.sudokusolver.server;

import okhttp3.*;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class UploadTest {

    // todo: report memory leak
    /**
     ********   For ANN    ******
     *** Images upload test ***

     Avg Time: 3009.3384615384616 ms
     Min Time: 310 ms
     Max Time: 10039 ms

     *******   For SVM   *******
     *** Images upload test ***

     Avg Time: 2950.8846153846152 ms
     Min Time: 29 ms
     Max Time: 9296 ms
     */
    @Test
    @Ignore
    /* Run when server is running */
    void performanceTest() throws IOException {

        OkHttpClient client = new OkHttpClient();
        int all = 130;
        long avgTime = 0;
        long minTime = Long.MAX_VALUE;
        long maxTime = Long.MIN_VALUE;
        for(int i = 0; i < all; i++) {

            String path = "../../Data/TestImgs/" + i + ".jpg";
            File file = new File(path);
            byte[] fileContent = Files.readAllBytes(file.toPath());

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("sudoku", "sudoku.jpg",
                            RequestBody.create(MediaType.get("image/jpg"), fileContent))
                    .addFormDataPart("recognizer", "ANN")
                    .build();
            Request request = new Request.Builder()
                    .url("http://localhost:8080/api/extractfromimg")
                    .post(requestBody)
                    .build();
            long startTime = System.currentTimeMillis();

            long endTime;
            Response response = null;
            try{
                response = client.newCall(request).execute();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                endTime = System.currentTimeMillis();
                if(response != null) response.close();
            }
            long currDuration = endTime - startTime;

            avgTime += currDuration;
            if(currDuration > maxTime) maxTime = currDuration;
            if(currDuration < minTime) minTime = currDuration;
        }
        System.out.println("*** Images upload test ***");
        System.out.println();
        System.out.println("Avg Time: " + (double)avgTime/(double)all + " ms");
        System.out.println("Min Time: " + minTime + " ms");
        System.out.println("Max Time: " + maxTime + " ms");
    }
}
