package pl.sudokusolver.recognizerlib.extractors.cells;

import com.google.common.collect.Lists;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import pl.sudokusolver.recognizerlib.exceptions.CellsExtractionFailedException;

import java.util.List;

/**
 * Implementation of extract cell algorithm.<br>
 * Extraction is being performed by dividing it into equally large pieces.
 */
public class SizeCellsExtractStrategy implements CellsExtractStrategy{
    @Override
    public List<Mat> extract(Mat grid) throws CellsExtractionFailedException {
        // calc size of one cell
        int size = grid.height() / 9;
        if(size <= 0) throw new CellsExtractionFailedException();
        Size cellSize = new Size(size, size);

        // create list of cells
        List<Mat> cells = Lists.newArrayList();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                // cut single cell
                Rect rect = new Rect(new Point(col * size, row * size), cellSize);

                // crete new image from cell rect
                Mat digit = new Mat(grid, rect);
                cells.add(digit);
            }
        }
        return cells;
    }

}
