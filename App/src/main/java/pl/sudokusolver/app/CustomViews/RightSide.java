package pl.sudokusolver.app.CustomViews;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pl.sudokusolver.app.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import pl.sudokusolver.app.Listeners.ImageListener;
import pl.sudokusolver.app.Listeners.Sender;
import pl.sudokusolver.app.Scenes.StageError;
import pl.sudokusolver.app.Scenes.StageImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Appearance of right side of main screen
 */
public class RightSide extends VBox implements ImageListener {

    private Sender sender;
    private ImageView imageView;
    private StageImage stageImage;
    private FileChooser.ExtensionFilter imageFilter;
    private Button solveButton, loadButton;

    public RightSide(Sender sender, double a, double b){
        this.sender = sender;
        try{
            init(a, b);
        }
        catch (FileNotFoundException e){
            new StageError(7);
        }
    }

    /**
     * Function to change application theme
     */
    public void change(){
        if (stageImage != null)
            stageImage.change();
    }

    /**
     * Function inherited from ImageListener, it is called when user accepts the image
     * @param image accepted image
     * @param parameters accepted parameters
     */
    @Override
    public void accepted(Image image, Parameters parameters){
        imageView.setImage(image);
        Utilities.log(parameters.getRecognition());

        BufferedImage bImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
        try{
            sender.send(bImage, parameters);
            //Singleton.getInstance().unblock();
        }
        catch (Exception e){
            //TODO error codes handiling ukasz musi mi powiedziec jak wygladaja exception zebym wiecial co logowac
            Singleton.getInstance().unblock();
            Utilities.log(e.toString());
        }
    }

    /**
     * Function to initialize whole layout
     * @param width preferable width of image
     * @param height preferable height of image
     * @throws FileNotFoundException in case path to image isn't valid
     */
    private void init(double width, double height) throws FileNotFoundException {
        initImage(width, height);

        setSpacing(10);
        getChildren().addAll(initTextBox(), initImageBox(), getButtons());
        setAlignment(Pos.CENTER);
    }

    /**
     * Function to init image
     * @param width preferable width of image
     * @param height preferable height of image
     * @throws FileNotFoundException in case path to image isn't valid
     */
    private void initImage(double width, double height) throws FileNotFoundException{
        Image image = new Image(new FileInputStream(Values.INITIAL_IMAGE));
        imageView = new ImageView(image);

        if (width > height){
            imageView.setFitWidth(height * 0.66f);
            imageView.setFitHeight(height * 0.66f);
        }
        else{
            imageView.setFitWidth(width * 0.66f);
            imageView.setFitHeight(width * 0.66f);
        }

        imageView.setPreserveRatio(true);
    }

    /**
     * Function to initialize box wrapper for image
     * @return completely initialized box wrapper
     */
    private VBox initImageBox(){
        VBox vBox = new VBox(imageView);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    /**
     * Function to initialize text with name of the app
     * @return completely initialized text
     */
    private Text initText(){
        Text text = new Text(Values.NAME);
        text.setStyle("-fx-font: 32 arial;" + "-fx-font-weight: bold;");
        return text;
    }

    /**
     * Function to initialize box wrapper for text
     * @return completely initialized box wrapper
     */
    private VBox initTextBox(){
        VBox vBox = new VBox(initText());
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    /**
     * Function to initialize button used to loading images
     * @return completely initialized button
     */
    private Button initLoad(){
        Button load = new Button(Values.LOAD);
        load.setPrefWidth(100);
        load.setFocusTraversable(false);

        load.setOnAction((event) -> {
            if (Singleton.getInstance().isBlocked()) return;

            if (imageFilter == null){
                imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");

                final FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(imageFilter);

                File file = fileChooser.showOpenDialog((Stage) sender);
                Singleton.getInstance().unblock();

                if (file != null) {
                    if (!(Utilities.getFileExtension(file).equals("jpg") || Utilities.getFileExtension(file).equals("png"))){
                        new StageError(2);
                        imageFilter = null;
                        return;
                    }

                    try {
                        if (ImageIO.read(file) == null) {
                            new StageError("Plik nie jest poprawny");
                            imageFilter = null;
                            return;
                        }
                    } catch(IOException ex) {
                        new StageError("Plik nie jest poprawny");
                        imageFilter = null;
                        return;
                    }

                    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
                    stageImage = new StageImage(RightSide.this);
                    double width = primaryScreenBounds.getWidth() * 0.375f;
                    double height = primaryScreenBounds.getHeight() * 0.66f;
                    stageImage.init(file, width, height);
                }
                else{
                    new StageError("Wybierz zdjecie");
                }

                imageFilter = null;
            }
        });

        return load;
    }

    /**
     * Function to initialize button used to solve sudoku
     * @return  completely initialized button
     */
    private Button initSolve(){
        Button solve = new Button(Values.SOLVE);
        solve.setPrefWidth(100);
        solve.setFocusTraversable(false);

        solve.setOnAction(event -> {
            if (Singleton.getInstance().isBlocked()) return;

            try{
                sender.solve();
            }
            catch (Exception e){
                //TODO error codes handiling ukasz musi mi powiedziec jak wygladaja exception zebym wiecial co logowac
                Singleton.getInstance().unblock();
                Utilities.log(e.toString());
            }

        });

        return solve;
    }

    /**
     * Function to align buttons next to each other and wrap them in a box
     * @return completely initialized box wrapper
     */
    private HBox getButtons(){
        HBox hBox = new HBox();
        hBox.setSpacing(25);

        loadButton = initLoad();
        solveButton = initSolve();
        hBox.getChildren().addAll(loadButton, solveButton);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    public void blockButtons(){
        loadButton.setDisable(true);
        solveButton.setDisable(true);

    }

    public void unblockButtons(){
        loadButton.setDisable(false);
        solveButton.setDisable(false);
    }

}
