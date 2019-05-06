package App.src.sample.CustomViews;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import App.src.sample.Values;

public class TitledPane extends HBox {

    public TitledPane(){
        init();
    }

    private void init(){
        //javafx.scene.layout.HBox hBox = new javafx.scene.layout.HBox();
        this.setSpacing(10);
        //this.setStyle("-fx-background-color: #F5F6F7;");

        javafx.scene.layout.VBox helpContent = new javafx.scene.layout.VBox();
        helpContent.getChildren().add(new Label(Values.ABOUT));
        helpContent.getChildren().add(new Label(Values.EXIT));
        helpContent.getChildren().add(new Label(Values.AUTHORS));

//        helpPane.setContent(helpContent);

        javafx.scene.layout.VBox themeContent = new javafx.scene.layout.VBox();
        themeContent.getChildren().add(new Label(Values.BRIGHT));
        themeContent.getChildren().add(new Label(Values.DARK));

        javafx.scene.control.TitledPane helpPane = new javafx.scene.control.TitledPane(Values.HELP, helpContent);
        javafx.scene.control.TitledPane themePane = new javafx.scene.control.TitledPane(Values.THEMES, themeContent);
        helpPane.setExpanded(false);

        //themePane.setContent(themeContent);
        themePane.setExpanded(false);

        this.setPadding(new Insets(5));
        this.getChildren().addAll(helpPane, themePane);

        //this.getChildren().add(hBox);
    }
}
