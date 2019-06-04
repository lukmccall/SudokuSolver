package pl.sudokusolver.recognizerlib.ocr;

import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.DigitExtractionFailedException;
import pl.sudokusolver.recognizerlib.utility.Pair;

/**
 * Abstrakcyjne przedstawienie ocrów
 */
public interface IRecognizer {
    /**
     * @param img macierz, na której znajduje się cyfra do rozpoznania
     * @return parę, która składa się z rozpoznanej liczby i pewności z jaką została rozpoznana. <b>W aktualnej wersji drugi parametr jest nieujednolicony</b>.
     */
    Pair<Integer, Double> recognize(Mat img) throws DigitExtractionFailedException;
}
