package pl.sudokusolver.app.Scenes;

import pl.sudokusolver.app.ButtonsListener;
import pl.sudokusolver.app.CustomViews.ViewAdvanced;
import pl.sudokusolver.app.ParametersListener;
import pl.sudokusolver.app.Values;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controls appearance of Advanced screen
 */
class StageAdvanced extends Stage implements ButtonsListener {

    private ViewAdvanced viewAdvanced;
    private Scene scene;

    private ParametersListener parametersListener;

    StageAdvanced(ParametersListener parametersListener){
        this.parametersListener = parametersListener;
        init();
    }

    /**
     * Function inherited from ButtonsListeners, used to send parameters
     * @param lineThickness line thickness value
     * @param proging proging value
     * @param distance distance value
     * @param gaussianBlur value
     */
    @Override
    public void send(double lineThickness, double proging, double distance, double gaussianBlur){
        parametersListener.parameters(lineThickness, proging, distance, gaussianBlur);
    }

    /**
     * Function inherited from ButtonsListener, used to close window after choosing parameters
     */
    @Override
    public void exit(){
        close();
    }

    /**
     * Function to change theme
     */
    void change(){
        viewAdvanced.setTheme();
    }

    /**
     * Function to initialize layout
     */
    private void init(){
        viewAdvanced = new ViewAdvanced(this);
        scene = new Scene(viewAdvanced);

        setTitle(Values.ADVANCED);
        setResizable(false);
        setScene(scene);
        show();
    }




}
