package pl.sudokusolver.app.Scenes;

import pl.sudokusolver.app.CustomViews.ViewAuthors;
import pl.sudokusolver.app.Theme;
import pl.sudokusolver.app.Values;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StageAuthors extends Stage {

    private ViewAuthors viewAuthors;


    public StageAuthors(){
        super();
        init();
    }

    public void change(){
        if (Values.THEME == Theme.LIGHT){
            viewAuthors.setStyle("-fx-background-color: #F0F0F0;");
        }
        else{
            viewAuthors.setStyle("-fx-background-color: #34495E;");
        }
    }

    private void init(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        viewAuthors = new ViewAuthors();

        Scene secondScene = new Scene(viewAuthors,
                primaryScreenBounds.getWidth() * 0.375f, primaryScreenBounds.getHeight() * 0.66f);

        this.setTitle(Values.AUTHORS);
        this.setScene(secondScene);

        this.setWidth(primaryScreenBounds.getWidth() * 0.375f);
        this.setHeight(primaryScreenBounds.getHeight() * 0.75f);

        this.setMinWidth(primaryScreenBounds.getWidth() * 0.375f);
        this.setMinHeight(primaryScreenBounds.getHeight() * 0.75f);
        this.setResizable(false);

        this.change();
        this.show();
    }
}
