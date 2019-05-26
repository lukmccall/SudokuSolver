package pl.sudokusolver.app.CustomViews.Texts;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pl.sudokusolver.app.Values;

/**
 * System Engineer layout
 */
public class Programmers extends VBox {

    public Programmers(){
        initProgrammers();
    }

    /**
     * Function to initialize whole layout
     */
    private void initProgrammers(){
        getChildren().addAll(initProgrammersText(), initProgrammersName());
        setAlignment(Pos.CENTER);
    }

    /**
     * Function to initialize text saying 'Programmers'
     * @return  completely created text
     */
    private Text initProgrammersText(){
        Text programmersText = new Text(Values.PROGRAMMERS);
        programmersText.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 24;");
        return programmersText;
    }

    /**
     * Function to initialize text saying names of Programmers
     * @return  completely create text
     */
    private Text initProgrammersName(){
        Text programmersNames = new Text(Values.PROGRAMMERS_NAMES);
        programmersNames.setStyle("-fx-font-size: 18;");
        programmersNames.setTextAlignment(TextAlignment.CENTER);
        return programmersNames;
    }
}
