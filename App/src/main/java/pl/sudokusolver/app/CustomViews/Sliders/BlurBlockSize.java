package pl.sudokusolver.app.CustomViews.Sliders;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pl.sudokusolver.app.Values;

/**
 * Blur Block Size layout in Advanced scene
 */
public class BlurBlockSize extends VBox {

    private int blurBlockSize = 31;
    private Slider slider;

    public BlurBlockSize(){
        init();
    }

    /**
     * Function to return value
     * @return Blur Block Size value
     */
    public int getValue(){
        return blurBlockSize;
    }

    /**
     * Function to set value
     * @param blurBlockSize new Blur Block Size value
     */
    public void setValue(int blurBlockSize){
        this.blurBlockSize = blurBlockSize;
        slider.setValue(blurBlockSize);
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
     * Function to initialize text to identify which slider is responsible for Blur Block Size
     * @return completely created text
     */
    private Text initText(){
        Text blurBlockSize = new Text(Values.BLUR_BLOCK_SIZE);
        blurBlockSize.setTextAlignment(TextAlignment.CENTER);
        return blurBlockSize;
    }

    /**
     * Function to initialize text indicating what value of Blur Block Size is chosen
     * @return completely created text
     */
    private Text initValue(){
        Text blurBlockSize = new Text("31");
        blurBlockSize.setTextAlignment(TextAlignment.CENTER);
        return blurBlockSize;
    }

    /**
     * Function to initialize slider for changing Blur Block Size value
     * @param value text that will be changed
     * @return completely created slider
     */
    private Slider initSlider(Text value){
        Slider slider = new Slider(0, 50, 31);
        slider.valueProperty().addListener((ObservableValue<? extends Number> observable,
                                                   Number oldValue, Number newValue) -> {

            value.setText(String.valueOf(newValue.intValue()));
            blurBlockSize = newValue.intValue();
        });

        return slider;
    }
}
