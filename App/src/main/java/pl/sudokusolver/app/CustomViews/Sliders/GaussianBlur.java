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
 * Gaussian Blur layout in Advanced Scene
 */
public class GaussianBlur extends VBox {

    private double gaussianBlur = 1.0;
    private Slider slider;

    public GaussianBlur(){
        init();
    }

    /**
     * Function to return value
     * @return gaussian blur value
     */
    public double getValue(){
        return gaussianBlur;
    }

    /**
     * Function to set value
     * @param gaussianBlur new gaussian blur value
     */
    public void setValue(double gaussianBlur){
        this.gaussianBlur = gaussianBlur;
        slider.setValue(gaussianBlur);
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
     * Function to initialize text to identify which slider is responsible for gaussian blur
     * @return completely created text
     */
    private Text initText(){
        Text gaussianBlur = new Text(Values.GAUSSIAN_BLUR);
        gaussianBlur.setTextAlignment(TextAlignment.CENTER);
        return gaussianBlur;
    }

    /**
     * Function to initialize text indicating what value of gaussian blur is chosen
     * @return completely created text
     */
    private Text initValue(){
        Text gaussianBlur = new Text("1.0");
        gaussianBlur.setTextAlignment(TextAlignment.CENTER);
        return gaussianBlur;
    }

    /**
     * Function to initialize slider for changing gaussian blur value
     * @param value text that will be changed
     * @return completely created slider
     */
    private Slider initSlider(Text value){
        Slider slider = new Slider(0, 1, 1);
        slider.valueProperty().addListener((ObservableValue<? extends Number> observable,
                                                   Number oldValue, Number newValue) -> {

            value.setText(String.format(Locale.US, "%.2f", newValue.doubleValue()));
            gaussianBlur = newValue.doubleValue();
        });

        return slider;
    }
}
