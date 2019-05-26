package pl.sudokusolver.app.CustomViews.Texts;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pl.sudokusolver.app.Values;

/**
 * System Engineer layout
 */
public class About extends VBox {

    public About(){
        initAbout();
    }

    /**
     * Function to initialize whole layout
     */
    private void initAbout(){
        setSpacing(30);
        getChildren().addAll(initNameText(), initDescriptionText());
        setAlignment(Pos.CENTER);
    }

    /**
     * Function to initialize text with application name
     * @return  completely created text
     */
    private Text initNameText(){
        Text name = new Text(Values.NAME);
        name.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 24;");
        return name;
    }

    /**
     * Function to initialize text describing what this application is about
     * @return  completely created text
     */
    private Text initDescriptionText(){
        Text description = new Text(Values.DESCRIPTION);
        description.setStyle("-fx-font-size: 18;");
        description.setTextAlignment(TextAlignment.CENTER);
        description.wrappingWidthProperty().bind(widthProperty());
        return description;
    }
}
