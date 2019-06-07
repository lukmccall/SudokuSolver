package pl.sudokusolver.recognizerlib.extractors.cells;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.CellsExtractionFailedException;

import java.util.List;

/**
 * Abstract interpretation of cell extraction algorithm.
 */
public interface CellsExtractStrategy {
    /**
     * From grid image try to extract 81 cells.
     * @param grid matrix which contains only grid's image. This matrix should be rectangular and be type of <code>CV_8U</code>.
     * @return list which contains 81 images when algorithm correctly cut grid.
     * @throws CellsExtractionFailedException if algorithm couldn't extract digit.
     */
    List<Mat> extract(Mat grid) throws CellsExtractionFailedException;
}
