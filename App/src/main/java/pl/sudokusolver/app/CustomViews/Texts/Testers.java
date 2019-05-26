package pl.sudokusolver.app.CustomViews.Texts;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pl.sudokusolver.app.Values;

/**
 * System Engineer layout
 */
public class Testers extends VBox {

    public Testers(){
        initTesters();
    }

    /**
     * Function to initialize whole layout
     */
    private void initTesters(){
        getChildren().addAll(initTestersText(), initTestersName());
        setAlignment(Pos.CENTER);
    }

    /**
     * Function to initialize text saying 'Testers'
     * @return  completely created text
     */
    private Text initTestersText(){
        Text testersText = new Text(Values.TESTERS);
        testersText.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 24;");
        return testersText;
    }

    /**
     * Function to initialize text saying names of Testers
     * @return  completely created text
     */
    private Text initTestersName(){
        Text testersNames = new Text(Values.TESTERS_NAMES);
        testersNames.setStyle("-fx-font-size: 18;");
        testersNames.setTextAlignment(TextAlignment.CENTER);
        return testersNames;
    }
}
