package pl.sudokusolver.recognizerlib.extractors.grid;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;

import static org.opencv.imgcodecs.Imgcodecs.imread;

// Strategy
public class GridExtractor {
    private Mat sudokuImg;
    private GridExtractStrategy gridExtractStrategy;

    public GridExtractor(){
        setGridExtractStrategy(new DefaultGridExtractStrategy());
    }

    public GridExtractor(String url) throws NotFoundSudokuException {
        setGridExtractStrategy(new DefaultGridExtractStrategy());
        this.imgToSudokuGrid(url);
    }

    public GridExtractor(Mat sudoku) throws NotFoundSudokuException {
        setGridExtractStrategy(new DefaultGridExtractStrategy());
        this.matToSudokuGrid(sudoku);
    }

    public GridExtractor(GridExtractStrategy gridExtractStrategy){
        setGridExtractStrategy(gridExtractStrategy);
    }

    public GridExtractor(GridExtractStrategy gridExtractStrategy, String url) throws NotFoundSudokuException {
        setGridExtractStrategy(gridExtractStrategy);
        this.imgToSudokuGrid(url);

    }

    public GridExtractor(GridExtractStrategy gridExtractStrategy, Mat sudoku) throws NotFoundSudokuException {
        setGridExtractStrategy(gridExtractStrategy);
        this.matToSudokuGrid(sudoku);
    }


    public void matToSudokuGrid(Mat sudoku) throws NotFoundSudokuException {
        sudokuImg = gridExtractStrategy.extractGrid(sudoku);
    }

    public void imgToSudokuGrid(String url) throws NotFoundSudokuException {
        matToSudokuGrid(imread(url, 1));
    }

    public Mat getImg() {
        return sudokuImg;
    }

    public void setGridExtractStrategy(GridExtractStrategy newGridExtractStrategy){
        gridExtractStrategy = newGridExtractStrategy;
    }
}
