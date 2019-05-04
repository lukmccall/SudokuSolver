package pl.sudokusolver.recognizerlib.ocr.ml;

import org.opencv.core.CvException;

/**
 * Impelementacja tego interfejsu oznacza, że ocr może być wczytany z pliku oraz do niego zapisany.
 */
public interface ILoader {
    /**
     * @param url absolutna ścieżka do pliku z migawką ocru
     * @throws CvException gdzy nie udało się otworzyć pliku
     */
    void load(String url) throws CvException;

    /**
     * @param url ścieżka, gdzie ma zostać wykonana migawka ocru
     */
    void dump(String url);
}
