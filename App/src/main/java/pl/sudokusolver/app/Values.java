package pl.sudokusolver.app;

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

    public static final String ERROR = "ERROR";
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

    public static final String LINE_THICKNESS = "LINE THICKNESS";
    public static final String PROGING = "PROGING";
    public static final String DISTANCE = "DISTANCE";
    public static final String GAUSSIAN_BLUR = "GAUSSIAN BLUR";
    public static final String LINE_THRESHOLD = "LINE THRESHOLD";
    public static final String LINE_GAP = "LINE GAP";
    public static final String MIN_LINE_SIZE = "MINIMUM LINE SIZE";
    public static final String BLUR_BLOCK_SIZE = "BLUR BLOCK SIZE";
    public static final String BLUR_C = "BLUR C";
    public static final String BLUR_SIZE = "BLUR SIZE";
    public static final String SCALING = "SCALING";
    public static final String RECOGNITION = "RECOGNITION";
    public static final String FIXED_WIDTH_SCALING = "FIXED WIDTH SCALING";
    public static final String MAX_AXIS_RESIZE = "MAX AXIS RESIZE";
    public static final String NONE = "NONE";
    public static final String SVM = "SVM";
    public static final String TESSERACT = "TESSERACT";

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
    public static final String E008 = "CAN'T INSERT INTO THIS FIELD";
    public static final String E009 = "CAN'T CUT WITH EMPTY SELECTION";


}
