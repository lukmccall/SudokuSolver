package pl.sudokusolver.server;

import okhttp3.*;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

public class UploadTest {

    // todo: report memory leak
    /**
     ********   For ANN    ******
     *** Images upload test ***

     Avg Time: 1489.1076923076923 ms
     Min Time: 313 ms
     Max Time: 4315 ms
     *******   For SVM   *******
     *** Images upload test ***

     Avg Time: 1431.7076923076922 ms
     Min Time: 380 ms
     Max Time: 3415 ms

     */
    @Test
    @Ignore
    /* Run when server is running */
    void performanceTest() throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
                                    .connectTimeout(10, TimeUnit.SECONDS)
                                    .writeTimeout(120, TimeUnit.SECONDS)
                                    .readTimeout(120, TimeUnit.SECONDS)
                                    .build();

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
                    .addFormDataPart("recognizer", "Tesseract")
                    .build();
            Request request = new Request.Builder()
                    .url("http://localhost:8080/app/api/extractfromimg")
                    .post(requestBody)
                    .build();
            long startTime = System.currentTimeMillis();

            long endTime;
            Response response = null;
            try{
                response = client.newCall(request).execute();
            } catch (Exception e) {
                System.out.println(i + " " + e.getMessage() + " time " + (System.currentTimeMillis()-startTime));

            } finally {
                endTime = System.currentTimeMillis();
                if(response != null)
                    response.close();

            }
            long currDuration = endTime - startTime;
            if (currDuration > 14000) System.out.println(i);

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
