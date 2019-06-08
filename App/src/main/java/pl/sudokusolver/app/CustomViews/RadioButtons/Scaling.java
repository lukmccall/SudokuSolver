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
public class Scaling extends VBox {

    private String scaling = "Fixed Width Resize";
    private RadioButton defaultOption;
    private ToggleGroup toggleGroup;

    public Scaling(){
        init();
    }

    /**
     * Function to return chosen scaling option
     * @return chosen scaling option
     */
    public String getValue(){
        return scaling;
    }

    /**
     * Function to set scaling option
     * @param scaling new scaling option
     */
    public void setValue(String scaling){
        this.scaling = scaling;
        defaultOption.setSelected(true);
    }

    /**
     * Function to initialize layout
     */
    protected void init(){
        Text text = initText();
        toggleGroup = new ToggleGroup();
        initToggleGroup();

        getChildren().addAll(text, defaultOption, initMaxAxisResize(), initNone());
        setSpacing(10);
        //setAlignment(Pos.CENTER);
    }

    /**
     * Function to initialize text to identify toggle group responsible for Scaling option
     * @return completely created text
     */
    private Text initText(){
        Text scaling = new Text(Values.SCALING);
        scaling.setTextAlignment(TextAlignment.CENTER);
        return scaling;
    }

    /**
     * Function to initialize toggle group for changing Scaling option
     */
    private void initToggleGroup(){
        initFixedWidthScaling();

        toggleGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov,
                Toggle old_toggle, Toggle new_toggle) ->
                    scaling = toggleGroup.getSelectedToggle().getUserData().toString());
    }

    /**
     * Function to initialize RadioButton responsible for Fixed Width Scaling option
     */
    private void initFixedWidthScaling(){
        defaultOption = new RadioButton(Values.FIXED_WIDTH_SCALING);
        defaultOption.setUserData(Values.FIXED_WIDTH_SCALING);
        defaultOption.setToggleGroup(toggleGroup);
        defaultOption.setStyle("-fx-text-fill: black;");
        defaultOption.setSelected(true);

    }

    /**
     * Function to initialize RadioButton responsible for Max Axis Resize option
     * @return radioButton that is already added to toggle group
     */
    private RadioButton initMaxAxisResize(){
        RadioButton radioButton = new RadioButton(Values.MAX_AXIS_RESIZE);
        radioButton.setUserData(Values.MAX_AXIS_RESIZE);
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setStyle("-fx-text-fill: black;");

        return radioButton;
    }

    /**
     * Function to initialize RadioButton responsible for None option
     * @return radioButton that is already added to toggle group
     */
    private RadioButton initNone(){
        RadioButton radioButton = new RadioButton(Values.NONE);
        radioButton.setUserData(Values.NONE);
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setStyle("-fx-text-fill: black;");

        return radioButton;
    }
}
