package pl.sudokusolver.app.Scenes;

import pl.sudokusolver.app.CustomViews.ViewAuthors;
import pl.sudokusolver.app.Values;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Authors screen available to open from menu
 */
public class StageAuthors extends Stage {

    private ViewAuthors viewAuthors;
    private Scene authorsScene;

    public StageAuthors(){
        super();
        init();
    }

    /**
     * Function to initialize whole screen
     */
    private void init(){
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        viewAuthors = new ViewAuthors();

        authorsScene = new Scene(viewAuthors, screenBounds.getWidth() * 0.375f, screenBounds.getHeight() * 0.66f);

        setDimensions(screenBounds);
        setTitle(Values.AUTHORS);
        setScene(authorsScene);
        setResizable(false);
        change();
        show();
    }

    /**
     * Function to control theme changing and it's effects
     */
    public void change(){
        viewAuthors.setTheme();
    }

    /**
     * Function to set dimensions of the main stage
     * @param screenBounds  bounds of the whole available screen
     */
    private void setDimensions(Rectangle2D screenBounds){
        setWidth(screenBounds.getWidth() * 0.375f);
        setHeight(screenBounds.getHeight() * 0.75f);

        setMinWidth(screenBounds.getWidth() * 0.375f);
        setMinHeight(screenBounds.getHeight() * 0.75f);
    }
}
