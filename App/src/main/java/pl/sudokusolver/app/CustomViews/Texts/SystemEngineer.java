package pl.sudokusolver.app.CustomViews.Texts;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import pl.sudokusolver.app.Values;

/**
 * System Engineer layout
 */
public class SystemEngineer extends VBox {

    public SystemEngineer(){
        initSystemEngineer();
    }

    /**
     * Function to initialize whole layout
     */
    private void initSystemEngineer(){
        getChildren().addAll(initSystemEngineerText(), initSystemEngineerName());
        setAlignment(Pos.CENTER);
    }

    /**
     * Function to initialize text saying 'System Engineer'
     * @return  completely create text
     */
    private Text initSystemEngineerText(){
        Text systemEngineerText = new Text(Values.SYSTEM_ENGINEER);
        systemEngineerText.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 24;");
        return systemEngineerText;
    }

    /**
     * Function to initialize text saying name of System Engineer
     * @return  completely create text
     */
    private Text initSystemEngineerName(){
        Text systemEngineerName = new Text(Values.SYSTEM_ENGINEER_NAME);
        systemEngineerName.setStyle("-fx-font-size: 18;");
        return systemEngineerName;
    }
}
