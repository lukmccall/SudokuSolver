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

public class StageImage extends Stage implements ParametersListener, ImageListener {

    private Parameters parameters = new Parameters();
    private ImageListener imageListener;
    private ViewImage viewImage;

    public StageImage(ImageListener imageListener){
        super();
        this.imageListener = imageListener;
    }

    @Override
    public void accepted(Image image, Parameters parameters){
        imageListener.accepted(image, parameters);
        close();
    }


    @Override
    public void parameters(double a, double b, double c, double d){
        parameters.set(a, b, c, d);
    }

    public void change(){
        viewImage.setTheme();
    }

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
        show();
    }
}
