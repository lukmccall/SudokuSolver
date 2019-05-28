package pl.sudokusolver.app.CustomViews;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

public class ViewSelection {

    final ViewSelection.DragContext dragContext = new ViewSelection.DragContext();
    Rectangle rect;

    Pane group;
    ImageView imageView;

    ViewSelection(Pane group, ImageView imageView) {

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

    Rectangle2D getRectangle(){
        Rectangle2D temp = new Rectangle2D(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        rect.setX(0);
        rect.setY(0);
        rect.setWidth(0);
        rect.setHeight(0);
        return temp;
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
