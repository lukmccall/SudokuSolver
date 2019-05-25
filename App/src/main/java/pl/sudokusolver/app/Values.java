package pl.sudokusolver.app;

import javafx.stage.Stage;
import okhttp3.MediaType;

public class Values {
    // Server variable
    public static final String SERVER_URL = "http://localhost:8080/";
    public static final MediaType SERVER_REQUEST_TYPE = MediaType.get("application/json; charset=utf-8");
    public static final MediaType SERVER_IMG_TYPE = MediaType.get("image/jpg");
    public static Theme THEME;

    public static String SAVE_FILE = "settings.txt";
    public static String INITIAL_IMAGE = "initial_image.png";

    public static String NAME = "SUDOKU SOLVER";

    public static final String LOAD = "LOAD";
    public static final String SOLVE = "SOLVE";
    public static final String ABOUT = "ABOUT";
    public static final String EXIT =  "EXIT";
    public static final String AUTHORS = "AUTHORS";
    public static final String BRIGHT = "BRIGHT";
    public static final String DARK = "DARK";
    public static final String HELP = "HELP";
    public static final String THEMES = "THEMES";
    public static final String CUT = "CUT";
    public static final String ADVANCED = "ADVANCED";
    public static final String ACCEPT = "ACCEPT";

    public static final String PROJECT_MANAGER = "Project Manager:";
    public static final String PROJECT_MANAGER_NAME = "Daniel Dobrowolski";
    public static final String SYSTEM_ENGINEER = "System Engineer:";
    public static final String SYSTEM_ENGINEER_NAME = "Małgorzata Dymek";
    public static final String PROGRAMMERS = "Programmers:";
    public static final String PROGRAMMERS_NAMES = "Tomasz Janik\nŁukasz Kosmaty";
    public static final String TESTERS = "Testers:";
    public static final String TESTERS_NAMES = "Nikodem Kwaśniak\nDawid Szczerba";

    public static final String DESCRIPTION = "Aplikacja przygotowana na przedmiot:\nInżynieria Oprogramowania";

    public static final String LINE_THICKNESS = "GRUBOŚĆ LINII";
    public static final String PROGING = "PROGOWANIE";
    public static final String DISTANCE = "ODLEGŁOŚĆ";
    public static final String GAUSSIAN_BLUR = "ROZMYCIE GAUSSA";

    public static final String SET = "SET";
    public static final String DEFAULT = "DEFAULT";

    public static final String CLEAR = "CLEAR";
    public static final String SUDOKU = "SUDOKU";

    public static final String E001 = "WRONG JAVA VERSION";
    public static final String E002 = "WRONG FILE EXTENSION";
    public static final String E003 = "COULDN'T RECOGNIZE SUDOKU";
    public static final String E004 = "SUDOKU ISN'T SOLVABLE";
    public static final String E005 = "NO INTERNET CONNECTION";
    public static final String E006 = "SERVER IS DOWN";
    public static final String E007 = "FILE DOESN'T EXIST";


}
