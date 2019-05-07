package App.src.sample.Scenes;

import App.src.sample.Theme;
import App.src.sample.Values;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Locale;

public class StageAdvanced extends Stage {

    private double lineThickness = 1.0f;
    private double proging = 1.0f;
    private double distance = 1.0f;
    private double gauss = 1.0f;

    private Slider sliderLineThickness;
    private Slider sliderProging;
    private Slider sliderDistance;
    private Slider sliderGauss;

    StageAdvanced(){
        init();
    }

    private void init(){
        this.setTitle(Values.ADVANCED);

        Text textLineThickness = new Text(Values.LINE_THICKNESS);
        Text textProging = new Text(Values.PROGING);
        Text textDistance = new Text(Values.DISTANCE);
        Text textGauss = new Text(Values.GAUSSIAN_BLUR);

        textLineThickness.setTextAlignment(TextAlignment.CENTER);
        textProging.setTextAlignment(TextAlignment.CENTER);
        textDistance.setTextAlignment(TextAlignment.CENTER);
        textGauss.setTextAlignment(TextAlignment.CENTER);

        sliderLineThickness = new Slider(0, 1, 1);
        sliderProging = new Slider(0, 1, 1);
        sliderDistance = new Slider(0, 1, 1);
        sliderGauss = new Slider(0, 1, 1);

        Text valueLineThickness = new Text("1.0");
        Text valueProging = new Text("1.0");
        Text valueDistance = new Text("1.0");
        Text valueGauss = new Text("1.0");

        valueLineThickness.setTextAlignment(TextAlignment.CENTER);
        valueProging.setTextAlignment(TextAlignment.CENTER);
        valueDistance.setTextAlignment(TextAlignment.CENTER);
        valueGauss.setTextAlignment(TextAlignment.CENTER);

        sliderLineThickness.valueProperty().addListener((ObservableValue<? extends Number> observable,
                                                         Number oldValue, Number newValue) -> {

            valueLineThickness.setText(String.format(Locale.US, "%.2f", newValue.doubleValue()));
            lineThickness = newValue.doubleValue();
        });

        sliderProging.valueProperty().addListener((ObservableValue<? extends Number> observable,
                                                         Number oldValue, Number newValue) -> {

            valueProging.setText(String.format(Locale.US, "%.2f", newValue.doubleValue()));
            proging = newValue.doubleValue();
        });

        sliderDistance.valueProperty().addListener((ObservableValue<? extends Number> observable,
                                                         Number oldValue, Number newValue) -> {

            valueDistance.setText(String.format(Locale.US, "%.2f", newValue.doubleValue()));
            distance = newValue.doubleValue();
        });

        sliderGauss.valueProperty().addListener((ObservableValue<? extends Number> observable,
                                                         Number oldValue, Number newValue) -> {

            valueGauss.setText(String.format(Locale.US, "%.2f", newValue.doubleValue()));
            gauss = newValue.doubleValue();
        });

        VBox vBoxLineThickness = new VBox(textLineThickness, sliderLineThickness, valueLineThickness);
        VBox vBoxProging = new VBox(textProging, sliderProging, valueProging);
        VBox vBoxDistance = new VBox(textDistance, sliderDistance, valueDistance);
        VBox vBoxGauss = new VBox(textGauss, sliderGauss, valueGauss);

        vBoxLineThickness.setSpacing(10);
        vBoxProging.setSpacing(10);
        vBoxDistance.setSpacing(10);
        vBoxGauss.setSpacing(10);

        vBoxLineThickness.setAlignment(Pos.CENTER);
        vBoxProging.setAlignment(Pos.CENTER);
        vBoxDistance.setAlignment(Pos.CENTER);
        vBoxGauss.setAlignment(Pos.CENTER);

        HBox vBoxTop = new HBox(vBoxLineThickness, vBoxDistance);
        HBox vBoxBottom = new HBox(vBoxProging, vBoxGauss);

        HBox buttons = getButtons();
        buttons.setAlignment(Pos.CENTER);


        VBox hBox = new VBox(vBoxTop, vBoxBottom, buttons);
        hBox.setSpacing(25);

        if (Values.THEME == Theme.LIGHT){
            hBox.setStyle("-fx-background-color: #F0F0F0;");
        }
        else{
            hBox.setStyle("-fx-background-color: #34495E;");
        }

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();


        double width = primaryScreenBounds.getWidth();
        double height = primaryScreenBounds.getHeight();

        VBox.setMargin(vBoxTop, new Insets(height / 20, width / 40, 0, width / 40));
        VBox.setMargin(vBoxBottom, new Insets(0, width / 40, 0, width / 40));
        VBox.setMargin(buttons, new Insets(0, width / 40, height / 20, width / 40));
        this.setScene(new Scene(hBox));
        this.show();
    }

    private javafx.scene.layout.HBox getButtons(){
        Button set = new Button(Values.SET);
        Button def = new Button(Values.DEFAULT);

        set.setPrefWidth(100);
        def.setPrefWidth(100);

        set.setOnAction((event) -> {

            this.close();
        });

        def.setOnAction((event) -> {
            sliderLineThickness.setValue(1.0f);
            sliderProging.setValue(1.0f);
            sliderDistance.setValue(1.0f);
            sliderGauss.setValue(1.0f);
        });


        javafx.scene.layout.HBox hBox = new javafx.scene.layout.HBox();
        hBox.setSpacing(25);
        hBox.getChildren().addAll(set, def);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }
}
