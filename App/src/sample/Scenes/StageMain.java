package App.src.sample.Scenes;

import App.src.sample.*;
import App.src.sample.CustomViews.Canvas;
import App.src.sample.CustomViews.Menu;
import App.src.sample.CustomViews.RightSide;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
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
    /*
     * LUKASZ KOSMATY TUTAJ DODAJE SWOJ KOD OKAY?
     */
    @Override
    public void send(BufferedImage image, Parameters parameters){
        recieved(null);
    }

    public void recieved(int[][] array){
        int[][] solution = new int[][]
                {
                        {5,3,8,4,6,1,7,9,2},
                        {6,9,7,3,2,5,8,1,4},
                        {2,1,4,7,8,9,5,6,3},
                        {9,4,1,2,7,8,6,3,5},
                        {7,6,2,1,5,3,9,4,8},
                        {8,5,3,9,4,6,1,2,7},
                        {3,8,9,5,1,2,4,7,6},
                        {4,2,6,8,9,7,3,5,1},
                        {1,7,5,6,3,4,2,8,9}
                };

        canvas.gameboard.modifyPlayer(solution);
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

        this.setTitle(Values.NAME);
        this.setScene(new Scene(vBox));
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
