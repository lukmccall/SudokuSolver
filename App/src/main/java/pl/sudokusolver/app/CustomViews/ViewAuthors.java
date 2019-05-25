package pl.sudokusolver.app.CustomViews;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import pl.sudokusolver.app.CustomViews.Texts.Programmers;
import pl.sudokusolver.app.CustomViews.Texts.ProjectManager;
import pl.sudokusolver.app.CustomViews.Texts.SystemEngineer;
import pl.sudokusolver.app.CustomViews.Texts.Testers;
import pl.sudokusolver.app.Theme;
import pl.sudokusolver.app.Values;

/**
 * About scene authors
 */
public class ViewAuthors extends VBox {

    public ViewAuthors(){
        init();
    }

    /**
     * Function to initialize whole layout
     */
    private void init(){
        setAlignment(Pos.CENTER);
        setSpacing(15);
        setTheme();

        getChildren().addAll(new ProjectManager(), new SystemEngineer(), new Programmers(), new Testers());
    }

    /**
     * Function to set scene theme
     */
    public void setTheme(){
        if (Values.THEME == Theme.LIGHT){
            setStyle("-fx-background-color: #F0F0F0;");
        }
        else{
            setStyle("-fx-background-color: #34495E;");
        }
    }


}
