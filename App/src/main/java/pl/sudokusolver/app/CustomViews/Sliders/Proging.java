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
 * Proging layout in Advanced scene
 */
public class Proging extends VBox {

    private double proging = 1.0;
    private Slider slider;

    public Proging(){
        init();
    }

    /**
     * Function to return value
     * @return proging value
     */
    public double getValue(){
        return proging;
    }

    /**
     * Function to set value
     * @param proging new proging value
     */
    public void setValue(double proging){
        this.proging = proging;
        slider.setValue(proging);
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
     * Function to initialize text to identify which slider is responsible for proging
     * @return completely created text
     */
    private Text initText(){
        Text proging = new Text(Values.PROGING);
        proging.setTextAlignment(TextAlignment.CENTER);
        return proging;
    }

    /**
     * Function to initialize text indicating what value of proging is chosen
     * @return completely created text
     */
    private Text initValue(){
        Text proging = new Text("1.0");
        proging.setTextAlignment(TextAlignment.CENTER);
        return proging;
    }

    /**
     * Function to initialize slider for changing proging value
     * @param value text that will be changed
     * @return completely created slider
     */
    private Slider initSlider(Text value){
        Slider slider = new Slider(0, 1, 1);
        slider.valueProperty().addListener((ObservableValue<? extends Number> observable,
                                                   Number oldValue, Number newValue) -> {

            value.setText(String.format(Locale.US, "%.2f", newValue.doubleValue()));
            proging = newValue.doubleValue();
        });

        return slider;
    }
}
