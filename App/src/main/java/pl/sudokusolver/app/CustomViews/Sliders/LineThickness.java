package pl.sudokusolver.app.CustomViews.Sliders;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pl.sudokusolver.app.Values;

import java.util.Locale;

/**
 * Line Thickness layout in Advanced scene
 */
public class LineThickness extends VBox {

    private double lineThickness = 1.0;
    private Slider slider;

    public LineThickness(){
        init();
    }

    /**
     * Function to return value
     * @return line thickness value
     */
    public double getValue(){
        return lineThickness;
    }

    /**
     * Function to set value
     * @param lineThickness new line thickness value
     */
    public void setValue(double lineThickness){
        this.lineThickness = lineThickness;
        slider.setValue(lineThickness);
    }

    /**
     * Function to initialize layout
     */
    private void init(){
        Text text = initText();
        Text value = initValue();
        slider = initSlider(value);

        getChildren().addAll(text, slider, value);
        setSpacing(10);
        setAlignment(Pos.CENTER);
    }

    /**
     * Function to initialize text to identify which slider is responsible for line thickness
     * @return completely created text
     */
    private Text initText(){
        Text lineThickness = new Text(Values.LINE_THICKNESS);
        lineThickness.setTextAlignment(TextAlignment.CENTER);
        return lineThickness;
    }

    /**
     * Function to initialize text indicating what value of line thickness is chosen
     * @return completely created text
     */
    private Text initValue(){
        Text lineThickness = new Text("1.0");
        lineThickness.setTextAlignment(TextAlignment.CENTER);
        return lineThickness;
    }

    /**
     * Function to initialize slider for changing line thickness value
     * @param value text that will be changed
     * @return completely created slider
     */
    private Slider initSlider(Text value){
        Slider slider = new Slider(0, 1, 1);
        slider.valueProperty().addListener((ObservableValue<? extends Number> observable,
                                                         Number oldValue, Number newValue) -> {

            value.setText(String.format(Locale.US, "%.2f", newValue.doubleValue()));
            lineThickness = newValue.doubleValue();
        });

        return slider;
    }
}
