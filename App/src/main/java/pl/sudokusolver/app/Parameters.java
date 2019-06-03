package pl.sudokusolver.app;

public class Parameters {

    private int lineThreshold;
    private int lineGap;
    private int minLineSize;
    private int blurSize;
    private int blurBlockSize;
    private int blurC;
    private String scaling;
    private String recognition;

    public Parameters(){
        this.lineThreshold = 50;
        this.lineGap = 5;
        this.minLineSize = 100;
        this.blurSize = 3;
        this.blurBlockSize = 31;
        this.blurC = 15;
        this.scaling= "Fixed Width Resize";
        this.recognition = "SVM";
    }

    public void set(int lineThreshold, int lineGap, int minLineSize, int blurSize, int blurBlockSize, int blurC,
                    String scaling, String recognition){
        this.lineThreshold = lineThreshold;
        this.lineGap = lineGap;
        this.minLineSize = minLineSize;
        this.blurSize = blurSize;
        this.blurBlockSize = blurBlockSize;
        this.blurC = blurC;
        this.scaling = scaling;
        this.recognition = recognition;
    }

    public void set(Parameters parameters){
        this.lineThreshold = parameters.getLineThreshold();
        this.lineGap = parameters.getLineGap();
        this.minLineSize = parameters.getMinLineSize();
        this.blurSize = parameters.getBlurSize();
        this.blurBlockSize = parameters.getBlurBlockSize();
        this.blurC = parameters.getBlurC();
        this.scaling = parameters.getScaling();
        this.recognition = parameters.getRecognition();
    }

    private int getLineThreshold() {
        return lineThreshold;
    }

    private int getLineGap() {
        return lineGap;
    }

    private int getMinLineSize() {
        return minLineSize;
    }

    private int getBlurSize() {
        return blurSize;
    }

    private int getBlurBlockSize() {
        return blurBlockSize;
    }

    private int getBlurC() {
        return blurC;
    }

    private String getScaling() {
        return scaling;
    }

    private String getRecognition() {
        return recognition;
    }
}
