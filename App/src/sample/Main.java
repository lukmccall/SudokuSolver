package App.src.sample;

import App.src.sample.Scenes.StageMain;
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

public class Main extends Application {

    @Override
    public void start(Stage stage){
        Values.openStages = new OpenStages<>();
        Utilities.readFile();
        new StageMain();
    }

    public static void main(String[] args) {
        launch(args);
    }
}