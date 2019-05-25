package pl.sudokusolver.app.CustomViews;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import pl.sudokusolver.app.CustomViews.Texts.About;
import pl.sudokusolver.app.Theme;
import pl.sudokusolver.app.Values;

/**
 * About scene layout
 */
public class ViewAbout extends VBox {

    public ViewAbout(){
        init();
    }

    /**
     * Function to initialize whole layout
     */
    private void init(){
        setAlignment(Pos.CENTER);
        setSpacing(15);
        setTheme();

        getChildren().addAll(new About());
    }

    /**
     * Function to set scene theme
     */
    public void setTheme(){
        if (Values.THEME == Theme.LIGHT){
            setStyle("-fx-background-color: #F0F0F0;");
        }
        else{
            setStyle("-fx-background-color: #34495E;");
        }
    }
}
