package pl.sudokusolver.recognizerlib.ocr;

import org.opencv.core.Mat;

/**
 * Interfejs działający na równi z IRecognizer.<br>
 * Pozwala na rozpoznanie zdjęcie, w "płaskiej" formie, tz. możemy rozpoznawać
 * zdjęcia, które zapisane są w wektorze a nie w dwuwymiarowej tablicy.
 */
public interface IRowRecognizer {
    /**
     * @param img wektor (jego wymiary zależa od użytego modelu), na której znajduje się cyfra do rozpoznania
     * @return rozpoznana liczba
     */
    int rowRecognize(Mat img);
}
