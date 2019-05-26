package pl.sudokusolver.app.CustomViews.Texts;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import pl.sudokusolver.app.Values;

/**
 * Project Manager layout
 */
public class ProjectManager extends VBox {

    public ProjectManager(){
        initProjectManager();
    }

    /**
     * Function to initialize whole layout
     */
    private void initProjectManager(){
        getChildren().addAll(initProjectManagerText(), initProjectManagerName());
        setAlignment(Pos.CENTER);
    }

    /**
     * Function to initialize text saying 'Project Manager'
     * @return  completely created text
     */
    private Text initProjectManagerText(){
        Text projectManagerText = new Text(Values.PROJECT_MANAGER);
        projectManagerText.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 24;");
        return projectManagerText;
    }

    /**
     * Function to initialize text saying name of Project Manager
     * @return  completely created text
     */
    private Text initProjectManagerName(){
        Text projectManagerName = new Text(Values.PROJECT_MANAGER_NAME);
        projectManagerName.setStyle("-fx-font-size: 18;");
        return projectManagerName;
    }
}
