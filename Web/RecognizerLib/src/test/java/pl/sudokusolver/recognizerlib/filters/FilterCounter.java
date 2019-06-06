package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;

// created only for UtilityTest -- applyFilterCounterTest

public class FilterCounter implements IFilter{
    static int counter = 0;

    public int getCounter() {
        return counter;
    }

    @Override
    public void apply(Mat input) {
        counter++;
    }
}
