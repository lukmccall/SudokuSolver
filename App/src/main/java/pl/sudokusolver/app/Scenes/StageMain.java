package pl.sudokusolver.app.Scenes;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import okhttp3.*;
import pl.sudokusolver.app.Api.ErrorResponse;
import pl.sudokusolver.app.Api.SolveRequest;
import pl.sudokusolver.app.Api.SudokuResponse;
import pl.sudokusolver.app.*;
import pl.sudokusolver.app.CustomViews.Canvas;
import pl.sudokusolver.app.CustomViews.Menu;
import pl.sudokusolver.app.CustomViews.RightSide;
import pl.sudokusolver.app.Listeners.MenuListener;
import pl.sudokusolver.app.Listeners.Sender;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Controls appearance of main screen and logic connected with sending and receiving sudoku
 */
public class StageMain extends Stage implements MenuListener, Sender {

    private BorderPane vBox;
    private Canvas canvas;
    private RightSide rightSide;
    private Menu menu;
    private Controls controls;

    public StageMain(){
        init();
    }

    /**
     * Function that sends solve request and waits for a respond from server
     * @throws Exception in case anything went badly
     */
    @Override
    public void solve() throws Exception{
        int[][] sudoku;
        try{
            sudoku = canvas.getInitial();
        }
        catch (IllegalArgumentException e){
            new StageError("Sudoku is not valid!");
            return;
        }
        
        block();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {

            HttpUrl url = HttpUrl.parse(Values.SERVER_URL).newBuilder()
                    .addPathSegment("api").addPathSegment("solve").build();

            OkHttpClient client = new OkHttpClient.Builder()
                                                .connectTimeout(10, TimeUnit.SECONDS)
                                                .writeTimeout(30, TimeUnit.SECONDS)
                                                .readTimeout(30, TimeUnit.SECONDS)
                                                .build();

            SolveRequest solveRequest = new SolveRequest(sudoku);
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
                    Platform.runLater(() -> new StageError(errorResponse.errorMessage));
                }
            } catch (ConnectException e){
                Platform.runLater(() -> new StageError("Nie udało się połączyć z serwerem."));
            } catch (SocketTimeoutException e){
                Platform.runLater(() -> new StageError("Serwer nie odpowiedział wystarczająco szybko."));
            } catch (IOException e) {
                Platform.runLater(() -> new StageError("Wystąpił błąd z serwerem."));
            } catch (Exception e) {
                Platform.runLater(() -> new StageError("Nieznany błąd."));
            }  finally {
                unblock();
            }
        });
    }

    /**
     * Function that sends recognize request and waits for a respond from server
     * @throws Exception in case anything went badly
     */
    @Override
    public void send(BufferedImage image, Parameters parameters) throws Exception{
        block();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            HttpUrl url = HttpUrl.parse(Values.SERVER_URL).newBuilder()
                    .addPathSegment("api").addPathSegment("extractfromimg").build();

            OkHttpClient client = new OkHttpClient.Builder()
                                                   .connectTimeout(10, TimeUnit.SECONDS)
                                                    .writeTimeout(30, TimeUnit.SECONDS)
                                                    .readTimeout(30, TimeUnit.SECONDS)
                                                    .build();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                ImageIO.write(image, "jpg", outputStream);
            } catch (IOException e) {
                Platform.runLater(() -> new StageError("Couldn't open file"));
                unblock();
                return;
            }
            Gson gson = new Gson();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("sudoku", "sudoku.jpg",
                            RequestBody.create(Values.SERVER_IMG_TYPE, outputStream.toByteArray()))
                    .addFormDataPart("lineThreshold", String.valueOf(parameters.getLineThreshold()))
                    .addFormDataPart("minLineSize", String.valueOf(parameters.getMinLineSize()))
                    .addFormDataPart("lineGap", String.valueOf(parameters.getLineGap()))
                    .addFormDataPart("blurSize", String.valueOf(parameters.getBlurSize()))
                    .addFormDataPart("blurBlockSize", String.valueOf(parameters.getBlurBlockSize()))
                    .addFormDataPart("blurC", String.valueOf(parameters.getBlurC()))
                    .addFormDataPart("scaling", parameters.getScaling())
                    .addFormDataPart("recognizer", parameters.getRecognition())
                    .addFormDataPart("strictMode", parameters.getStrictMode() ? "true" : "false")
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                unblock();
                if(response.isSuccessful()){
                    SudokuResponse sudokuResponse = gson.fromJson(response.body().charStream(),SudokuResponse.class);
                    receivedInitial(sudokuResponse.sudoku);
                } else {
                    ErrorResponse errorResponse = gson.fromJson(response.body().charStream(), ErrorResponse.class);
                    Platform.runLater(() -> new StageError(errorResponse.errorMessage));
                }
            } catch (ConnectException e){
                Platform.runLater(() -> new StageError("Nie udało się połączyć z serwerem."));
            }catch (SocketTimeoutException e){
                Platform.runLater(() -> new StageError("Serwer nie odpowiedział wystarczająco szybko."));
            } catch (IOException e) {
                Platform.runLater(() -> new StageError("Wystąpił błąd z serwerem."));
            } catch (Exception e) {
                Platform.runLater(() -> new StageError("Nieznany błąd."));
            } finally {
                unblock();
            }

        });

    }

    /**
     * Function to block user from sending requests to server when we are waiting for one
     */
    private void block(){
        rightSide.blockButtons();
        Singleton.getInstance().block();
    }

    /**
     * Function to unblock user from sending requests to server when we are waiting for one
     */
    private void unblock(){
        rightSide.unblockButtons();
        Singleton.getInstance().unblock();
    }

    /**
     * Function that controls what happens when we receive response after send request
     * @param array array received from server
     */
    private void receivedSolved(int[][] array){
        canvas.modifySolution(array);
    }

    /**
     * Function that controls what happens when we receive response after recognize request
     * @param array array received from server
     */
    private void receivedInitial(int[][] array){
        canvas.modifyInitial(array);
    }

    /**
     * Function inherited from MenuListener, it is called after clear is clicked in menu
     */
    @Override
    public void clear(){
        canvas.clear();
    }

    /**
     * Function inherited from MenuListener, it updates whole application theme
     */
    @Override
    public void change(){
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

    /**
     * Function inherited from MenuListener, it closes the whole application
     */
    @Override
    public void exit(){
        close();
        Platform.exit();
        System.exit(0);
    }

    /**
     * Function to initialize screen appearance
     */
    private void init(){
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        rightSide = new RightSide(this, screenBounds.getWidth() * 0.75f, screenBounds.getHeight() * 0.75f);
        menu = new Menu(this);
        canvas = new Canvas();
        vBox = new BorderPane();

        initCanvas();
        initVBox();
        initScene();
    }

    /**
     * Function to initialize main scene
     */
    private void initScene(){
        controls = new Controls(canvas);
        Scene scene = new Scene(vBox);

        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            controls.onKeyPressed(keyCode);
        });

        initStage(scene);
    }

    /**
     * A function to initialize main stage of the application
     * @param scene main scene previously initialized
     */
    private void initStage(Scene scene){
        setTitle(Values.NAME);

        setDimensions(Screen.getPrimary().getVisualBounds());

        change();
        setScene(scene);

        setOnCloseRequest((event) -> {
            Platform.exit();
            System.exit(0);
        });

        show();
    }

    /**
     * Function to set dimensions of the main stage
     * @param screenBounds  bounds of the whole available screen
     */
    private void setDimensions(Rectangle2D screenBounds){
        setX(screenBounds.getMinX());
        setY(screenBounds.getMinY());

        setWidth(screenBounds.getWidth() * 0.75f);
        setHeight(screenBounds.getHeight() * 0.75f);

        setMinWidth(screenBounds.getWidth() * 0.66f);
        setMinHeight(screenBounds.getHeight() * 0.75f);
    }

    /**
     * Function to initialize canvas
     */
    private void initCanvas(){
        canvas.widthProperty().bind(vBox.widthProperty().multiply(0.45f));
        canvas.heightProperty().bind(vBox.heightProperty().multiply(0.95f));

        canvas.setOnMouseClicked((event) -> {
            double mouseX = event.getX();// - canvas.getOffsetX();
            double mouseY = event.getY() - canvas.getOffsetY();

            canvas.mouseClick(mouseX, mouseY);
        });
    }

    /**
     * Function to initialize main VBox
     */
    private void initVBox(){
        vBox.setTop(menu);
        vBox.setCenter(rightSide);
        vBox.setLeft(canvas);
        BorderPane.setMargin(canvas, new Insets(0,0,0,20));
    }
}