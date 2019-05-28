package pl.sudokusolver.app;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.sudokusolver.app.Scenes.StageError;
import pl.sudokusolver.app.Scenes.StageMain;

public class Main extends Application {

    @Override
    public void start(Stage stage){
        if (!Utilities.isJavaValid()) {
            new StageError(1);
            return;
        }
        Utilities.readFile();
        new StageMain();
    }

    public static void main(String[] args) {
        launch(args);
    }
}