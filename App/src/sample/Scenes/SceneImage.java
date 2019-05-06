package App.src.sample.Scenes;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    private ImageView imageView;
    final Rectangle rectBound = new Rectangle(0, 0);

    public SceneImage(){
        super();
    }


    public void init(File file, ImageListener imageListener, double width, double height) throws FileNotFoundException {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        image = new Image(new FileInputStream(file.getPath()),
                primaryScreenBounds.getWidth(), primaryScreenBounds.getWidth(), false, false);
        imageView = new ImageView(image);

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

        rectBound.setFill(Color.TRANSPARENT);
        rectBound.setStroke(Color.RED);
        //ScrollPane scp = new ScrollPane();
        HBox root = new HBox(15);
        //scp.setContent(root);
        //root.setOrientation(Orientation.HORIZONTAL);

        Pane imageViewParent = new Pane();
        imageViewParent.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        //imageView1.setLayoutX(0.0);imageView1.setLayoutY(0.0);
        imageViewParent.getChildren().add(imageView);

        imageViewParent.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {

                    //imageViewParent.getChildren().removeAll();

                    rectBound.setWidth(0.0); rectBound.setHeight(0.0);
                    rectBound.setLayoutX(event.getX());
                    rectBound.setLayoutY(event.getY()); // setX or setY

                    if (rectBound.getParent() == null){
                        imageViewParent.getChildren().add(rectBound);
                    }

                }
                else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {

                }
                else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    double tempX = event.getX();
                    double tempY = event.getY();

                    if (tempX < imageView.getX()){
                        tempX = imageView.getX();
                    }

                    if (tempY < imageView.getY()){
                        tempY = imageView.getY();
                    }

                    if (tempX > imageView.getX() + imageView.getBoundsInParent().getWidth()){
                        tempX = imageView.getX() + imageView.getBoundsInParent().getWidth();
                    }
                    if (tempY > imageView.getY() + imageView.getBoundsInParent().getHeight()){
                        tempY = imageView.getY() + imageView.getBoundsInParent().getHeight();
                    }

                    rectBound.setWidth(tempX - rectBound.getLayoutX());
                    rectBound.setHeight(tempY - rectBound.getLayoutY());
                    System.out.println(rectBound.getWidth());
                }
                else if (event.getEventType() == MouseEvent.MOUSE_CLICKED
                        && event.getButton() == MouseButton.SECONDARY) {
                    if (rectBound.getParent() != null) {
                        imageViewParent.getChildren().remove(rectBound);
                    }
                }
            }
        });


        BorderPane temp = new BorderPane();

        //temp.setSpacing(50);

        if (Values.THEME == Theme.LIGHT){
            temp.setStyle("-fx-background-color: #F0F0F0;");
        }
        else{
            temp.setStyle("-fx-background-color: #34495E;");
        }

        root.getChildren().add(imageViewParent);
        //temp.setAlignment(Pos.TOP_CENTER);


        HBox hBox = getButtons(imageListener);
        HBox imageHBox = new HBox(imageViewParent);
        imageHBox.setAlignment(Pos.CENTER);

        temp.setBottom(hBox);
        temp.setTop(imageHBox);

        BorderPane.setMargin(imageHBox, new Insets(height * 0.05f, width * 0.05f, 0, width * 0.05f));
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

    private javafx.scene.layout.HBox getButtons(ImageListener imageListener){
        Button cut = new Button(Values.CUT);
        Button advanced = new Button(Values.ADVANCED);
        Button accept = new Button(Values.ACCEPT);

        cut.setPrefWidth(100);
        advanced.setPrefWidth(100);
        accept.setPrefWidth(100);

        cut.setOnAction((event) -> {
            System.out.println(rectBound.getWidth());

            /*PixelReader reader = imageView.getImage().getPixelReader();
            WritableImage newImage = new WritableImage(reader,
                    (int) rectBound.getLayoutX(),
                    (int) rectBound.getLayoutY(),
                    (int) rectBound.getWidth(),
                    (int) rectBound.getHeight());
*/
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            parameters.setViewport(new Rectangle2D(rectBound.getLayoutX(), rectBound.getLayoutY(), rectBound.getWidth(), rectBound.getHeight()));

            WritableImage wi = new WritableImage((int)rectBound.getWidth(), (int)rectBound.getHeight());
            Image croppedImage = imageView.snapshot(parameters, wi);

            rectBound.setWidth(0.0);
            rectBound.setHeight(0.0);
            imageView.setImage(croppedImage);

        });

        advanced.setOnAction((event) -> {
        });

        accept.setOnAction((event) -> {
            imageListener.accepted(imageView.getImage());
            this.close();
        });

        javafx.scene.layout.HBox hBox = new javafx.scene.layout.HBox();
        hBox.setSpacing(25);
        hBox.getChildren().addAll(cut, advanced, accept);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }
}
