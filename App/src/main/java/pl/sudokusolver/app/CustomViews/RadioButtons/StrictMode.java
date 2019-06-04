package pl.sudokusolver.app.CustomViews.RadioButtons;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pl.sudokusolver.app.Values;

/**
 * Minimum Line Size layout in Advanced scene
 */
public class StrictMode extends VBox {

    private boolean strict = true;
    private RadioButton defaultOption;

    public StrictMode(){
        init();
    }

    /**
     * Function to return chosen scaling option
     * @return chosen scaling option
     */
    public boolean getValue(){
        return strict;
    }

    /**
     * Function to set strict option
     * @param strict new strict option
     */
    public void setValue(boolean strict){
        this.strict = strict;
        defaultOption.setSelected(strict);
    }

    /**
     * Function to initialize layout
     */
    private void init(){
        initStrict();

        getChildren().addAll(defaultOption);
        setSpacing(10);
        setAlignment(Pos.CENTER);
    }


    /**
     * Function to initialize RadioButton responsible for strict option
     */
    private void initStrict(){
        defaultOption = new RadioButton(Values.STRICT_MODE);
        defaultOption.setStyle("-fx-text-fill: black;");
        defaultOption.setSelected(true);
        defaultOption.selectedProperty().addListener((ObservableValue<? extends Boolean> obs, Boolean prev, Boolean now) -> {
            strict = now;
        });

    }

}
