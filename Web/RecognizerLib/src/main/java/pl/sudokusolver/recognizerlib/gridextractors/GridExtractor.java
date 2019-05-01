package pl.sudokusolver.recognizerlib.gridextractors;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuExceptions;

import static org.opencv.imgcodecs.Imgcodecs.imread;

// Strategy
public class GridExtractor {
    private Mat sudokuImg;
    private ExtractStrategy extractStrategy;

    public GridExtractor(){
        setExtractStrategy(new DefaultExtractStrategy());
    }

    public GridExtractor(String url) throws NotFoundSudokuExceptions {
        setExtractStrategy(new DefaultExtractStrategy());
        this.imgToSudokuGrid(url);
    }

    public GridExtractor(Mat sudoku) throws NotFoundSudokuExceptions {
        setExtractStrategy(new DefaultExtractStrategy());
        this.matToSudokuGrid(sudoku);
    }

    public GridExtractor(ExtractStrategy extractStrategy){
        setExtractStrategy(extractStrategy);
    }

    public GridExtractor(ExtractStrategy extractStrategy, String url) throws NotFoundSudokuExceptions {
        setExtractStrategy(extractStrategy);
        this.imgToSudokuGrid(url);

    }

    public GridExtractor(ExtractStrategy extractStrategy, Mat sudoku) throws NotFoundSudokuExceptions {
        setExtractStrategy(extractStrategy);
        this.matToSudokuGrid(sudoku);
    }


    public void matToSudokuGrid(Mat sudoku) throws NotFoundSudokuExceptions {
        sudokuImg = extractStrategy.matToSudokuGrid(sudoku);
    }

    public void imgToSudokuGrid(String url) throws NotFoundSudokuExceptions {
        matToSudokuGrid(imread(url, 1));
    }

    public Mat getImg() {
        return sudokuImg;
    }

    public void setExtractStrategy(ExtractStrategy newExtractStrategy){
        extractStrategy = newExtractStrategy;
    }
}
