package pl.sudokusolver.app.CustomViews.Sliders;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pl.sudokusolver.app.Values;

/**
 * Blur C layout in Advanced scene
 */
public class BlurC extends VBox {

    private int blurC = 15;
    private Slider slider;

    public BlurC(){
        init();
    }

    /**
     * Function to return value
     * @return Blur C value
     */
    public int getValue(){
        return blurC;
    }

    /**
     * Function to set value
     * @param blurC new Blur C value
     */
    public void setValue(int blurC){
        this.blurC = blurC;
        slider.setValue(blurC);
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
     * Function to initialize text to identify which slider is responsible for Blur C
     * @return completely created text
     */
    private Text initText(){
        Text blurC = new Text(Values.BLUR_C);
        blurC.setTextAlignment(TextAlignment.CENTER);
        return blurC;
    }

    /**
     * Function to initialize text indicating what value of Blur C is chosen
     * @return completely created text
     */
    private Text initValue(){
        Text blurC = new Text("15");
        blurC.setTextAlignment(TextAlignment.CENTER);
        return blurC;
    }

    /**
     * Function to initialize slider for changing Blur C value
     * @param value text that will be changed
     * @return completely created slider
     */
    private Slider initSlider(Text value){
        Slider slider = new Slider(1, 50, 15);
        slider.valueProperty().addListener((ObservableValue<? extends Number> observable,
                                                   Number oldValue, Number newValue) -> {

            value.setText(String.valueOf(newValue.intValue()));
            blurC = newValue.intValue();
        });

        return slider;
    }
}
