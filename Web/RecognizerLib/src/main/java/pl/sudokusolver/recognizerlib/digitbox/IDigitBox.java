package pl.sudokusolver.recognizerlib.digitbox;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.util.Optional;

/**
 * Abstrakcyjna reprezentacja algorytmów do znajdywania cyfry na zdjęciu
 */
public interface IDigitBox {
    /**
     * @param input macierz z zdjęciem, na którym znajduje się cyfra. Macierz powinna być typu CV_8U lub CV_8UC1
     * @return kwadrat, w którym znajduje się cyfra lub Optional.Empty() gdzy nie uda się znaleźć cyfry
     */
    Optional<Rect> getDigitBox(Mat input);
}
