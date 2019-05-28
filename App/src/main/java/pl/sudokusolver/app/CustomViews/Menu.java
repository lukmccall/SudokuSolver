package pl.sudokusolver.app.CustomViews;

import pl.sudokusolver.app.*;
import pl.sudokusolver.app.Listeners.MenuListener;
import pl.sudokusolver.app.Scenes.StageAbout;
import pl.sudokusolver.app.Scenes.StageAuthors;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;

/**
 * Menu that can be used by user in the main stage
 */
public class Menu extends MenuBar {

    private StageAbout aboutStage;
    private StageAuthors authorsStage;
    private MenuListener menuListener;

    public Menu(MenuListener menuListener){
        super();

        this.menuListener = menuListener;
        getMenus().addAll(initHelpMenu(), initThemesMenu(), initClearMenu());
    }

    /**
     * Function to initialize help menu
     * @return  completely created help menu
     */
    private javafx.scene.control.Menu initHelpMenu(){
        javafx.scene.control.Menu helpMenu = new javafx.scene.control.Menu(Values.HELP);

        helpMenu.getItems().add(initAbout());
        helpMenu.getItems().add(initAuthors());
        helpMenu.getItems().add(initExit());

        return helpMenu;
    }

    /**
     * Function to initialize about tab in help menu
     * @return  completely created about tab
     */
    private MenuItem initAbout(){
        MenuItem about = new MenuItem(Values.ABOUT);

        about.setOnAction((event) -> {
            if (Singleton.getInstance().isBlocked()) return;

            if (aboutStage != null) {
                if (aboutStage.isShowing()) aboutStage.toFront();
                else aboutStage.show();
                return;
            }

            aboutStage = new StageAbout();
        });

        return about;
    }

    /**
     * Function to initialize authors tab in help menu
     * @return  completely created authors tab
     */
    private MenuItem initAuthors(){
        MenuItem authors = new MenuItem(Values.AUTHORS);

        authors.setOnAction((event) -> {
            if (Singleton.getInstance().isBlocked()) return;

            if (authorsStage != null) {
                if (authorsStage.isShowing()) authorsStage.toFront();
                else authorsStage.show();
                return;
            }

            authorsStage = new StageAuthors();
        });

        return authors;
    }

    /**
     * Function to initialize exit tab in help menu
     * @return  completely created exit tab
     */
    private MenuItem initExit(){
        MenuItem exit = new MenuItem(Values.EXIT);

        exit.setOnAction((event) -> {
            if (Singleton.getInstance().isBlocked()) return;
            menuListener.exit();
        });

        return exit;
    }

    /**
     * Function to initialize themes menu
     * @return  completely created themes menu
     */
    private javafx.scene.control.Menu initThemesMenu(){
        javafx.scene.control.Menu themesMenu = new javafx.scene.control.Menu(Values.THEMES);

        RadioMenuItem lightTheme = initLightTheme();
        RadioMenuItem darkTheme = initDarkTheme();

        initToggleGroup(lightTheme, darkTheme);

        themesMenu.getItems().add(lightTheme);
        themesMenu.getItems().add(darkTheme);

        return themesMenu;
    }

    /**
     * Function to initialize light theme tab in themes menu
     * @return  completely created light theme tab
     */
    private RadioMenuItem initLightTheme(){
        RadioMenuItem lightTheme = new RadioMenuItem(Values.BRIGHT);

        if (Values.THEME == Theme.LIGHT){
            lightTheme.setSelected(true);
        }

        lightTheme.setOnAction((event) -> {
            if (Singleton.getInstance().isBlocked()) return;

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
                Utilities.saveFile("Theme: LIGHT");
            }
        });

        return lightTheme;
    }

    /**
     * Function to initialize dark theme tab in themes menu
     * @return  completely created dark theme tab
     */
    private RadioMenuItem initDarkTheme(){
        RadioMenuItem darkTheme = new RadioMenuItem(Values.DARK);

        if (Values.THEME == Theme.DARK){
            darkTheme.setSelected(true);
        }

        darkTheme.setOnAction((event) -> {
            if (Singleton.getInstance().isBlocked()) return;

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

                Utilities.saveFile("Theme: DARK");
            }
        });

        return darkTheme;
    }

    /**
     * Function to create toggle group of light and dark theme in themes menu
     * @param lightTheme    light theme from themes menu
     * @param darkTheme     dark theme from themes menu
     */
    private void initToggleGroup(RadioMenuItem lightTheme, RadioMenuItem darkTheme){
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().add(lightTheme);
        toggleGroup.getToggles().add(darkTheme);
    }

    /**
     * Function to initialize clear menu
     * @return  completely created clear menu
     */
    private javafx.scene.control.Menu initClearMenu(){
        javafx.scene.control.Menu clearMenu = new javafx.scene.control.Menu(Values.SUDOKU);

        clearMenu.getItems().add(initClear());

        return clearMenu;
    }

    /**
     * Function to initialize clear tab in clear menu
     * @return  completely created clear tab
     */
    private MenuItem initClear(){
        MenuItem clear = new MenuItem(Values.CLEAR);

        clear.setOnAction((event) -> {
            if (Singleton.getInstance().isBlocked()) return;
            menuListener.clear();
        });

        return clear;
    }
}
