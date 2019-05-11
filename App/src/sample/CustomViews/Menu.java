package App.src.sample.CustomViews;

import App.src.sample.Scenes.StageAbout;
import App.src.sample.Scenes.StageAuthors;
import App.src.sample.ThemeChangeListener;
import javafx.application.Platform;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import App.src.sample.Theme;
import App.src.sample.Utilities;
import App.src.sample.Values;

public class Menu extends MenuBar {

    private StageAbout aboutStage;
    private StageAuthors authorsStage;

    public Menu(Stage stage, ThemeChangeListener themeChangeListener){
        super();
        init(stage, themeChangeListener);
    }

    private void init(Stage stage, ThemeChangeListener themeChangeListener){
        javafx.scene.control.Menu helpMenu = new javafx.scene.control.Menu(Values.HELP);

        MenuItem about = new MenuItem(Values.ABOUT);
        MenuItem exit = new MenuItem(Values.EXIT);
        MenuItem authors = new MenuItem(Values.AUTHORS);

        helpMenu.getItems().add(about);
        helpMenu.getItems().add(exit);
        helpMenu.getItems().add(authors);

        about.setOnAction((event) -> {
            if (aboutStage != null) {
                if (aboutStage.isShowing()) aboutStage.toFront();
                else aboutStage.show();
                return;
            }

            aboutStage = new StageAbout();
        });

        exit.setOnAction((event) -> {
            stage.close();
            Platform.exit();
            System.exit(0);
        });

        authors.setOnAction((event) -> {
            if (authorsStage != null) {
                if (authorsStage.isShowing()) authorsStage.toFront();
                else authorsStage.show();
                return;
            }

            authorsStage = new StageAuthors();
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
                    authorsStage.change();
                    authorsStage.toBack();
                }

                if (aboutStage != null){
                    aboutStage.change();
                    aboutStage.toBack();
                }

                themeChangeListener.changed();
                Utilities.saveFile("Theme: LIGHT");
            }
        });

        darkTheme.setOnAction((event) -> {
            if (Values.THEME != Theme.DARK){
                Values.THEME = Theme.DARK;

                if (authorsStage != null){
                    authorsStage.change();
                    authorsStage.toBack();
                }

                if (aboutStage != null){
                    aboutStage.change();
                    aboutStage.toBack();
                }
                themeChangeListener.changed();

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
