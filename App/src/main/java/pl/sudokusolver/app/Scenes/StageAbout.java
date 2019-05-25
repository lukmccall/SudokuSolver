package pl.sudokusolver.app.Scenes;

import pl.sudokusolver.app.CustomViews.ViewAbout;
import pl.sudokusolver.app.Theme;
import pl.sudokusolver.app.Values;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StageAbout extends Stage {

    private ViewAbout viewAbout;

    public StageAbout(){
        super();
        init();
    }


    public void change(){
        viewAbout.setTheme();
    }

    private void init(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        viewAbout = new ViewAbout();

        Scene secondScene = new Scene(viewAbout,
                primaryScreenBounds.getWidth() * 0.375f, primaryScreenBounds.getHeight() * 0.66f);

        setTitle(Values.AUTHORS);
        setScene(secondScene);

        setWidth(primaryScreenBounds.getWidth() * 0.375f);
        setHeight(primaryScreenBounds.getHeight() * 0.75f);

        setMinWidth(primaryScreenBounds.getWidth() * 0.375f);
        setMinHeight(primaryScreenBounds.getHeight() * 0.75f);
        setResizable(false);

        show();

    }
}
