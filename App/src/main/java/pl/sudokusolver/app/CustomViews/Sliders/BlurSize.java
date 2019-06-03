package pl.sudokusolver.app.CustomViews.Sliders;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pl.sudokusolver.app.Values;

/**
 * Blur Size layout in Advanced scene
 */
public class BlurSize extends VBox {

    private int blurSize = 3;
    private Slider slider;

    public BlurSize(){
        init();
    }

    /**
     * Function to return value
     * @return Blur Size value
     */
    public int getValue(){
        return blurSize;
    }

    /**
     * Function to set value
     * @param blurSize new Blur Size value
     */
    public void setValue(int blurSize){
        this.blurSize = blurSize;
        slider.setValue(blurSize);
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
     * Function to initialize text to identify which slider is responsible for Blur Size
     * @return completely created text
     */
    private Text initText(){
        Text blurSize = new Text(Values.BLUR_SIZE);
        blurSize.setTextAlignment(TextAlignment.CENTER);
        return blurSize;
    }

    /**
     * Function to initialize text indicating what value of Blur Size is chosen
     * @return completely created text
     */
    private Text initValue(){
        Text blurSize = new Text("3");
        blurSize.setTextAlignment(TextAlignment.CENTER);
        return blurSize;
    }

    /**
     * Function to initialize slider for changing Blur Size value
     * @param value text that will be changed
     * @return completely created slider
     */
    private Slider initSlider(Text value){
        Slider slider = new Slider(0, 49, 3);
        slider.valueProperty().addListener((ObservableValue<? extends Number> observable,
                                                   Number oldValue, Number newValue) -> {

            if (newValue.intValue() % 2 == 0){
                value.setText(String.valueOf(newValue.intValue() + 1));
                blurSize = newValue.intValue() + 1;
            }
            else{
                value.setText(String.valueOf(newValue.intValue()));
                blurSize = newValue.intValue();
            }

        });

        return slider;
    }
}
