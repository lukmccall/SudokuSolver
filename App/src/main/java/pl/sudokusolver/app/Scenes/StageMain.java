package pl.sudokusolver.app.Scenes;

import com.google.gson.Gson;
import okhttp3.*;
import pl.sudokusolver.app.*;
import pl.sudokusolver.app.CustomViews.Canvas;
import pl.sudokusolver.app.CustomViews.Menu;
import pl.sudokusolver.app.CustomViews.RightSide;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pl.sudokusolver.app.Api.ErrorResponse;
import pl.sudokusolver.app.Api.SolveRequest;
import pl.sudokusolver.app.Api.SudokuResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 *
 */
public class StageMain extends Stage implements MenuListener, ThemeChangeListener, Sender {

    private BorderPane vBox;
    private Canvas canvas;
    private RightSide rightSide;

    public StageMain(){
        init();
    }

    @Override
    public void solve() throws Exception{
        HttpUrl url = HttpUrl.parse(Values.SERVER_URL).newBuilder()
                             .addPathSegment("api").addPathSegment("solve").build();

        OkHttpClient client = new OkHttpClient();
        SolveRequest solveRequest = new SolveRequest(canvas.getInitial());
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(Values.SERVER_REQUEST_TYPE, gson.toJson(solveRequest, solveRequest.getClass()));

        Request request = new Request.Builder()
                                     .url(url)
                                     .post(body)
                                     .build();

        try (Response response = client.newCall(request).execute()) {
            if(response.isSuccessful()){
                SudokuResponse sudokuResponse = gson.fromJson(response.body().charStream(),SudokuResponse.class);
                receivedSolved(sudokuResponse.sudoku);
            } else {
                ErrorResponse errorResponse = gson.fromJson(response.body().charStream(), ErrorResponse.class);
                System.out.println(errorResponse);
                //todo:> error handling - DJ Bomasz
            }
        }
    }

    @Override
    public void send(BufferedImage image, Parameters parameters) throws Exception{
        HttpUrl url = HttpUrl.parse(Values.SERVER_URL).newBuilder()
                .addPathSegment("api").addPathSegment("extractfromimg").build();

        OkHttpClient client = new OkHttpClient();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        Gson gson = new Gson();

        RequestBody requestBody = new MultipartBody.Builder()
                                        .setType(MultipartBody.FORM)
                                        .addFormDataPart("sudoku", "sudoku.jpg",
                                            RequestBody.create(Values.SERVER_IMG_TYPE, outputStream.toByteArray()))
                                        .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if(response.isSuccessful()){
                SudokuResponse sudokuResponse = gson.fromJson(response.body().charStream(),SudokuResponse.class);
                receivedInitial(sudokuResponse.sudoku);
            } else {
                ErrorResponse errorResponse = gson.fromJson(response.body().charStream(), ErrorResponse.class);
                System.out.println(errorResponse);
                //todo:> error handling - DJ Bomasz
            }
        }

    }

    private void receivedSolved(int[][] array){
        canvas.modifySolution(array);
    }

    private void receivedInitial(int[][] array){
        canvas.modifyInitial(array);
    }

    @Override
    public void clear(){
        canvas.clear();
    }


    @Override
    public void changed(){
        canvas.update();
        if (rightSide != null)
            rightSide.change();
        if (Values.THEME == Theme.LIGHT){
            vBox.setStyle("-fx-background-color: #F0F0F0;");
        }
        else{
            vBox.setStyle("-fx-background-color: #34495E;");
        }
    }

    private void init(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        rightSide = new RightSide(this, primaryScreenBounds.getWidth() * 0.75f, primaryScreenBounds.getHeight() * 0.75f);
        Menu menu = new Menu(this, this, this);

        canvas = new Canvas();
        canvas.setOnMouseClicked((event) -> {
            double mouseX = event.getX();
            double mouseY = event.getY() - canvas.getOffsetY();

            canvas.mouseClick(mouseX, mouseY);
        });

        this.setX(primaryScreenBounds.getMinX());
        this.setY(primaryScreenBounds.getMinY());

        this.setWidth(primaryScreenBounds.getWidth() * 0.75f);
        this.setHeight(primaryScreenBounds.getHeight() * 0.75f);

        this.setMinWidth(primaryScreenBounds.getWidth() * 0.66f);
        this.setMinHeight(primaryScreenBounds.getHeight() * 0.75f);

        vBox = new BorderPane();
        vBox.setTop(menu);
        vBox.setCenter(rightSide);
        vBox.setLeft(canvas);

        canvas.widthProperty().bind(vBox.widthProperty().multiply(0.45f));
        canvas.heightProperty().bind(vBox.heightProperty().multiply(0.95f));

        changed();

        Scene scene = new Scene(vBox);
        Controls controls = new Controls(canvas);

        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            controls.onKeyPressed(keyCode);
        });

        setTitle(Values.NAME);
        setScene(scene);

        setOnCloseRequest((event) -> {
            Platform.exit();
            System.exit(0);
        });

        show();
    }
}