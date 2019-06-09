package pl.sudokusolver.app.CustomViews.Texts;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pl.sudokusolver.app.Utilities;

/**
 * Error layout
 */
public class Error extends VBox {

    public Error(int error, double width){
        initError(error, width);
    }

    public Error(int error, String string, double width){
        initError(error, string, width);
    }

    public Error(String error, double width){
        initError(error, width);
    }

    /**
     * Function to initialize whole layout
     * @param errorID id of the error
     */
    private void initError(int errorID, double width){
        setSpacing(30);
        getChildren().addAll(initErrorText(errorID, width), initErrorDescription(errorID, width));
        setAlignment(Pos.CENTER);
    }

    /**
     * Function to initialize whole layout
     * @param string error string
     * @param errorID id of the error
     */
    private void initError(int errorID, String string, double width){
        setSpacing(30);
        getChildren().addAll(initErrorText(errorID, width), initErrorDescription(string, width));
        setAlignment(Pos.CENTER);
    }

    /**
     * Function to initialize whole layout
     * @param error string describing the error
     */
    private void initError(String error, double width){
        setSpacing(30);
        getChildren().addAll(initErrorDescription(error, width));
        setAlignment(Pos.CENTER);
    }

    /**
     * Function to initialize text with error value
     * @return  completely created text
     */
    private Text initErrorText(int errorID, double width){
        Text name;
        if (errorID < 10){
            name = new Text("Error 00" + errorID + ":");
        }
        else{
            name = new Text("Error 0" + errorID + ":");
        }

        name.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 24;");
        name.setTextAlignment(TextAlignment.CENTER);
        name.setWrappingWidth(width * 0.95f);
        return name;
    }

    /**
     * Function to initialize text describing the error
     * @return  completely created text
     */
    private Text initErrorDescription(int errorID, double width){
        Text description = new Text(Utilities.getError(errorID));
        description.setStyle("-fx-font-size: 18;");
        description.setTextAlignment(TextAlignment.CENTER);
        description.setWrappingWidth(width * 0.95f);
        return description;
    }

    /**
     * Function to initialize text describing the error
     * @return  completely created text
     */
    private Text initErrorDescription(String error, double width){
        Text description = new Text(error);
        description.setStyle("-fx-font-size: 18;");
        description.setTextAlignment(TextAlignment.CENTER);
        description.setWrappingWidth(width * 0.95f);
        return description;
    }

}
