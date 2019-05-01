package pl.sudokusolver.recognizerlib.extractors.cells;

import com.google.common.collect.Lists;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;

import java.util.List;

public class SizeCellsExtractStrategy implements CellsExtractStrategy{
    @Override
    public List<Mat> getCells(Mat grid) {
        int size = grid.height() / 9;

        Size cellSize = new Size(size, size);
        List<Mat> cells = Lists.newArrayList();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Rect rect = new Rect(new Point(col * size, row * size), cellSize);

                Mat digit = new Mat(grid, rect).clone();
                cells.add(digit);
            }
        }
        return cells;
    }

}
