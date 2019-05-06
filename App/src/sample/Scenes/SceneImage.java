package App.src.sample.Scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import App.src.sample.ImageListener;
import App.src.sample.Theme;
import App.src.sample.Values;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SceneImage extends Stage {

    private Image image;
    public SceneImage(){
        super();
    }


    public void init(File file, ImageListener imageListener, double width, double height) throws FileNotFoundException {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        image = new Image(new FileInputStream(file.getPath()));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(primaryScreenBounds.getWidth());
        imageView.setFitHeight(primaryScreenBounds.getWidth());
        //imageView.
        if (height < width){
            imageView.setFitWidth(width * 0.75f);
            imageView.setFitHeight(width * 0.75f);
        }
        else{
            imageView.setFitWidth(height * 0.75f);
            imageView.setFitHeight(height * 0.75f);
        }

        imageView.setPreserveRatio(true);

        //hBox.setMa(new Insets( -height * 0.05f, 0, 0, 0));

        BorderPane temp = new BorderPane();

        //temp.setSpacing(50);

        if (Values.THEME == Theme.LIGHT){
            temp.setStyle("-fx-background-color: #F0F0F0;");
        }
        else{
            temp.setStyle("-fx-background-color: #34495E;");
        }

        //temp.setAlignment(Pos.TOP_CENTER);

        HBox hBox = getButtons(imageListener, height);
        HBox imageHBox = new HBox(imageView);
        imageHBox.setAlignment(Pos.CENTER);

        temp.setBottom(hBox);
        temp.setTop(imageHBox);

        BorderPane.setMargin(imageHBox, new Insets(height * 0.05f, 0, 0, 0));
        BorderPane.setMargin(hBox, new Insets(0, 0, height * 0.05f, 0));

        this.setTitle("Image");
        this.setScene(new Scene(temp));

        this.setWidth(primaryScreenBounds.getWidth() * 0.375f);
        this.setHeight(primaryScreenBounds.getHeight() * 0.75f);

        this.setMinWidth(primaryScreenBounds.getWidth() * 0.375f);
        this.setMinHeight(primaryScreenBounds.getHeight() * 0.75f);



        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                Values.openStages.focusStage(this);
            }
        });
    }

    private javafx.scene.layout.HBox getButtons(ImageListener imageListener, double height){
        Button cut = new Button(Values.CUT);
        Button advanced = new Button(Values.ADVANCED);
        Button accept = new Button(Values.ACCEPT);

        cut.setPrefWidth(100);
        advanced.setPrefWidth(100);
        accept.setPrefWidth(100);

        cut.setOnAction((event) -> {

        });

        advanced.setOnAction((event) -> {

        });

        accept.setOnAction((event) -> {
            imageListener.accepted(image);
            this.close();
        });

        javafx.scene.layout.HBox hBox = new javafx.scene.layout.HBox();
        hBox.setSpacing(25);
        hBox.getChildren().addAll(cut, advanced, accept);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }
}
