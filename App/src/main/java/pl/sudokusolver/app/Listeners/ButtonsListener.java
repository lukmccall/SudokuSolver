package pl.sudokusolver.app.Listeners;

public interface ButtonsListener {
    void send(int lineThreshold, int lineGap, int minLineSize, int blurSize, int blurBlockSize, int blurC,
              String scaling, String recognition);
    void exit();
}
