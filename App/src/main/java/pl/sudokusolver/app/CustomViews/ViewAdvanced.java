package pl.sudokusolver.app.CustomViews;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import pl.sudokusolver.app.Listeners.ButtonsListener;
import pl.sudokusolver.app.CustomViews.Sliders.Distance;
import pl.sudokusolver.app.CustomViews.Sliders.GaussianBlur;
import pl.sudokusolver.app.CustomViews.Sliders.LineThickness;
import pl.sudokusolver.app.CustomViews.Sliders.Proging;
import pl.sudokusolver.app.Theme;
import pl.sudokusolver.app.Values;

/**
 * Advanced scene layout
 */
public class ViewAdvanced extends VBox {

    private LineThickness lineThickness;
    private Proging proging;
    private Distance distance;
    private GaussianBlur gaussianBlur;

    private ButtonsListener buttonsListener;

    public ViewAdvanced(ButtonsListener buttonsListener){
        this.buttonsListener = buttonsListener;
        init();
    }

    /**
     * Function to initialize whole layout
     */
    private void init(){
        lineThickness = new LineThickness();
        proging = new Proging();
        distance = new Distance();
        gaussianBlur = new GaussianBlur();

        HBox topHBox = initTop();
        HBox bottomHBox = initBottom();
        HBox buttonsHBox = initButtons();
        setMargins(topHBox, bottomHBox, buttonsHBox);

        getChildren().addAll(topHBox, bottomHBox, buttonsHBox);
        setSpacing(25);
        setAlignment(Pos.CENTER);
        setTheme();
    }

    /**
     * Function to initialize top line of sliders
     * @return completely created line
     */
    private HBox initTop(){
        HBox hBox = new HBox(lineThickness, distance);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    /**
     * Function to initialize bottom line of sliders
     * @return completely created line
     */
    private HBox initBottom(){
        HBox hBox = new HBox(proging, gaussianBlur);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    /**
     * Function to initialize line of buttons
     * @return completely created line
     */
    private HBox initButtons(){
        Button set = new Button(Values.SET);
        Button def = new Button(Values.DEFAULT);

        set.setPrefWidth(100);
        def.setPrefWidth(100);

        set.setOnAction((event) -> {
            buttonsListener.send(lineThickness.getValue(), proging.getValue(), distance.getValue(), gaussianBlur.getValue());
            buttonsListener.exit();
        });

        def.setOnAction((event) -> {
            lineThickness.setValue(1.0f);
            proging.setValue(1.0f);
            distance.setValue(1.0f);
            gaussianBlur.setValue(1.0f);
        });


        HBox hBox = new HBox();
        hBox.setSpacing(25);
        hBox.getChildren().addAll(set, def);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    /**
     * Function to set scene theme
     */
    public void setTheme(){
        if (Values.THEME == Theme.LIGHT){
            setStyle("-fx-background-color: #F0F0F0;");
        }
        else{
            setStyle("-fx-background-color: #34495E;");
        }
    }

    /**
     * Function to set margins to whole layout
     * @param top line of sliders
     * @param bottom line of sliders
     * @param buttons line of buttons
     */
    private void setMargins(HBox top, HBox bottom, HBox buttons){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        double width = primaryScreenBounds.getWidth();
        double height = primaryScreenBounds.getHeight();

        VBox.setMargin(top, new Insets(height / 20, width / 40, 0, width / 40));
        VBox.setMargin(bottom, new Insets(0, width / 40, 0, width / 40));
        VBox.setMargin(buttons, new Insets(0, width / 40, height / 20, width / 40));
    }
}
