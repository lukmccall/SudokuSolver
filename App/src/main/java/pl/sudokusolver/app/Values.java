package pl.sudokusolver.app;

import okhttp3.MediaType;

public class Values {

    // Server variable
    public static final String SERVER_URL = "http://ns3102827.ip-54-37-129.eu:10020/app/";
    public static final MediaType SERVER_REQUEST_TYPE = MediaType.get("application/json; charset=utf-8");
    public static final MediaType SERVER_IMG_TYPE = MediaType.get("image/jpg");
    public static Theme THEME;

    public static String SAVE_FILE = "settings.txt";
    public static String INITIAL_IMAGE = "initial_image.png";

    public static String NAME = "SUDOKU SOLVER";

    public static final String ERROR = "ERROR";
    public static final String LOAD = "WCZYTAJ";
    public static final String SOLVE = "ROZWIĄŻ";
    public static final String ABOUT = "O programie";
    public static final String EXIT =  "Wyjscie";
    public static final String AUTHORS = "Autorzy";
    public static final String BRIGHT = "Jasny";
    public static final String DARK = "Ciemny";
    public static final String HELP = "POMOC";
    public static final String THEMES = "MOTYW";
    public static final String CUT = "PRZYTNIJ";
    public static final String ADVANCED = "ZAAWANSOWANE";
    public static final String ACCEPT = "ZATWIERDŹ";

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
    public static final String ANN = "ANN";

    public static final String STRICT_MODE = "STRICT MODE";

    public static final String SET = "OK";
    public static final String DEFAULT = "USTAW DOMYŚLNE";

    public static final String CLEAR = "Wyczyść";
    public static final String SUDOKU = "SUDOKU";

    public static final String E000 = "Nieznany bład.";
    public static final String E001 = "Nie wykryto odpowiedniej wersji JAVY.";
    public static final String E002 = "Nieznany format pliku (tylko .jpg, .png).";

    public static final String E003 = "Niewykryte poprawne sudoku na zdjęciu.";
    public static final String E004 = "Sudoku nie posiada rozwiązania.";
    public static final String E005 = "Brak połączenia z siecią.";
    public static final String E006 = "Serwer nieaktywny.";
    public static final String E007 = "Plik nie istnieje.";

    public static final String E008 = "Nie można wpisać tej wartości w to pole.";
    public static final String E009 = "Nie można wyciąć bez zaznaczenia.";
    public static final String E010 = "Serwer nie odpowiedział wystarczająco szybko.";
    public static final String E011 = "Plik nie jest poprawny.";
    public static final String E012 = "Nie udało się otworzyć zdjęcia.";


}
