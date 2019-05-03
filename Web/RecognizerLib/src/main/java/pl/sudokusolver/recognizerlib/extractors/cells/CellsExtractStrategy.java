package pl.sudokusolver.recognizerlib.extractors.cells;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.CellsExtractionFailedException;

import java.util.List;

public interface CellsExtractStrategy {
    List<Mat> extract(Mat grid) throws CellsExtractionFailedException;
}
