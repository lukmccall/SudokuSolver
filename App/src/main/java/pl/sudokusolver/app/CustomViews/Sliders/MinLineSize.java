package pl.sudokusolver.app.CustomViews.Sliders;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pl.sudokusolver.app.Values;

/**
 * Minimum Line Size layout in Advanced scene
 */
public class MinLineSize extends VBox {

    private int minLineSize = 65;
    private Slider slider;

    public MinLineSize(){
        init();
    }

    /**
     * Function to return value
     * @return Minimum Line Size value
     */
    public int getValue(){
        return minLineSize;
    }

    /**
     * Function to set value
     * @param minLineSize new Minimum Line Size value
     */
    public void setValue(int minLineSize){
        this.minLineSize = minLineSize;
        slider.setValue(minLineSize);
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
     * Function to initialize text to identify which slider is responsible for Minimum Line Size
     * @return completely created text
     */
    private Text initText(){
        Text minLineSize = new Text(Values.MIN_LINE_SIZE);
        minLineSize.setTextAlignment(TextAlignment.CENTER);
        return minLineSize;
    }

    /**
     * Function to initialize text indicating what value of Minimum Line Size is chosen
     * @return completely created text
     */
    private Text initValue(){
        Text minLineSize = new Text("65");
        minLineSize.setTextAlignment(TextAlignment.CENTER);
        return minLineSize;
    }

    /**
     * Function to initialize slider for changing Minimum Line Size value
     * @param value text that will be changed
     * @return completely created slider
     */
    private Slider initSlider(Text value){
        Slider slider = new Slider(1, 600, 65);
        slider.valueProperty().addListener((ObservableValue<? extends Number> observable,
                                                   Number oldValue, Number newValue) -> {

            value.setText(String.valueOf(newValue.intValue()));
            minLineSize = newValue.intValue();
        });

        return slider;
    }
}
