package App.src.sample.CustomViews;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import App.src.sample.Theme;
import App.src.sample.Values;

public class ViewAbout extends VBox {

    public ViewAbout(){
        init();
    }

    private void init(){
        Text name = new Text(Values.NAME);
        name.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 24;");
        Text description = new Text(Values.DESCRIPTION);
        description.setStyle("-fx-font-size: 18;");
        description.setTextAlignment(TextAlignment.CENTER);
        description.wrappingWidthProperty().bind(this.widthProperty());

        this.setAlignment(Pos.CENTER);
        this.setSpacing(15);
        if (Values.THEME == Theme.LIGHT){
            this.setStyle("-fx-background-color: #F0F0F0;");
        }
        else{
            this.setStyle("-fx-background-color: #34495E;");
        }
        this.getChildren().addAll(name, description);

    }
}
