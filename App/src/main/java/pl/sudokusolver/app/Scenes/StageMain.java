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

public class StageMain extends Stage implements MenuListener, ThemeChangeListener, Sender {

    private BorderPane vBox;
    private Canvas canvas;
    private RightSide temp;

    public StageMain(){
        init();
    }


    @Override
    public void solve() throws Exception{
        HttpUrl url = HttpUrl.parse(Values.SERVER_URL).newBuilder()
                             .addPathSegment("api").addPathSegment("solve").build();

        OkHttpClient client = new OkHttpClient();
        SolveRequest solveRequest = new SolveRequest(canvas.gameboard.getInitial());
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(Values.SERVER_REQUEST_TYPE, gson.toJson(solveRequest, solveRequest.getClass()));

        Request request = new Request.Builder()
                                     .url(url)
                                     .post(body)
                                     .build();

        try (Response response = client.newCall(request).execute()) {
            if(response.isSuccessful()){
                SudokuResponse sudokuResponse = gson.fromJson(response.body().charStream(),SudokuResponse.class);
                recievedSolved(sudokuResponse.sudoku);
            } else {
                ErrorResponse errorResponse = gson.fromJson(response.body().charStream(), ErrorResponse.class);
                System.out.println(errorResponse);
                //todo:> error handling - DJ Bomasz
            }
        }
    }

    /*
     * LUKASZ KOSMATY TUTAJ DODAJE SWOJ KOD OKAY? - OKEY ;)
     */
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
                recievedInitial(sudokuResponse.sudoku);
            } else {
                ErrorResponse errorResponse = gson.fromJson(response.body().charStream(), ErrorResponse.class);
                System.out.println(errorResponse);
                //todo:> error handling - DJ Bomasz
            }
        }

    }

    void recievedSolved(int[][] array){
        canvas.gameboard.modifyPlayer(array);
        canvas.update();
    }

    void recievedInitial(int[][] array){
        canvas.gameboard.modifyInitial(array);
        canvas.update();
    }

    @Override
    public void clear(){
        canvas.gameboard.clear();
        canvas.update();
    }


    @Override
    public void changed(){
        canvas.update();
        if (temp != null)
            temp.change();
        if (Values.THEME == Theme.LIGHT){
            vBox.setStyle("-fx-background-color: #F0F0F0;");
        }
        else{
            vBox.setStyle("-fx-background-color: #34495E;");
        }
    }

    private void init(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        temp = new RightSide(this, primaryScreenBounds.getWidth() * 0.75f, primaryScreenBounds.getHeight() * 0.75f);
        Menu menu = new Menu(this, this, this);
        canvas = new Canvas();

        canvas.setOnMouseClicked((event) -> {
            double mouseX = event.getX();
            double mouseY = event.getY() - canvas.offset_y;

            canvas.playerCol = (int) (mouseX / canvas.SIZE_REC);
            canvas.playerRow = (int) (mouseY / canvas.SIZE_REC);

            canvas.update();
        });

        this.setX(primaryScreenBounds.getMinX());
        this.setY(primaryScreenBounds.getMinY());

        this.setWidth(primaryScreenBounds.getWidth() * 0.75f);
        this.setHeight(primaryScreenBounds.getHeight() * 0.75f);

        this.setMinWidth(primaryScreenBounds.getWidth() * 0.66f);
        this.setMinHeight(primaryScreenBounds.getHeight() * 0.75f);

        vBox = new BorderPane();
        vBox.setTop(menu);
        vBox.setCenter(temp);
        vBox.setLeft(canvas);

        canvas.widthProperty().bind(vBox.widthProperty().multiply(0.45f));
        canvas.heightProperty().bind(vBox.heightProperty().multiply(0.95f));

        changed();

        Scene scene = new Scene(vBox);

        scene.setOnKeyPressed(e -> {
            KeyCode keyCode = e.getCode();
            if (keyCode == KeyCode.DIGIT1 || keyCode == KeyCode.NUMPAD1) {
                canvas.gameboard.modifyInitial(1, canvas.playerRow, canvas.playerCol);
                canvas.update();
            }
            else if (keyCode == KeyCode.DIGIT2 || keyCode == KeyCode.NUMPAD2) {
                canvas.gameboard.modifyInitial(2, canvas.playerRow, canvas.playerCol);
                canvas.update();
            }
            else if (keyCode == KeyCode.DIGIT3 || keyCode == KeyCode.NUMPAD3) {
                canvas.gameboard.modifyInitial(3, canvas.playerRow, canvas.playerCol);
                canvas.update();
            }
            else if (keyCode == KeyCode.DIGIT4 || keyCode == KeyCode.NUMPAD4) {
                canvas.gameboard.modifyInitial(4, canvas.playerRow, canvas.playerCol);
                canvas.update();
            }
            else if (keyCode == KeyCode.DIGIT5 || keyCode == KeyCode.NUMPAD5) {
                canvas.gameboard.modifyInitial(5, canvas.playerRow, canvas.playerCol);
                canvas.update();
            }
            else if (keyCode == KeyCode.DIGIT6 || keyCode == KeyCode.NUMPAD6) {
                canvas.gameboard.modifyInitial(6, canvas.playerRow, canvas.playerCol);
                canvas.update();
            }
            else if (keyCode == KeyCode.DIGIT7 || keyCode == KeyCode.NUMPAD7) {
                canvas.gameboard.modifyInitial(7, canvas.playerRow, canvas.playerCol);
                canvas.update();
            }
            else if (keyCode == KeyCode.DIGIT8 || keyCode == KeyCode.NUMPAD8) {
                canvas.gameboard.modifyInitial(8, canvas.playerRow, canvas.playerCol);
                canvas.update();
            }
            else if (keyCode == KeyCode.DIGIT9 || keyCode == KeyCode.NUMPAD9) {
                canvas.gameboard.modifyInitial(9, canvas.playerRow, canvas.playerCol);
                canvas.update();
            }
            else if (keyCode == KeyCode.BACK_SPACE || keyCode == KeyCode.DELETE){
                canvas.gameboard.modifyInitial(0, canvas.playerRow, canvas.playerCol);
                canvas.update();
            }

        });

        this.setTitle(Values.NAME);
        this.setScene(scene);

        this.setOnCloseRequest((event) -> {
            Platform.exit();
            System.exit(0);
        });

        this.show();
    }
}