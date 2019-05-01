package pl.sudokusolver.recognizerlib.extractors.cells;

import org.opencv.core.Mat;

import java.util.List;

public interface CellsExtractStrategy {
    List<Mat> getCells(Mat grid);
}
