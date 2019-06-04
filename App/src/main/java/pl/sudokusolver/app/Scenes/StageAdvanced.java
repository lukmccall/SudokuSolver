package pl.sudokusolver.app.Scenes;

import javafx.stage.Modality;
import pl.sudokusolver.app.Listeners.ButtonsListener;
import pl.sudokusolver.app.CustomViews.ViewAdvanced;
import pl.sudokusolver.app.Listeners.ParametersListener;
import pl.sudokusolver.app.Parameters;
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
     * @param parameters image parameters
     */
    @Override
    public void send(Parameters parameters){
        parametersListener.parameters(parameters);
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
