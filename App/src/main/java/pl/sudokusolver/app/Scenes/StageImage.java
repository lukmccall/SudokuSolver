package pl.sudokusolver.app.Scenes;

import javafx.stage.Modality;
import pl.sudokusolver.app.CustomViews.ViewImage;
import pl.sudokusolver.app.Parameters;
import pl.sudokusolver.app.Listeners.ParametersListener;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pl.sudokusolver.app.Listeners.ImageListener;

import java.io.File;

/**
 * Image Screen that is opened after new image is selected
 */
public class StageImage extends Stage implements ParametersListener, ImageListener {

    private Parameters parameters = new Parameters();
    private ImageListener imageListener;
    private ViewImage viewImage;

    public StageImage(ImageListener imageListener){
        super();
        this.imageListener = imageListener;
    }

    /**
     * Function inherited from ImageListener, called when user accepts image with parameters
     * @param image accepted image
     * @param parameters accepted parameters
     */
    @Override
    public void accepted(Image image, Parameters parameters){
        imageListener.accepted(image, parameters);
        close();
    }

    /**
     * Function inherited from ParametersListener, called when user accepts parameters
     * @param parameters image parameters
     */
    @Override
    public void parameters(Parameters parameters){
        this.parameters.set(parameters);
    }

    /**
     * Function to change theme of this stage
     */
    public void change(){
        viewImage.setTheme();
    }

    /**
     * Function to initialize layout the whole stage
     * @param file  path to image
     * @param width default width of image
     * @param height default height of image
     */
    public void init(File file, double width, double height){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        viewImage = new ViewImage(this);
        viewImage.init(file, width, height);
        Scene scene = new Scene(viewImage);

        initModality(Modality.APPLICATION_MODAL);
        setTitle("Image");
        setScene(scene);

        setWidth(primaryScreenBounds.getWidth() * 0.375f);
        setHeight(primaryScreenBounds.getHeight() * 0.75f);

        setMinWidth(primaryScreenBounds.getWidth() * 0.375f);
        setMinHeight(primaryScreenBounds.getHeight() * 0.75f);
        change();
        show();
    }
}
