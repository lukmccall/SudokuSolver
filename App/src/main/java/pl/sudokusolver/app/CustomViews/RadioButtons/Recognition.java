package pl.sudokusolver.app.CustomViews.RadioButtons;

import javafx.beans.value.ObservableValue;
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
public class Recognition extends VBox {

    private String recognition = "SVM";
    private RadioButton defaultOption;
    private ToggleGroup toggleGroup;

    public Recognition(){
        init();
    }

    /**
     * Function to return chosen recognition option
     * @return chosen recognition option
     */
    public String getValue(){
        return recognition;
    }

    /**
     * Function to set recognition option
     * @param recognition new recognition option
     */
    public void setValue(String recognition){
        this.recognition = recognition;
        defaultOption.setSelected(true);
    }

    /**
     * Function to initialize layout
     */
    private void init(){
        Text text = initText();
        toggleGroup = new ToggleGroup();
        initToggleGroup();

        getChildren().addAll(text, defaultOption, initTesseract(), initAnn());
        setSpacing(10);
    }

    /**
     * Function to initialize text to identify toggle group responsible for Recognition option
     * @return completely created text
     */
    private Text initText(){
        Text scaling = new Text(Values.RECOGNITION);
        scaling.setTextAlignment(TextAlignment.CENTER);
        return scaling;
    }

    /**
     * Function to initialize toggle group for changing Recognition option
     */
    private void initToggleGroup(){
        initSVM();

        toggleGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov,
                Toggle old_toggle, Toggle new_toggle) ->
                    recognition = toggleGroup.getSelectedToggle().getUserData().toString());
    }

    /**
     * Function to initialize RadioButton responsible for SVM option
     */
    private void initSVM(){
        defaultOption = new RadioButton(Values.SVM);
        defaultOption.setUserData(Values.SVM);
        defaultOption.setToggleGroup(toggleGroup);
        defaultOption.setStyle("-fx-text-fill: black;");
        defaultOption.setSelected(true);
    }

    /**
     * Function to initialize RadioButton responsible for Tesseract option
     * @return radioButton that is already added to toggle group
     */
    private RadioButton initTesseract(){
        RadioButton radioButton = new RadioButton(Values.TESSERACT);
        radioButton.setUserData(Values.TESSERACT);
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setStyle("-fx-text-fill: black;");

        return radioButton;
    }

    /**
     * Function to initialize RadioButton responsible for ANN option
     * @return radioButton that is already added to toggle group
     */
    private RadioButton initAnn(){
        RadioButton radioButton = new RadioButton(Values.ANN);
        radioButton.setUserData(Values.ANN);
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setStyle("-fx-text-fill: black;");

        return radioButton;
    }
}
