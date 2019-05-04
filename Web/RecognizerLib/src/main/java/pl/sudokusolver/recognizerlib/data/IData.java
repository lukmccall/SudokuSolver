package pl.sudokusolver.recognizerlib.data;

import org.opencv.core.Mat;

/**
 * Abstrakcyjne przedstawienie danych. Pakiet taki może być użyty przy tworzeniu nowych obiektów ocr.
 */
public interface IData {
    /**
     * @return Macierz przechowującą próbki (zdjęcia)
     */
    Mat getData();

    /**
     * @return Macierz przehowującą etykiety
     */
    Mat getLabels();


    /**
     * @return Typ próbek. Parametr z openCV
     */
    int getSampleType();

    /**
     * @return Rozmiar pojedynczej próbki (zdjęcia)
     */
    short getSize();

    /**
     * @return Typ danych
     */
    DataType getDataType();
}
