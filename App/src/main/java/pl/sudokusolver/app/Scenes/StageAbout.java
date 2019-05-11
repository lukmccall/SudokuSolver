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
        if (Values.THEME == Theme.LIGHT){
            viewAbout.setStyle("-fx-background-color: #F0F0F0;");
        }
        else{
            viewAbout.setStyle("-fx-background-color: #34495E;");
        }
    }

    private void init(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        viewAbout = new ViewAbout();

        Scene secondScene = new Scene(viewAbout,
                primaryScreenBounds.getWidth() * 0.375f, primaryScreenBounds.getHeight() * 0.66f);

        this.setTitle(Values.AUTHORS);
        this.setScene(secondScene);

        this.setWidth(primaryScreenBounds.getWidth() * 0.375f);
        this.setHeight(primaryScreenBounds.getHeight() * 0.75f);

        this.setMinWidth(primaryScreenBounds.getWidth() * 0.375f);
        this.setMinHeight(primaryScreenBounds.getHeight() * 0.75f);
        this.setResizable(false);

        this.show();

    }
}
