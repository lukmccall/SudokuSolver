package pl.sudokusolver.app.CustomViews;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.sudokusolver.app.Listeners.ImageListener;
import pl.sudokusolver.app.Scenes.StageError;
import pl.sudokusolver.app.Singleton;
import pl.sudokusolver.app.Utilities;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Class used to indicate what part of image the user is trying to cut
 */
class ViewSelection {

    final private ViewSelection.DragContext dragContext = new ViewSelection.DragContext();
    private Rectangle rect;

    private Pane group;
    private ImageView imageView;
    private FileChooser.ExtensionFilter imageFilter;
    private ImageListener imageListener;


    ViewSelection(Pane group, ImageView imageView, ImageListener imageListener) {
        this.group = group;
        this.imageView = imageView;
        this.imageListener = imageListener;
        init();
    }

    /**
     * Function to initialize whole class
     */
    private void init(){
        initRect();
        initPane();
    }

    /**
     * Function to initialize pane and add mouse events to it
     */
    private void initPane(){
        group.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
        group.addEventHandler(MouseEvent.MOUSE_CLICKED, onMouseClickedEventHandler);
        group.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
    }

    /**
     * Function to initialize rectangle used in indicating what part of image is chosen by user
     */
    private void initRect(){
        rect = new Rectangle( 0,0,0,0);
        rect.setStroke(Color.BLUE);
        rect.setStrokeWidth(1);
        rect.setStrokeLineCap(StrokeLineCap.ROUND);
        rect.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.6));
    }

    /**
     * Function to get rectangle currently chosen by user
     * @return  whole rectangle
     */
    Rectangle2D getRectangle(){
        Rectangle2D temp = new Rectangle2D(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        clearRect();
        return temp;
    }

    /**
     * Function to make chosen rectangle go back to it's default stage
     */
    private void clearRect(){
        rect.setX(0);
        rect.setY(0);
        rect.setWidth(0);
        rect.setHeight(0);
    }

    /**
     * Function that handles mouse press on image
     */
    private EventHandler<MouseEvent> onMousePressedEventHandler = (event) -> {
        if (event.isSecondaryButtonDown())
            return;

        // remove old rect
        clearRect();
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

    private EventHandler<MouseEvent> onMouseClickedEventHandler = (event) -> {
        if (rect.getWidth() <= 0.5f && rect.getHeight() <= 0.5f)
        if (imageFilter == null){
            imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");

            final FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(imageFilter);

            File file = fileChooser.showOpenDialog((Stage) imageListener);
            Singleton.getInstance().unblock();

            if (file != null) {
                if (!(Utilities.getFileExtension(file).toLowerCase().equals("jpg") || Utilities.getFileExtension(file).toLowerCase().equals("png"))){
                    new StageError(2, "Nieznany format pliku (tylko .jpg, .png).");
                    imageFilter = null;
                    return;
                }

                try {
                    if (ImageIO.read(file) == null) {
                        new StageError(11, "Plik nie jest poprawny.");
                        imageFilter = null;
                        return;
                    }
                } catch(IOException ex) {
                    new StageError(11, "Plik nie jest poprawny.");
                    imageFilter = null;
                    return;
                }

                Image image;
                try{
                    image = new Image(new FileInputStream(file.getPath()));
                }
                catch (Exception e){
                    new StageError(12, "Nie udało się otworzyć zdjęcia.");
                    imageFilter = null;
                    return;
                }

                imageView.setImage(image);
            }
            else{
                ((Stage) imageListener).close();
                //new StageError("Wybierz zdjęcie");
            }

            imageFilter = null;
        }
    };

    /**
     * Function that handled mouse drag on image
     */
    private EventHandler<MouseEvent> onMouseDraggedEventHandler = (event) -> {
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
