package pl.sudokusolver.app.Scenes;

import javafx.stage.Modality;
import pl.sudokusolver.app.Listeners.ButtonsListener;
import pl.sudokusolver.app.CustomViews.ViewAdvanced;
import pl.sudokusolver.app.Listeners.ParametersListener;
import pl.sudokusolver.app.Values;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controls appearance of Advanced screen
 */
public class StageAdvanced extends Stage implements ButtonsListener {

    private ViewAdvanced viewAdvanced;
    private Scene scene;

    private ParametersListener parametersListener;

    public StageAdvanced(ParametersListener parametersListener){
        this.parametersListener = parametersListener;
        init();
    }

    /**
     * Function inherited from ButtonsListeners, used to send parameters
     * @param lineThreshold line threshold value
     * @param lineGap line gap value
     * @param minLineSize minimum line size value
     * @param blurSize blur size value
     * @param blurBlockSize blur block size value
     * @param blurC blur C value
     * @param scaling scaling option
     * @param recognition recognition option
     */
    @Override
    public void send(int lineThreshold, int lineGap, int minLineSize, int blurSize, int blurBlockSize, int blurC,
                     String scaling, String recognition){
        parametersListener.parameters(lineThreshold, lineGap, minLineSize, blurSize, blurBlockSize, blurC,
                scaling, recognition);
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
    private void change(){
        viewAdvanced.setTheme();
    }

    /**
     * Function to initialize layout
     */
    private void init(){
        viewAdvanced = new ViewAdvanced(this);
        scene = new Scene(viewAdvanced);

        initModality(Modality.APPLICATION_MODAL);
        setTitle(Values.ADVANCED);
        setResizable(false);
        setScene(scene);
        change();
        show();
    }




}
