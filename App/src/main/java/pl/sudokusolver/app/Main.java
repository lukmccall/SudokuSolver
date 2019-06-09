package pl.sudokusolver.app;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.sudokusolver.app.Scenes.StageError;
import pl.sudokusolver.app.Scenes.StageMain;

public class Main extends Application {

    @Override
    public void start(Stage stage){
        //check if java version is correct
        if (!Utilities.isJavaValid()) {
            new StageError(1, "Nie wykryto odpowiedniej wersji JAVY.");
            return;
        }
        Utilities.readFile();
        new StageMain();
    }

    public static void main(String[] args) {
        launch(args);
    }
}