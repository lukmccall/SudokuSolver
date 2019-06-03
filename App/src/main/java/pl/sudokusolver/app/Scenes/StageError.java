package pl.sudokusolver.app.Scenes;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pl.sudokusolver.app.CustomViews.ViewError;
import pl.sudokusolver.app.Values;

/**
 * About screen available to open from menu
 */
public class StageError extends Stage {

    private ViewError viewError;
    private Scene errorScene;

    public StageError(int error){
        super();
        init(error);
    }

    public StageError(String error){
        super();
        init(error);
    }

    /**
     * Function to initialize whole screen
     * @param error error id
     */
    private void init(int error){
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        viewError = new ViewError(error, screenBounds.getWidth() * 0.25f);
        errorScene = new Scene(viewError, screenBounds.getWidth() * 0.25f, screenBounds.getHeight() * 0.5f);

        initModality(Modality.APPLICATION_MODAL);
        setDimensions(screenBounds);
        setTitle(Values.ERROR);
        setScene(errorScene);
        setResizable(false);
        change();
        show();
    }

    /**
     * Function to initialize whole screen
     * @param error error string
     */
    private void init(String error){
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        viewError = new ViewError(error, screenBounds.getWidth() * 0.25f);
        initScene();

        initModality(Modality.APPLICATION_MODAL);
        setDimensions(screenBounds);
        setTitle(Values.ERROR);
        setScene(errorScene);
        setResizable(false);
        change();
        show();
    }

    private void initScene(){
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        errorScene = new Scene(viewError, screenBounds.getWidth() * 0.25f, screenBounds.getHeight() * 0.5f);

        errorScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE || e.getCode() == KeyCode.BACK_SPACE ||
                    e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.DELETE) {
                close();
            }
        });
    }


    /**
     * Function to control theme changing and it's effects
     */
    private void change(){
        viewError.setTheme();
    }

    /**
     * Function to set dimensions of the main stage
     * @param screenBounds  bounds of the whole available screen
     */
    private void setDimensions(Rectangle2D screenBounds){
        setWidth(screenBounds.getWidth() * 0.25f);
        setHeight(screenBounds.getHeight() * 0.5f);

        setMinWidth(screenBounds.getWidth() * 0.25f);
        setMinHeight(screenBounds.getHeight() * 0.5f);
    }
}
