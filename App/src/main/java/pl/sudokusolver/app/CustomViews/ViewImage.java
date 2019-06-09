package pl.sudokusolver.app.CustomViews;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pl.sudokusolver.app.*;
import pl.sudokusolver.app.Listeners.ImageListener;
import pl.sudokusolver.app.Listeners.ParametersListener;
import pl.sudokusolver.app.Scenes.StageAdvanced;
import pl.sudokusolver.app.Scenes.StageError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ViewImage extends BorderPane implements ParametersListener {

    private ImageView imageView;
    private Pane imageParent;
    private Rectangle rectBound;
    private StageAdvanced stageAdvanced;
    private ViewSelection rubberBandSelection;
    private Parameters parameters = new Parameters();
    private ImageListener imageListener;

    public ViewImage(ImageListener imageListener){
        this.imageListener = imageListener;
    }

    /**
     * Function inherited from ParametersListener, called when user accepts parameters in Advanced Stage
     * @param parameters image parameters
     */
    @Override
    public void parameters(Parameters parameters){
        this.parameters.set(parameters);
    }

    /**
     * Function to initialize image
     * @param file path to image
     * @param width width of a screen
     * @param height height of a screen
     * @throws FileNotFoundException when path to image is invalid
     */
    private void initImage(File file, double width, double height) throws FileNotFoundException{
        Image image = new Image(new FileInputStream(file.getPath()));
        imageView = new ImageView(image);

        if (height < width){
            imageView.setFitWidth(width * 0.75f);
            imageView.setFitHeight(width * 0.75f);
        }
        else{
            imageView.setFitWidth(height * 0.75f);
            imageView.setFitHeight(height * 0.75f);
        }

        imageView.setPreserveRatio(true);
    }

    /**
     * Function to initialize whole view
     * @param file path to image that we want to open
     * @param width width of a screen
     * @param height height of a screen
     */
    public void init(File file, double width, double height) {
        try{
            initImage(file, width, height);
        }
        catch (FileNotFoundException e){
            new StageError(12, "Nie udało się otworzyć zdjęcia");
            return;
        }

        initRectangle();
        imageParent = initImageParent();
        rubberBandSelection = new ViewSelection(imageParent, imageView, imageListener);

        HBox buttons = getButtons();
        HBox image = initImage();

        setMargins(width, height, image, buttons);
        setBottom(buttons);
        setTop(image);
    }

    /**
     * Function to set margins in layout
     * @param width width of the screen
     * @param height height of the screen
     * @param image image that the margin will be applied to
     * @param buttons wrapped box that the margin will be applied to
     */
    private void setMargins(double width, double height, HBox image, HBox buttons){
        BorderPane.setMargin(image, new Insets(height * 0.05f, width * 0.05f, 0, width * 0.05f));
        BorderPane.setMargin(buttons, new Insets(0, 0, height * 0.05f, 0));
    }

    /**
     * Function to initialize rectangle used for choosing what part of image to cut
     */
    private void initRectangle(){
        rectBound = new Rectangle(0, 0);
        rectBound.setFill(Color.TRANSPARENT);
        rectBound.setStroke(Color.RED);
    }

    /**
     * Function to initialize pane used for drawing rectangle on image
     * @return completely initialized pane
     */
    private Pane initImageParent(){
        Pane imageParent = new Pane();
        imageParent.getChildren().add(imageView);
        return imageParent;
    }

    /**
     * Function to initialize box wrapper for image
     * @return completely initialized image wrapped ia a box
     */
    private HBox initImage(){
        HBox hBox = new HBox(imageParent);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    /**
     * Function to initialize cut button
     * @return completely initialized cut button
     */
    private Button initCut(){
        Button cut = new Button(Values.CUT);
        cut.setPrefWidth(100);

        cut.setOnAction((event) -> {
            Rectangle2D rectangle2D = rubberBandSelection.getRectangle();

            if (!Utilities.isValid(rectangle2D)){
                new StageError(9, "Nie można wyciąć bez zaznaczenia.");
                return;
            }

            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            parameters.setViewport(rectangle2D);

            WritableImage wi = new WritableImage((int)rectangle2D.getWidth(), (int)rectangle2D.getHeight());
            Image croppedImage = imageView.snapshot(parameters, wi);

            imageView.setImage(croppedImage);

        });

        return cut;
    }

    /**
     * Function to initialize advanced button
     * @return completely initialized advanced button
     */
    private Button initAdvanced(){
        Button advanced = new Button(Values.ADVANCED);
        advanced.setPrefWidth(150);
        advanced.setOnAction((event) -> {
            if (stageAdvanced != null) {
                if (stageAdvanced.isShowing()) stageAdvanced.toFront();
                else stageAdvanced.show();
                return;
            }
            stageAdvanced = new StageAdvanced(this);

        });

        return advanced;
    }

    /**
     * Function to initialize accept button
     * @return completely initialized accept button
     */
    private Button initAccept(){
        Button accept = new Button(Values.ACCEPT);
        accept.setPrefWidth(100);
        accept.setOnAction((event) -> imageListener.accepted(imageView.getImage(), parameters));

        return accept;
    }

    /**
     * Function to initialize layout for buttons
     * @return completely initialized box wrapper for buttons
     */
    private HBox getButtons(){
        HBox hBox = new HBox();
        hBox.setSpacing(25);
        hBox.getChildren().addAll(initCut(), initAdvanced(), initAccept());
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


}
