package App.src.sample.CustomViews;

import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import App.src.sample.Values;

public class HBox extends javafx.scene.layout.HBox {

    public HBox(){
        init();
    }

    private void init(){
        javafx.scene.layout.HBox buttons = new javafx.scene.layout.HBox();

        Button load = new Button(Values.LOAD);
        Button solve = new Button(Values.SOLVE);

        javafx.scene.layout.HBox.setHgrow(load, Priority.ALWAYS);
        javafx.scene.layout.HBox.setHgrow(solve, Priority.ALWAYS);

        load.setMaxWidth(Double.MAX_VALUE);
        solve.setMaxWidth(Double.MAX_VALUE);

        buttons.getChildren().addAll(load, solve);
        this.getChildren().add(buttons);
    }

    @Override
    public boolean isResizable() {
        return true;
    }

}
