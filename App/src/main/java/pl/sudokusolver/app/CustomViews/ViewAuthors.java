package pl.sudokusolver.app.CustomViews;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pl.sudokusolver.app.Theme;
import pl.sudokusolver.app.Values;

public class ViewAuthors extends VBox {

    public ViewAuthors(){
        init();
    }

    private void init(){
        Text projectManager = new Text(Values.PROJECT_MANAGER);
        projectManager.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 24;");
        Text projectManagerName = new Text(Values.PROJECT_MANAGER_NAME);
        projectManagerName.setStyle("-fx-font-size: 18;");

        Text systemEngineer = new Text(Values.SYSTEM_ENGINEER);
        systemEngineer.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 24;");
        Text systemEngineerName = new Text(Values.SYSTEM_ENGINEER_NAME);
        systemEngineerName.setStyle("-fx-font-size: 18;");

        Text programmers = new Text(Values.PROGRAMMERS);
        programmers.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 24;");
        Text programmersNames = new Text(Values.PROGRAMMERS_NAMES);
        programmersNames.setStyle("-fx-font-size: 18;");
        programmersNames.setTextAlignment(TextAlignment.CENTER);

        Text testers = new Text(Values.TESTERS);
        testers.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 24;");
        Text testersNames = new Text(Values.TESTERS_NAMES);
        testersNames.setStyle("-fx-font-size: 18;");
        testersNames.setTextAlignment(TextAlignment.CENTER);

        VBox boxProjectManager = new VBox(projectManager, projectManagerName);
        VBox boxSystemEngineer = new VBox(systemEngineer, systemEngineerName);
        VBox boxProgrammers = new VBox(programmers, programmersNames);
        VBox boxTesters = new VBox(testers, testersNames);

        boxProjectManager.setAlignment(Pos.CENTER);
        boxSystemEngineer.setAlignment(Pos.CENTER);
        boxProgrammers.setAlignment(Pos.CENTER);
        boxTesters.setAlignment(Pos.CENTER);

        this.setAlignment(Pos.CENTER);
        this.setSpacing(15);

        this.getChildren().addAll(boxProjectManager, boxSystemEngineer, boxProgrammers, boxTesters);

    }
}