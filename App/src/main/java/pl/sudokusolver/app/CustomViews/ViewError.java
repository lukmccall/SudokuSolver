package pl.sudokusolver.app.CustomViews;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import pl.sudokusolver.app.CustomViews.Texts.Error;
import pl.sudokusolver.app.Theme;
import pl.sudokusolver.app.Values;

/**
 * Layout of Error
 */
public class ViewError extends VBox {

    public ViewError(int error, double width){
        init(error, width);
    }

    public ViewError(int error, String string, double width){
        init(error, string, width);
    }

    public ViewError(String error, double width){
        init(error, width);
    }

    /**
     * Function to initialize error layout
     * @param error error message
     * @param width maximal width of error window
     */
    private void init(String error, double width){
        setAlignment(Pos.CENTER);
        setSpacing(15);
        setTheme();

        getChildren().addAll(new Error(error, width));
    }

    /**
     * Function to initialize error layout
     * @param error error id
     * @param string error string
     * @param width maximal width of error window
     */
    private void init(int error, String string, double width){
        setAlignment(Pos.CENTER);
        setSpacing(15);
        setTheme();

        getChildren().addAll(new Error(error, string, width));
    }

    /**
     * Function to initialize error layout
     * @param error error id
     * @param width maximal width of error window
     */
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
