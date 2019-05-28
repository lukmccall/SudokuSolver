package pl.sudokusolver.app.Scenes;

import javafx.stage.Modality;
import pl.sudokusolver.app.CustomViews.ViewAbout;
import pl.sudokusolver.app.Values;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * About screen available to open from menu
 */
public class StageAbout extends Stage {

    private ViewAbout viewAbout;
    private Scene aboutScene;

    public StageAbout(){
        super();
        init();
    }

    /**
     * Function to initialize whole screen
     */
    private void init(){
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        viewAbout = new ViewAbout();
        aboutScene = new Scene(viewAbout, screenBounds.getWidth() * 0.375f, screenBounds.getHeight() * 0.66f);

        initModality(Modality.APPLICATION_MODAL);
        setDimensions(screenBounds);
        setTitle(Values.AUTHORS);
        setScene(aboutScene);
        setResizable(false);
        change();
        show();
    }

    /**
     * Function to control theme changing and it's effects
     */
    public void change(){
        viewAbout.setTheme();
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
