package App.src.sample.Scenes;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
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
    private final Rectangle rectBound = new Rectangle(0, 0);
    private Stage stageAdvanced;
    RubberBandSelection rubberBandSelection;

    public SceneImage(){
        super();
    }


    public void init(File file, ImageListener imageListener, double width, double height) throws FileNotFoundException {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        image = new Image(new FileInputStream(file.getPath()));
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

        rectBound.setFill(Color.TRANSPARENT);
        rectBound.setStroke(Color.RED);

        Pane imageViewParent = new Pane();
        //imageViewParent.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        imageViewParent.getChildren().add(imageView);

        //Group imageLayer = new Group(imageView);
        rubberBandSelection = new RubberBandSelection(imageViewParent, imageView);

        /*imageLayer.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isSecondaryButtonDown()) {
                    contextMenu.show(imageLayer, event.getScreenX(), event.getScreenY());
                }
            }
        });*/
        /*Pane imageViewParent = new Pane();
        imageViewParent.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        imageViewParent.getChildren().add(imageView);

        imageViewParent.addEventFilter(MouseEvent.ANY, event ->  {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {

                rectBound.setWidth(0.0);
                rectBound.setHeight(0.0);
                rectBound.setLayoutX(event.getX());
                rectBound.setLayoutY(event.getY());

                if (rectBound.getParent() == null){
                    imageViewParent.getChildren().add(rectBound);
                }

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


                if (tempX - rectBound.getLayoutX() > 0){
                    rectBound.setWidth(tempX - rectBound.getLayoutX());
                }
                else{
                    double tempp = rectBound.getLayoutX();
                    rectBound.setLayoutX(tempX);
                    rectBound.setWidth(tempp - rectBound.getLayoutX());
                }

                if (tempY - rectBound.getLayoutY() > 0){
                    rectBound.setHeight(tempY - rectBound.getLayoutY());
                }
                else{
                    double tempp = rectBound.getLayoutY();
                    rectBound.setLayoutY(tempY);
                    rectBound.setHeight(tempp - rectBound.getLayoutY());
                }
            }
            else if (event.getEventType() == MouseEvent.MOUSE_CLICKED
                    && event.getButton() == MouseButton.SECONDARY) {
                if (rectBound.getParent() != null) {
                    imageViewParent.getChildren().remove(rectBound);
                }
            }

        });*/


        BorderPane temp = new BorderPane();

        if (Values.THEME == Theme.LIGHT){
            temp.setStyle("-fx-background-color: #F0F0F0;");
        }
        else{
            temp.setStyle("-fx-background-color: #34495E;");
        }

        //root.getChildren().add(imageViewParent);

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

            Rectangle2D rectangle2D = rubberBandSelection.getRectangle();

            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            parameters.setViewport(rectangle2D);

            WritableImage wi = new WritableImage((int)rectangle2D.getWidth(), (int)rectangle2D.getHeight());
            Image croppedImage = imageView.snapshot(parameters, wi);

            imageView.setImage(croppedImage);

        });

        advanced.setOnAction((event) -> {
            if (stageAdvanced != null) {
                //stageAdvanced.setScene(secondScene);
                if (stageAdvanced.isShowing()) stageAdvanced.toFront();
                else stageAdvanced.show();
                return;
            }
            stageAdvanced = new StageAdvanced();

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

    public static class RubberBandSelection {

        final DragContext dragContext = new DragContext();
        Rectangle rect;

        Pane group;
        ImageView imageView;

        Rectangle2D getRectangle(){
            Rectangle2D temp = new Rectangle2D(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            rect.setX(0);
            rect.setY(0);
            rect.setWidth(0);
            rect.setHeight(0);
            return temp;
        }

        RubberBandSelection(Pane group, ImageView imageView) {

            this.group = group;
            this.imageView = imageView;

            rect = new Rectangle( 0,0,0,0);
            rect.setStroke(Color.BLUE);
            rect.setStrokeWidth(1);
            rect.setStrokeLineCap(StrokeLineCap.ROUND);
            rect.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.6));

            group.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
            group.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
        }

        EventHandler<MouseEvent> onMousePressedEventHandler = (event) -> {
            if( event.isSecondaryButtonDown())
                return;

            // remove old rect
            rect.setX(0);
            rect.setY(0);
            rect.setWidth(0);
            rect.setHeight(0);

            group.getChildren().remove(rect);


            // prepare new drag operation
            dragContext.mouseAnchorX = event.getX();
            dragContext.mouseAnchorY = event.getY();

            rect.setX(dragContext.mouseAnchorX);
            rect.setY(dragContext.mouseAnchorY);
            rect.setWidth(0);
            rect.setHeight(0);

            group.getChildren().add( rect);

        };

        EventHandler<MouseEvent> onMouseDraggedEventHandler = (event) -> {
            if (event.isSecondaryButtonDown())
                return;

            double tempX = event.getX();
            double tempY = event.getY();

            if (tempX > imageView.getX() + imageView.getBoundsInParent().getWidth()){
                tempX = imageView.getX() + imageView.getBoundsInParent().getWidth();
            }
            if (tempY > imageView.getY() + imageView.getBoundsInParent().getHeight()){
                tempY = imageView.getY() + imageView.getBoundsInParent().getHeight();
            }

            if (tempX - dragContext.mouseAnchorX > 0){
                rect.setX(dragContext.mouseAnchorX);
                rect.setWidth(tempX - dragContext.mouseAnchorX);
            }
            else {
                if (tempX < imageView.getX()){
                    tempX = imageView.getX();
                }

                rect.setX(tempX);
                rect.setWidth(dragContext.mouseAnchorX - rect.getX());
            }

            if (tempY - dragContext.mouseAnchorY > 0) {
                rect.setHeight(tempY - dragContext.mouseAnchorY);
                rect.setY(dragContext.mouseAnchorY);
            }
            else {
                if (tempY < imageView.getY()){
                    tempY = imageView.getY();

                }

                rect.setY(tempY);
                rect.setHeight(dragContext.mouseAnchorY - rect.getY());
            }
        };


        private static final class DragContext {
            double mouseAnchorX;
            double mouseAnchorY;
        }
    }

}
