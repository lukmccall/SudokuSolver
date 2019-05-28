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

    private int error;

    public Error(int error){
        this.error = error;
        initAbout();
    }

    /**
     * Function to initialize whole layout
     */
    private void initAbout(){
        setSpacing(30);
        getChildren().addAll(initErrorText(), initErrorDescription());
        setAlignment(Pos.CENTER);
    }

    /**
     * Function to initialize text with error value
     * @return  completely created text
     */
    private Text initErrorText(){
        Text name = new Text("Error 00" + error + ":");
        name.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 24;");
        return name;
    }

    /**
     * Function to initialize text describing the error
     * @return  completely created text
     */
    private Text initErrorDescription(){
        Text description = new Text(Utilities.getError(error));
        description.setStyle("-fx-font-size: 18;");
        description.setTextAlignment(TextAlignment.CENTER);
        description.wrappingWidthProperty().bind(widthProperty());
        return description;
    }
}
