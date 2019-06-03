package pl.sudokusolver.app.CustomViews.Sliders;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pl.sudokusolver.app.Values;

/**
 * Line Gap layout in Advanced scene
 */
public class LineGap extends VBox {

    private int lineGap = 5;
    private Slider slider;

    public LineGap(){
        init();
    }

    /**
     * Function to return value
     * @return Line Gap value
     */
    public int getValue(){
        return lineGap;
    }

    /**
     * Function to set value
     * @param lineGap new Line Gap value
     */
    public void setValue(int lineGap){
        this.lineGap = lineGap;
        slider.setValue(lineGap);
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
     * Function to initialize text to identify which slider is responsible for Line Gap
     * @return completely created text
     */
    private Text initText(){
        Text lineGap = new Text(Values.LINE_GAP);
        lineGap.setTextAlignment(TextAlignment.CENTER);
        return lineGap;
    }

    /**
     * Function to initialize text indicating what value of Line Gap is chosen
     * @return completely created text
     */
    private Text initValue(){
        Text lineGap = new Text("5");
        lineGap.setTextAlignment(TextAlignment.CENTER);
        return lineGap;
    }

    /**
     * Function to initialize slider for changing Line Gap value
     * @param value text that will be changed
     * @return completely created slider
     */
    private Slider initSlider(Text value){
        Slider slider = new Slider(0, 50, 5);
        slider.valueProperty().addListener((ObservableValue<? extends Number> observable,
                                                   Number oldValue, Number newValue) -> {

            value.setText(String.valueOf(newValue.intValue()));
            lineGap = newValue.intValue();
        });

        return slider;
    }
}
