package App.src.sample.Scenes;

import App.src.sample.CustomViews.Canvas;
import App.src.sample.CustomViews.Menu;
import App.src.sample.CustomViews.RightSide;
import App.src.sample.Test;
import App.src.sample.Theme;
import App.src.sample.Values;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StageMain extends Stage implements Test {

    private BorderPane vBox;
    private Canvas canvas;

    public StageMain(){
        init();
    }

    @Override
    public void changed(){
        canvas.update();
        if (Values.THEME == Theme.LIGHT){
            vBox.setStyle("-fx-background-color: #F0F0F0;");
        }
        else{
            vBox.setStyle("-fx-background-color: #34495E;");
        }
    }

    private void init(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        RightSide temp = new RightSide(primaryScreenBounds.getWidth() * 0.75f, primaryScreenBounds.getHeight() * 0.75f);
        Menu menu = new Menu(this, this);
        canvas = new Canvas();

        this.setX(primaryScreenBounds.getMinX());
        this.setY(primaryScreenBounds.getMinY());

        this.setWidth(primaryScreenBounds.getWidth() * 0.75f);
        this.setHeight(primaryScreenBounds.getHeight() * 0.75f);

        this.setMinWidth(primaryScreenBounds.getWidth() * 0.66f);
        this.setMinHeight(primaryScreenBounds.getHeight() * 0.75f);

        vBox = new BorderPane();
        vBox.setTop(menu);
        vBox.setRight(temp);
        vBox.setLeft(canvas);

        BorderPane.setMargin(temp, new Insets(this.getHeight() / 20, this.getWidth() / 40,
                this.getHeight() / 20, this.getWidth() / 40));

        if (Values.THEME == Theme.LIGHT){
            vBox.setStyle("-fx-background-color: #F0F0F0;");
        }
        else{
            vBox.setStyle("-fx-background-color: #34495E;");
        }

        BorderPane.setMargin(temp, new Insets(getHeight() / 20,getWidth() / 20,getHeight() / 20,0));
        //temp.prefWidthProperty().bind(vBox.widthProperty().multiply(0.45f));
        //temp.prefHeightProperty().bind(vBox.heightProperty().multiply(0.9f));
        canvas.widthProperty().bind(vBox.widthProperty().multiply(0.45f));
        canvas.heightProperty().bind(vBox.heightProperty().multiply(0.95f));
        temp.setPadding(new Insets(0,0,0,this.getWidth() / 20));

        this.setTitle(Values.NAME);
        this.setScene(new Scene(vBox));
        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                Values.openStages.focusStage(this);
            }
        });


        this.setOnCloseRequest((event) -> {
            Platform.exit();
            System.exit(0);
        });

        this.show();
    }
}
