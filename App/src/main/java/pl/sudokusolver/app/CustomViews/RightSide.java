package pl.sudokusolver.app.CustomViews;

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
import pl.sudokusolver.app.Scenes.StageImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RightSide extends VBox implements ImageListener {

    private Sender sender;
    private ImageView imageView;
    private StageImage stage;
    private FileChooser.ExtensionFilter imageFilter;

    public RightSide(Sender sender, double a, double b){
        this.sender = sender;
        try{
            init(a, b);
        }
        catch (FileNotFoundException e){
            Utilities.log(Values.E007);
        }
    }

    public void change(){
        if (stage != null)
            stage.change();
    }

    @Override
    public void accepted(Image image, Parameters parameters){
        imageView.setImage(image);

        BufferedImage bImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
        try{
            sender.send(bImage, parameters);
        }
        catch (Exception e){
            //TODO error codes handiling ukasz musi mi powiedziec jak wygladaja exception zebym wiecial co logowac
            Utilities.log(e.toString());
        }

    }

    private void init(double width, double height) throws FileNotFoundException {
        Text temp = new Text(Values.NAME);
        temp.setStyle("-fx-font: 32 arial;" + "-fx-font-weight: bold;");

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

        VBox vBox1 = new VBox(temp);
        vBox1.setAlignment(Pos.CENTER);

        VBox vBox2 = new VBox(imageView);
        vBox2.setAlignment(Pos.CENTER);

        javafx.scene.layout.HBox hBox = getButtons();
        hBox.setAlignment(Pos.CENTER);

        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(vBox1, vBox2, hBox);
    }

    private javafx.scene.layout.HBox getButtons(){
        Button load = new Button(Values.LOAD);
        Button solve = new Button(Values.SOLVE);

        load.setPrefWidth(100);
        solve.setPrefWidth(100);

        load.setOnAction((event) -> {
            if (imageFilter == null){
                imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");

                final FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(imageFilter);

                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
                    stage = new StageImage(RightSide.this);
                    try{
                        double width = primaryScreenBounds.getWidth() * 0.375f;
                        double height = primaryScreenBounds.getHeight() * 0.66f;
                        stage.init(file, width, height);
                    }
                    catch (FileNotFoundException e){
                        Utilities.log(Values.E007);
                    }
                }
                else{
                    Utilities.log(Values.E002);
                }

                imageFilter = null;
            }
        });

        solve.setOnAction(event -> {
            try{
                sender.solve();
            }
            catch (Exception e){
                //TODO error codes handiling ukasz musi mi powiedziec jak wygladaja exception zebym wiecial co logowac
                Utilities.log(e.toString());
            }

        });

        load.setFocusTraversable(false);
        solve.setFocusTraversable(false);

        javafx.scene.layout.HBox hBox = new javafx.scene.layout.HBox();
        hBox.setSpacing(25);
        hBox.getChildren().addAll(load, solve);

        return hBox;
    }
}
