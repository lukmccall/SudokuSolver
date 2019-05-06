package App.src.sample;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 * Implementation of a SimpleListProperty that will store our ObservableArrayList
 */
public class OpenStages<Stage> extends SimpleListProperty<Stage> {

    /**
     * Constructor that creates an ObservableArrayList
     */
    public OpenStages() {
        super(FXCollections.observableArrayList());
    }

    /**
     * Removes this Stage from the list and re-adds it at index 0
     */
    public void focusStage(Stage stage) {
        this.remove(stage);
        this.add(0, stage);
    }
}