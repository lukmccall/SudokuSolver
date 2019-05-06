package App.src.sample.CustomViews;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import App.src.sample.ImageListener;
import App.src.sample.Scenes.SceneImage;
import App.src.sample.Values;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RightSide extends BorderPane implements ImageListener {

    private ImageView imageView;

    public RightSide(double a, double b){
        try{
            init(a, b);
        }
        catch (FileNotFoundException e){

        }
    }

    @Override
    public void accepted(Image image){
        imageView.setImage(image);
    }

    private void init(double a, double b) throws FileNotFoundException {
        //this.setAlignment(Pos.CENTER);
        Text temp = new Text(Values.NAME);
        temp.setStyle("-fx-font: 32 arial;");

        Image image = new Image(new FileInputStream("C:\\Users\\Thomas\\Desktop\\hasloxDD.png"));
        imageView = new ImageView(image);

        if (a > b){
            imageView.setFitWidth(b * 0.75f);
            imageView.setFitHeight(b * 0.75f);
        }
        else{
            imageView.setFitWidth(a * 0.75f);
            imageView.setFitHeight(a * 0.75f);
        }

        imageView.setPreserveRatio(true);

        VBox vBox1 = new VBox(temp);
        vBox1.setAlignment(Pos.CENTER);

        VBox vBox2 = new VBox(imageView);
        vBox2.setAlignment(Pos.CENTER);

        javafx.scene.layout.HBox hBox = getButtons();
        hBox.setAlignment(Pos.CENTER);

        this.setTop(vBox1);
        this.setCenter(vBox2);
        this.setBottom(hBox);

        //this.getChildren().addAll(temp, imageView, hBox);
    }

    private javafx.scene.layout.HBox getButtons(){
        Button load = new Button(Values.LOAD);
        Button solve = new Button(Values.SOLVE);

        load.setPrefWidth(100);
        solve.setPrefWidth(100);

        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneImage stage = new SceneImage();
                FileChooser.ExtensionFilter imageFilter
                        = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");


                final FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(imageFilter);

                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
                    try{
                        double width = primaryScreenBounds.getWidth() * 0.375f;
                        double height = primaryScreenBounds.getHeight() * 0.66f;
                        stage.init(file, RightSide.this, width, height);
                    }
                    catch (Exception e){

                    }

                    /*Scene secondScene = new Scene(new SceneImage(file, RightSide.this,
                            primaryScreenBounds.getWidth() * 0.375f, primaryScreenBounds.getHeight() * 0.66f),
                            primaryScreenBounds.getWidth() * 0.375f, primaryScreenBounds.getHeight() * 0.66f);


                    stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                            Values.openStages.focusStage(stage);
                        }
                    });


                    stage.setTitle("Image");
                    stage.setScene(secondScene);

                    stage.setWidth(primaryScreenBounds.getWidth() * 0.375f);
                    stage.setHeight(primaryScreenBounds.getHeight() * 0.75f);

                    stage.setMinWidth(primaryScreenBounds.getWidth() * 0.375f);
                    stage.setMinHeight(primaryScreenBounds.getHeight() * 0.75f);*/

                    stage.show();
                }
                else{

                }
            }
        });

        solve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        javafx.scene.layout.HBox hBox = new javafx.scene.layout.HBox();
        hBox.setSpacing(25);
        hBox.getChildren().addAll(load, solve);
        //hBox.setAlignment(Pos.CENTER);

        return hBox;
    }


}
