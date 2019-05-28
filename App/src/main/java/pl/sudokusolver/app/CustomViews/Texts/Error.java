package pl.sudokusolver.app.CustomViews.Texts;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pl.sudokusolver.app.Utilities;
import pl.sudokusolver.app.Values;

/**
 * Error layout
 */
public class Error extends VBox {

    public Error(int error){
        initAbout(error);
    }

    public Error(String error){
        initAbout(error);
    }

    /**
     * Function to initialize whole layout
     */
    private void initAbout(int errorID){
        setSpacing(30);
        getChildren().addAll(initErrorText(errorID), initErrorDescription(errorID));
        setAlignment(Pos.CENTER);
    }

    private void initAbout(String error){
        setSpacing(30);
        getChildren().addAll(initErrorDescription(error));
        setAlignment(Pos.CENTER);
    }

    /**
     * Function to initialize text with error value
     * @return  completely created text
     */
    private Text initErrorText(int errorID){
        Text name = new Text("Error 00" + errorID + ":");
        name.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 24;");
        return name;
    }

    /**
     * Function to initialize text describing the error
     * @return  completely created text
     */
    private Text initErrorDescription(int errorID){
        Text description = new Text(Utilities.getError(errorID));
        description.setStyle("-fx-font-size: 18;");
        description.setTextAlignment(TextAlignment.CENTER);
        description.wrappingWidthProperty().bind(widthProperty());
        return description;
    }

    /**
     * Function to initialize text describing the error
     * @return  completely created text
     */
    private Text initErrorDescription(String error){
        Text description = new Text(error);
        description.setStyle("-fx-font-size: 18;");
        description.setTextAlignment(TextAlignment.CENTER);
        description.wrappingWidthProperty().bind(widthProperty());
        return description;
    }

}
