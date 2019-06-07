package pl.sudokusolver.recognizerlib.digitbox;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.util.Optional;

/**
 * Abstract interpretation of finding digit algorithm.
 */
public interface IDigitBox {
    /**
     * Try find digit on given img. If found something then return it, else just return <code>Optional.Empty()</code>
     * @param input matrix which contains image of digit. Matrix should be type of <code>CV_8U</code>.
     * @return bounding rect with image or <code>Optional.Empty()</code> when algorithm couldn't find digit.
     */
    Optional<Rect> getDigitBox(Mat input);
}
