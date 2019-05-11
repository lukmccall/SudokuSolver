package pl.sudokusolver.app;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.sudokusolver.app.Scenes.StageMain;

//import javafx.scene.control.Menu;

public class Main extends Application {

    @Override
    public void start(Stage stage){
        Utilities.readFile();
        new StageMain();
    }

    public static void main(String[] args) {
        launch(args);
    }
}