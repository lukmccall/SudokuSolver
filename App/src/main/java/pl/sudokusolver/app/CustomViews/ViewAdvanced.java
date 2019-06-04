package pl.sudokusolver.app.CustomViews;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import pl.sudokusolver.app.CustomViews.RadioButtons.Recognition;
import pl.sudokusolver.app.CustomViews.RadioButtons.Scaling;
import pl.sudokusolver.app.CustomViews.RadioButtons.StrictMode;
import pl.sudokusolver.app.CustomViews.Sliders.*;
import pl.sudokusolver.app.Listeners.ButtonsListener;
import pl.sudokusolver.app.Parameters;
import pl.sudokusolver.app.Theme;
import pl.sudokusolver.app.Values;

/**
 * Advanced scene layout
 */
public class ViewAdvanced extends VBox {

    private LineThreshold lineThreshold;
    private MinLineSize minLineSize;
    private LineGap lineGap;
    private BlurSize blurSize;
    private BlurBlockSize blurBlockSize;
    private BlurC blurC;
    private Scaling scaling;
    private Recognition recognition;
    private StrictMode strictMode;

    private ButtonsListener buttonsListener;

    public ViewAdvanced(ButtonsListener buttonsListener){
        this.buttonsListener = buttonsListener;
        init();
    }

    /**
     * Function to initialize whole layout
     */
    private void init(){
        lineThreshold = new LineThreshold();
        lineGap = new LineGap();
        minLineSize = new MinLineSize();
        blurSize = new BlurSize();
        blurBlockSize = new BlurBlockSize();
        blurC = new BlurC();
        scaling = new Scaling();
        recognition = new Recognition();
        strictMode = new StrictMode();

        HBox topHBox = initSlidersTop();
        HBox bottomHBox = initSlidersBottom();
        HBox buttonsHBox = initButtons();
        HBox toggleHBox = initGroups();
        setMargins(topHBox, bottomHBox, toggleHBox, buttonsHBox);

        getChildren().addAll(topHBox, bottomHBox, toggleHBox, strictMode, buttonsHBox);
        setSpacing(25);
        setAlignment(Pos.CENTER);
        setTheme();
    }

    /**
     * Function to initialize top line of sliders
     * @return completely created line
     */
    private HBox initSlidersTop(){
        HBox hBox = new HBox(lineThreshold, lineGap, minLineSize);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    /**
     * Function to initialize bottom line of sliders
     * @return completely created line
     */
    private HBox initSlidersBottom(){
        HBox hBox = new HBox(blurSize, blurBlockSize, blurC);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    /**
     * Function to initialize bottom line of sliders
     * @return completely created line
     */
    private HBox initGroups(){
        HBox hBox = new HBox(scaling, recognition);

        HBox.setMargin(scaling, new Insets(0,50,0,50));
        HBox.setMargin(recognition, new Insets(0,50,0,50));

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
            Parameters parameters = new Parameters();
            parameters.set(lineThreshold.getValue(), lineGap.getValue(), minLineSize.getValue(),
                    blurSize.getValue(), blurBlockSize.getValue(), blurC.getValue(),
                    scaling.getValue(), recognition.getValue(), strictMode.getValue());
            buttonsListener.send(parameters);
            buttonsListener.exit();
        });

        def.setOnAction((event) -> {
            lineThreshold.setValue(50);
            minLineSize.setValue(100);
            lineGap.setValue(5);
            blurSize.setValue(3);
            blurBlockSize.setValue(31);
            blurC.setValue(15);
            scaling.setValue(Values.FIXED_WIDTH_SCALING);
            recognition.setValue(Values.SVM);
            strictMode.setValue(true);
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
    private void setMargins(HBox top, HBox bottom, HBox group, HBox buttons){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        double width = primaryScreenBounds.getWidth();
        double height = primaryScreenBounds.getHeight();

        VBox.setMargin(top, new Insets(height / 20, width / 40, 0, width / 40));
        VBox.setMargin(bottom, new Insets(0, width / 40, 0, width / 40));
        VBox.setMargin(group, new Insets(0, width / 40, 0, width / 40));
        VBox.setMargin(strictMode, new Insets(0, width / 40, 0, width / 40));
        VBox.setMargin(buttons, new Insets(0, width / 40, height / 20, width / 40));
    }
}
