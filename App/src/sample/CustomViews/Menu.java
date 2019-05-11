package App.src.sample.CustomViews;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import App.src.sample.Test;
import App.src.sample.Theme;
import App.src.sample.Utilities;
import App.src.sample.Values;

public class Menu extends MenuBar {

    private Stage aboutStage;
    private Stage authorsStage;

    public Menu(Stage stage, Test test){
        super();
        init(stage, test);
    }

    private void init(Stage stage, Test test){
        javafx.scene.control.Menu helpMenu = new javafx.scene.control.Menu(Values.HELP);

        MenuItem about = new MenuItem(Values.ABOUT);
        MenuItem exit = new MenuItem(Values.EXIT);
        MenuItem authors = new MenuItem(Values.AUTHORS);

        helpMenu.getItems().add(about);
        helpMenu.getItems().add(exit);
        helpMenu.getItems().add(authors);

        about.setOnAction((event) -> {
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

            Scene secondScene = new Scene(new ViewAbout(),
                    primaryScreenBounds.getWidth() * 0.375f, primaryScreenBounds.getHeight() * 0.66f);

            if (aboutStage != null) {
                if (aboutStage.isShowing()) aboutStage.toFront();
                else aboutStage.show();
                aboutStage.setScene(secondScene);
                return;
            }

            aboutStage = new Stage();

            aboutStage.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (aboutStage != null && newValue) {
                    Values.openStages.focusStage(aboutStage);
                }
            });

            aboutStage.setTitle(Values.AUTHORS);
            aboutStage.setScene(secondScene);

            aboutStage.setWidth(primaryScreenBounds.getWidth() * 0.375f);
            aboutStage.setHeight(primaryScreenBounds.getHeight() * 0.75f);

            aboutStage.setMinWidth(primaryScreenBounds.getWidth() * 0.375f);
            aboutStage.setMinHeight(primaryScreenBounds.getHeight() * 0.75f);
            aboutStage.setResizable(false);

            aboutStage.show();
        });

        exit.setOnAction((event) -> {
            stage.close();
            Platform.exit();
            System.exit(0);
            //outputTextArea.appendText("Button Action\n");
        });

        authors.setOnAction((event) -> {
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

            Scene secondScene = new Scene(new ViewAuthors(),
                    primaryScreenBounds.getWidth() * 0.375f, primaryScreenBounds.getHeight() * 0.66f);

            if (authorsStage != null) {
                authorsStage.setScene(secondScene);
                if (authorsStage.isShowing()) authorsStage.toFront();
                else authorsStage.show();
                return;
            }


            authorsStage = new Stage();

            authorsStage.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (authorsStage != null && newValue) {
                    Values.openStages.focusStage(authorsStage);
                }
            });

            authorsStage.setTitle(Values.AUTHORS);
            authorsStage.setScene(secondScene);

            authorsStage.setWidth(primaryScreenBounds.getWidth() * 0.375f);
            authorsStage.setHeight(primaryScreenBounds.getHeight() * 0.75f);

            authorsStage.setMinWidth(primaryScreenBounds.getWidth() * 0.375f);
            authorsStage.setMinHeight(primaryScreenBounds.getHeight() * 0.75f);
            authorsStage.setResizable(false);

            authorsStage.show();
        });

        javafx.scene.control.Menu themesMenu = new javafx.scene.control.Menu(Values.THEMES);

        RadioMenuItem lightTheme = new RadioMenuItem(Values.BRIGHT);
        RadioMenuItem darkTheme = new RadioMenuItem(Values.DARK);

        if (Values.THEME == Theme.LIGHT){
            lightTheme.setSelected(true);
        }
        else{
            darkTheme.setSelected(true);
        }

        lightTheme.setOnAction((event) -> {
            if (Values.THEME != Theme.LIGHT){
                Values.THEME = Theme.LIGHT;

                if (authorsStage != null){
                    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

                    Scene secondScene = new Scene(new ViewAuthors(),
                            primaryScreenBounds.getWidth() * 0.375f, primaryScreenBounds.getHeight() * 0.66f);
                    authorsStage.setScene(secondScene);

                    authorsStage.toBack();
                }

                if (aboutStage != null){
                    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

                    Scene secondScene = new Scene(new ViewAbout(),
                            primaryScreenBounds.getWidth() * 0.375f, primaryScreenBounds.getHeight() * 0.66f);
                    aboutStage.setScene(secondScene);

                    aboutStage.toBack();
                }
                test.changed();

                Utilities.saveFile("Theme: LIGHT");
            }
        });

        darkTheme.setOnAction((event) -> {
            if (Values.THEME != Theme.DARK){
                Values.THEME = Theme.DARK;

                if (authorsStage != null){
                    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

                    Scene secondScene = new Scene(new ViewAuthors(),
                            primaryScreenBounds.getWidth() * 0.375f, primaryScreenBounds.getHeight() * 0.66f);
                    authorsStage.setScene(secondScene);

                    authorsStage.toBack();
                }

                if (aboutStage != null){
                    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

                    Scene secondScene = new Scene(new ViewAbout(),
                            primaryScreenBounds.getWidth() * 0.375f, primaryScreenBounds.getHeight() * 0.66f);
                    aboutStage.setScene(secondScene);

                    aboutStage.toBack();
                }
                test.changed();

                Utilities.saveFile("Theme: DARK");
            }
        });

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().add(lightTheme);
        toggleGroup.getToggles().add(darkTheme);

        themesMenu.getItems().add(lightTheme);
        themesMenu.getItems().add(darkTheme);

        this.getMenus().addAll(helpMenu, themesMenu);
    }
}
