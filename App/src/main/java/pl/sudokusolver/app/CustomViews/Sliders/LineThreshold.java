package pl.sudokusolver.app.CustomViews.Sliders;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pl.sudokusolver.app.Values;

/**
 * Line Threshold layout in Advanced scene
 */
public class LineThreshold extends VBox {

    private int lineThreshold = 50;
    private Slider slider;

    public LineThreshold(){
        init();
    }

    /**
     * Function to return value
     * @return Line Threshold value
     */
    public int getValue(){
        return lineThreshold;
    }

    /**
     * Function to set value
     * @param lineThreshold new Line Threshold value
     */
    public void setValue(int lineThreshold){
        this.lineThreshold = lineThreshold;
        slider.setValue(lineThreshold);
    }

    /**
     * Function to initialize layout
     */
    protected void init(){
        Text text = initText();
        Text value = initValue();
        slider = initSlider(value);

        getChildren().addAll(text, slider, value);
        setSpacing(10);
        setAlignment(Pos.CENTER);
    }

    /**
     * Function to initialize text to identify which slider is responsible for Line Threshold
     * @return completely created text
     */
    private Text initText(){
        Text lineThreshold = new Text(Values.LINE_THRESHOLD);
        lineThreshold.setTextAlignment(TextAlignment.CENTER);
        return lineThreshold;
    }

    /**
     * Function to initialize text indicating what value of Line Threshold is chosen
     * @return completely created text
     */
    private Text initValue(){
        Text lineThreshold = new Text("50");
        lineThreshold.setTextAlignment(TextAlignment.CENTER);
        return lineThreshold;
    }

    /**
     * Function to initialize slider for changing Line Threshold value
     * @param value text that will be changed
     * @return completely created slider
     */
    private Slider initSlider(Text value){
        Slider slider = new Slider(1, 50, 50);
        slider.valueProperty().addListener((ObservableValue<? extends Number> observable,
                                                   Number oldValue, Number newValue) -> {

            value.setText(String.valueOf(newValue.intValue()));
            lineThreshold = newValue.intValue();
        });

        return slider;
    }
}
