package pl.sudokusolver.app.CustomViews;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import pl.sudokusolver.app.CustomViews.Texts.Error;
import pl.sudokusolver.app.Theme;
import pl.sudokusolver.app.Values;

public class ViewError extends VBox {

    public ViewError(int error, double width){
        init(error, width);
    }

    public ViewError(String error, double width){
        init(error, width);
    }

    private void init(String error, double width){
        setAlignment(Pos.CENTER);
        setSpacing(15);
        setTheme();

        getChildren().addAll(new Error(error, width));
    }

    private void init(int error, double width){
        setAlignment(Pos.CENTER);
        setSpacing(15);
        setTheme();

        getChildren().addAll(new Error(error, width));
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
