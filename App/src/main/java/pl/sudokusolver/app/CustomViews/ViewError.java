package pl.sudokusolver.app.CustomViews;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import pl.sudokusolver.app.CustomViews.Texts.Error;
import pl.sudokusolver.app.Theme;
import pl.sudokusolver.app.Values;

public class ViewError extends VBox {

    public ViewError(int error){
        init(error);
    }

    public ViewError(String error){
        init(error);
    }

    private void init(String error){
        setAlignment(Pos.CENTER);
        setSpacing(15);
        setTheme();

        getChildren().addAll(new Error(error));
    }

    private void init(int error){
        setAlignment(Pos.CENTER);
        setSpacing(15);
        setTheme();

        getChildren().addAll(new Error(error));
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
