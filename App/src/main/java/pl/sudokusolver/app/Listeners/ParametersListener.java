package pl.sudokusolver.app.Listeners;

public interface ParametersListener {
    void parameters(int lineThreshold, int lineGap, int minLineSize, int blurSize, int blurBlockSize, int blurC,
                    String scaling, String recognition);
}
