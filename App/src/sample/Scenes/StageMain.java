package App.src.sample.Scenes;

import App.src.sample.*;
import App.src.sample.CustomViews.Canvas;
import App.src.sample.CustomViews.Menu;
import App.src.sample.CustomViews.RightSide;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;

public class StageMain extends Stage implements Test, Sender {

    private BorderPane vBox;
    private Canvas canvas;
    private RightSide temp;

    public StageMain(){
        init();
    }


    //TODO ukasz ukasz ukasz
    @Override
    public void solve(int [][] sudoku){
        //recievedSolved(outputFunkcji);
    }

    //TODO ukasz ukasz ukasz
    /*
     * LUKASZ KOSMATY TUTAJ DODAJE SWOJ KOD OKAY?
     */
    @Override
    public void send(BufferedImage image, Parameters parameters){
        //recievedInitial(outputFunkcji);
    }

    public void recievedSolved(int[][] array){
        canvas.gameboard.modifyPlayer(array);
        canvas.update();
    }

    public void recievedInitial(int[][] array){
        canvas.gameboard.modifyInitial(array);
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
        Menu menu = new Menu(this, this);
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

        });

        this.setTitle(Values.NAME);
        this.setScene(scene);
        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                Values.openStages.focusStage(this);
            }
        });

        this.setOnCloseRequest((event) -> {
            Platform.exit();
            System.exit(0);
        });

        this.show();
    }
}
