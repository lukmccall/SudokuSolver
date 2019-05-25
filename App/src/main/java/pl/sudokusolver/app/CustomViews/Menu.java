package pl.sudokusolver.app.CustomViews;

import pl.sudokusolver.app.*;
import pl.sudokusolver.app.Scenes.StageAbout;
import pl.sudokusolver.app.Scenes.StageAuthors;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class Menu extends MenuBar {

    private StageAbout aboutStage;
    private StageAuthors authorsStage;
    private MenuListener menuListener;
    //private ThemeChangeListener themeChangeListener;

    public Menu(MenuListener menuListener){
        super();
        this.menuListener = menuListener;
        //this.themeChangeListener = themeChangeListener;

        init();
    }

    private void init(){

        javafx.scene.control.Menu helpMenu = new javafx.scene.control.Menu(Values.HELP);

        MenuItem about = new MenuItem(Values.ABOUT);
        MenuItem exit = new MenuItem(Values.EXIT);
        MenuItem authors = new MenuItem(Values.AUTHORS);

        helpMenu.getItems().add(about);
        helpMenu.getItems().add(authors);
        helpMenu.getItems().add(exit);

        about.setOnAction((event) -> {
            if (aboutStage != null) {
                if (aboutStage.isShowing()) aboutStage.toFront();
                else aboutStage.show();
                return;
            }

            aboutStage = new StageAbout();
        });

        exit.setOnAction((event) -> {
            menuListener.exit();
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

                menuListener.change();
                //themeChangeListener.changed();
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
                menuListener.change();
                //themeChangeListener.changed();

                Utilities.saveFile("Theme: DARK");
            }
        });

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().add(lightTheme);
        toggleGroup.getToggles().add(darkTheme);

        themesMenu.getItems().add(lightTheme);
        themesMenu.getItems().add(darkTheme);

        javafx.scene.control.Menu clearMenu = new javafx.scene.control.Menu(Values.SUDOKU);
        MenuItem clear = new MenuItem(Values.CLEAR);

        clear.setOnAction((event) -> {
            menuListener.clear();
        });

        clearMenu.getItems().add(clear);

        this.getMenus().addAll(helpMenu, themesMenu, clearMenu);
    }
}
