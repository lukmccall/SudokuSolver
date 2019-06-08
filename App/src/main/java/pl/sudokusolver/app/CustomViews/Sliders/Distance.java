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
 * Distance layout in Advanced scene
 */
public class Distance extends VBox {

    private double distance = 1.0;
    private Slider slider;

    public Distance(){
        init();
    }

    /**
     * Function to return value
     * @return distance value
     */
    public double getValue(){
        return distance;
    }

    /**
     * Function to set value
     * @param distance new distance value
     */
    public void setValue(double distance){
        this.distance = distance;
        slider.setValue(distance);
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
     * Function to initialize text to identify which slider is responsible for distance
     * @return completely created text
     */
    private Text initText(){
        Text distance = new Text(Values.DISTANCE);
        distance.setTextAlignment(TextAlignment.CENTER);
        return distance;
    }

    /**
     * Function to initialize text indicating what value of distance is chosen
     * @return completely created text
     */
    private Text initValue(){
        Text distance = new Text("1.0");
        distance.setTextAlignment(TextAlignment.CENTER);
        return distance;
    }

    /**
     * Function to initialize slider for changing distance value
     * @param value text that will be changed
     * @return completely created slider
     */
    private Slider initSlider(Text value){
        Slider slider = new Slider(1, 1, 1);
        slider.valueProperty().addListener((ObservableValue<? extends Number> observable,
                                                   Number oldValue, Number newValue) -> {

            value.setText(String.format(Locale.US, "%.2f", newValue.doubleValue()));
            distance = newValue.doubleValue();
        });

        return slider;
    }
}
