package pl.sudokusolver.recognizerlib.sudoku;

import com.google.common.collect.Lists;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;
import pl.sudokusolver.recognizerlib.exceptions.CellsExtractionFailedException;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;
import pl.sudokusolver.recognizerlib.extractors.cells.CellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.cells.LineCellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.cells.SizeCellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.ContoursDigitExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.DigitsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.FastDigitExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.grid.DefaultGridExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.grid.GridExtractStrategy;
import pl.sudokusolver.recognizerlib.filters.*;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;
import pl.sudokusolver.recognizerlib.utility.staticmethods.Utility;

import javax.print.attribute.standard.Media;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.*;

/**
 * Podstawowa implementacja <code>sudoku extractora</code>. Nie zawiera ona konkretnych strategi oraz filtórw.
 * Należy nadać je podczas inicjalizacji.<br>
 * Klasa ta posiada również <code>buildera</code>, który ułatwia tworzenie instancji oraz posiada przygotowane<br>
 * wcześniej kompozycję strategii oraz filtrów.
 */
public class BaseSudokuExtractor extends SudokuExtractor {

    public BaseSudokuExtractor(GridExtractStrategy gridExtractStrategy, CellsExtractStrategy cellsExtractStrategy,
                               DigitsExtractStrategy digitsExtractStrategy, IRecognizer recognizer){
        super(gridExtractStrategy,cellsExtractStrategy,digitsExtractStrategy,recognizer);
    }

    public BaseSudokuExtractor(GridExtractStrategy gridExtractStrategy, CellsExtractStrategy cellsExtractStrategy,
                               DigitsExtractStrategy digitsExtractStrategy, IRecognizer recognizer,
                               List<IFilter> preGridFilters, List<IFilter> preCellsFilters,
                               List<IFilter> preDigitsFilters) {
        super(gridExtractStrategy, cellsExtractStrategy, digitsExtractStrategy, recognizer,
                preGridFilters, preCellsFilters, preDigitsFilters);
    }

    /**
     * Dokładny opis {@link pl.sudokusolver.recognizerlib.sudoku.BaseSudokuExtractor#extract(Mat)}
     * @param url absolutna ścieżka do zdjęcia z sudoku
     * @return sudoku stworzone na podstawie zdjęcia
     * @throws NotFoundSudokuException gdy nie uda się znaleźć sudoku na zdjęciu
     * @throws CellsExtractionFailedException gdy nie uda się wyciąć komórek ze zdjęcia
     */
    @Override
    public Sudoku extract(String url) throws NotFoundSudokuException, CellsExtractionFailedException {
        Mat img = imread(url);
        return extract(img);
    }

    /**
     * Przebieg algorytmu extrakcji<br>
     *  <ul>
     *      <li>Aplikacja filtrów <code>preGrid</code></li>
     *      <li>Skorzystanie z strategi extrakcji siatki</li>
     *      <li>Aplikacja filtrów <code>preCells</code></li>
     *      <li>Skorzystanie z strategi extrakcji komórek</li>
     *      <li>Aplikacja filtrów <code>preDigitsFilters</code></li>
     *      <li>Skorzystanie z strategi extrakcji cyfr</li>
     *      <li>Skorzystanie z <code>Recognizera</code></li>
     *  </ul>
     * @param img macierz z zdjęciem
     * @return sudoku stworzone na podstawie zdjęcia
     * @throws NotFoundSudokuException gdy nie uda się znaleźć sudoku na zdjęciu
     * @throws CellsExtractionFailedException gdy nie uda się wyciąć komórek ze zdjęcia
     */
    @Override
    public Sudoku extract(Mat img) throws NotFoundSudokuException, CellsExtractionFailedException {

      //  new DisplayHelper().apply(img);
      //  Utility.applyFilters(img, preGridFilters);
       // new DisplayHelper().apply(img);

        double ratio = img.size().height/img.size().width;
        resize(img,img, new Size(1000f,1000f*ratio));
      //  new DisplayHelper().apply(img);


        /*=============================KOD */
      //  Mat test = img.clone();
    //    new ToGrayFilter().apply(test);

      //  Photo.fastNlMeansDenoising(test,test,16,5,10);
      //  adaptiveThreshold(test, test, 255, ADAPTIVE_THRESH_GAUSSIAN_C, THRESH_BINARY_INV, 15, 2);
        /*
        //GaussianBlur(test,test, new Size(3,3),0);



       // medianBlur(test,test, 3);
       // new BlurFilter(3,21,2).apply(test);



        int width = 1;
        int height = 1;
        Mat kernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(2 * width + 1, 2 * height + 1), new Point(width,height));

        Imgproc.dilate(test,test,kernel);


        List<MatOfPoint> cont = Lists.newArrayList();
        findContours(test, cont, new Mat(), RETR_CCOMP, CHAIN_APPROX_SIMPLE);
        MatOfPoint max = cont.get(0);

        for (MatOfPoint p : cont) {
          if(contourArea(p)>contourArea(max))
          {
              max = p;
          }
        }
*/

      //  new DisplayHelper().apply(test);


        /*===============================KOD */
        Mat sudokuGrid = gridExtractStrategy.extractGrid(img);

  //      new DisplayHelper().apply(sudokuGrid);
        resize(sudokuGrid,sudokuGrid,new Size(600f,600f));
     //   new DisplayHelper().apply(sudokuGrid);
        Utility.applyFilters(sudokuGrid, preCellsFilters);
    //    new DisplayHelper().apply(sudokuGrid);
        List<Mat> cells = cellsExtractStrategy.extract(sudokuGrid);
     //  new DisplayHelper().apply(sudokuGrid);
//      new DisplayHelper().apply(sudokuGrid);


        //todo: make exception
        if(cells == null) throw new CellsExtractionFailedException();

        Sudoku sudoku = new Sudoku();
        for(int i = 0; i < cells.size(); i++){
            Mat cell = cells.get(i);
           resize(cell,cell,new Size(50f,50f)); // ZOSTAW MI TO!
            Utility.applyFilters(cell, preDigitsFilters);

            Optional<Mat> digit = digitsExtractStrategy.extractDigit(cell);

            if (digit.isPresent())
            {

                sudoku.setDigit(recognizer.recognize(digit.get()).getFirst(), i / 9, i % 9);
             //System.out.println(sudoku.getDigit(i / 9, i % 9)+" "+cell.size().height+" "+cell.size().width);
            //  new DisplayHelper().apply(digit.get());

            }


        }
        return sudoku;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder simpleBuilderRecipe(IRecognizer recognizer){
        return builder().setGridStrategy(new DefaultGridExtractStrategy())
                .setCellsStrategy(new SizeCellsExtractStrategy())
                .setDigitsStrategy(new FastDigitExtractStrategy())
                .setRecognizer(recognizer)
                .addPreCellsFilters(new CleanLinesFilter());
    }

    public static Builder simpleBuilderRecipe(IRecognizer recognizer, BlurFilter blurFilter, CleanLinesFilter cleanLinesFilter){
        return builder().setGridStrategy(new DefaultGridExtractStrategy(blurFilter))
                .setCellsStrategy(new SizeCellsExtractStrategy())
                .setDigitsStrategy(new FastDigitExtractStrategy())
                .setRecognizer(recognizer)
                .addPreCellsFilters(cleanLinesFilter);
    }

    public static Builder complexBuilderRecipe(IRecognizer recognizer){
        return builder().setGridStrategy(new DefaultGridExtractStrategy())
                .setCellsStrategy(new LineCellsExtractStrategy())
                .setDigitsStrategy(new ContoursDigitExtractStrategy())
                .setRecognizer(recognizer)
                .addPreCellsFilters(new BlurFilter());
    }

    /**
     * Builder klasy <code>BaseSudokuExtractor</code>.
     */
    public static final class Builder {
        private GridExtractStrategy gridExtractStrategy;
        private CellsExtractStrategy cellsExtractStrategy;
        private DigitsExtractStrategy digitsExtractStrategy;
        private IRecognizer recognizer;

        private List<IFilter> preGridFilters;
        private List<IFilter> preCellsFilters;
        private List<IFilter> preDigitsFilters;

        public SudokuExtractor build() throws IllegalStateException {
            if(gridExtractStrategy == null){
                throw new IllegalStateException("GridExtractStrategy cannot be null");
            }
            if(cellsExtractStrategy == null){
                throw new IllegalStateException("CellsExtractStrategy cannot be null");
            }
            if(digitsExtractStrategy == null){
                throw new IllegalStateException("DigitsExtractStrategy cannot be null");
            }
            if(recognizer == null){
                throw new IllegalStateException("Recognizer cannot be null");
            }

            return new BaseSudokuExtractor(gridExtractStrategy, cellsExtractStrategy, digitsExtractStrategy,
                    recognizer, preGridFilters, preCellsFilters, preDigitsFilters);
        }

        public Builder setGridStrategy(GridExtractStrategy gridExtractStrategy) {
            this.gridExtractStrategy = gridExtractStrategy;
            return this;
        }

        public Builder setCellsStrategy(CellsExtractStrategy cellsExtractStrategy) {
            this.cellsExtractStrategy = cellsExtractStrategy;
            return this;
        }

        public Builder setDigitsStrategy(DigitsExtractStrategy digitsExtractStrategy) {
            this.digitsExtractStrategy = digitsExtractStrategy;
            return this;
        }

        public Builder setRecognizer(IRecognizer recognizer) {
            this.recognizer = recognizer;
            return this;
        }

        public Builder setPreGridFilters(List<IFilter> preGridFilters) {
            this.preGridFilters = preGridFilters;
            return this;
        }

        public Builder addPreGridFilters(IFilter filter){
            if(preGridFilters == null){
                preGridFilters = new LinkedList<>();
                preGridFilters.add(filter);
            }
            else
                preGridFilters.add(filter);
            return this;
        }

        public Builder setPreCellsFilters(List<IFilter> preCellsFilters) {
            this.preCellsFilters = preCellsFilters;
            return this;
        }

        public Builder addPreCellsFilters(IFilter filter){
            if(preCellsFilters == null){
                preCellsFilters = new LinkedList<>();
                preCellsFilters.add(filter);
            }
            else
                preCellsFilters.add(filter);
            return this;
        }

        public Builder setPreDigitsFilters(List<IFilter> preDigitsFilters) {
            this.preDigitsFilters = preDigitsFilters;
            return this;
        }

        public Builder addPreDigitsFilters(IFilter filter){
            if(preDigitsFilters == null){
                preDigitsFilters = new LinkedList<>();
                preDigitsFilters.add(filter);
            }
            else
                preDigitsFilters.add(filter);
            return this;
        }
    }
}
