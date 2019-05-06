package App.src.sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import App.src.sample.CustomViews.Canvas;
import App.src.sample.CustomViews.Menu;
import App.src.sample.CustomViews.RightSide;
import App.src.sample.CustomViews.TitledPane;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

//import javafx.scene.control.Menu;

public class Main extends Application implements Test {

    private BorderPane vBox;
    private Canvas canvas;

    @Override
    public void start(Stage stage){
        Values.openStages = new OpenStages<>();

        Utilities.readFile();

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        RightSide temp = new RightSide(primaryScreenBounds.getWidth() * 0.75f, primaryScreenBounds.getHeight() * 0.75f);
        Menu menu = new Menu(stage, this);
        canvas = new Canvas();

        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());

        stage.setWidth(primaryScreenBounds.getWidth() * 0.75f);
        stage.setHeight(primaryScreenBounds.getHeight() * 0.75f);

        stage.setMinWidth(primaryScreenBounds.getWidth() * 0.66f);
        stage.setMinHeight(primaryScreenBounds.getHeight() * 0.75f);

        vBox = new BorderPane();
        vBox.setTop(menu);
        vBox.setRight(temp);
        vBox.setLeft(canvas);

        BorderPane.setMargin(temp, new Insets(stage.getHeight() / 20, stage.getWidth() / 40,
                stage.getHeight() / 20, stage.getWidth() / 40));

        if (Values.THEME == Theme.LIGHT){
            vBox.setStyle("-fx-background-color: #F0F0F0;");
        }
        else{
            vBox.setStyle("-fx-background-color: #34495E;");
        }

        temp.prefWidthProperty().bind(vBox.widthProperty().multiply(0.45f));
        temp.prefHeightProperty().bind(vBox.heightProperty().multiply(0.9f));
        canvas.widthProperty().bind(vBox.widthProperty().multiply(0.45f));
        canvas.heightProperty().bind(vBox.heightProperty().multiply(0.95f));
        temp.setPadding(new Insets(0,0,0,stage.getWidth() / 20));

        stage.setTitle(Values.NAME);
        stage.setScene(new Scene(vBox));
        stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                Values.openStages.focusStage(stage);
            }
        });


        stage.setOnCloseRequest((event) -> {
            Platform.exit();
            System.exit(0);
        });

        stage.show();
    }

    @Override
    public void changed(){
        canvas.update();
        if (Values.THEME == Theme.LIGHT){
            vBox.setStyle("-fx-background-color: #F0F0F0;");
        }
        else{
            vBox.setStyle("-fx-background-color: #34495E;");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}