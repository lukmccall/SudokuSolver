package pl.sudokusolver.app;

/**
 * Class that stores all parameters that are chosen by user used to process the image
 */
public class Parameters {

    private int lineThreshold;
    private int lineGap;
    private int minLineSize;
    private int blurSize;
    private int blurBlockSize;
    private int blurC;
    private String scaling;
    private String recognition;
    private boolean strictMode;

    public Parameters(){
        this.lineThreshold = 50;
        this.lineGap = 5;
        this.minLineSize = 100;
        this.blurSize = 3;
        this.blurBlockSize = 31;
        this.blurC = 15;
        this.scaling= "Fixed Width Resize";
        this.recognition = "SVM";
        this.strictMode = true;
    }

    /**
     * Function to set values
     * @param lineThreshold line threshold value
     * @param lineGap line gap value
     * @param minLineSize minimum line size value
     * @param blurSize blur size value
     * @param blurBlockSize blur block size value
     * @param blurC blur C value
     * @param scaling scaling option
     * @param recognition recognition option
     * @param strictMode strict mode
     */
    public void set(int lineThreshold, int lineGap, int minLineSize, int blurSize, int blurBlockSize, int blurC,
                    String scaling, String recognition, boolean strictMode){
        this.lineThreshold = lineThreshold;
        this.lineGap = lineGap;
        this.minLineSize = minLineSize;
        this.blurSize = blurSize;
        this.blurBlockSize = blurBlockSize;
        this.blurC = blurC;
        this.scaling = scaling;
        this.recognition = recognition;
        this.strictMode = strictMode;
    }

    /**
     * Function to set values
     * @param parameters new parameters
     */
    public void set(Parameters parameters){
        this.lineThreshold = parameters.getLineThreshold();
        this.lineGap = parameters.getLineGap();
        this.minLineSize = parameters.getMinLineSize();
        this.blurSize = parameters.getBlurSize();
        this.blurBlockSize = parameters.getBlurBlockSize();
        this.blurC = parameters.getBlurC();
        this.scaling = parameters.getScaling();
        this.recognition = parameters.getRecognition();
        this.strictMode = parameters.getStrictMode();
    }

    /**
     * Function to return line threshold
     * @return line threshold
     */
    private int getLineThreshold() {
        return lineThreshold;
    }

    /**
     * Function to return line gap
     * @return line gap
     */
    private int getLineGap() {
        return lineGap;
    }

    /**
     * Function to return minimal line size
     * @return minimal line size
     */
    private int getMinLineSize() {
        return minLineSize;
    }

    /**
     * Function to return blur size
     * @return blur size
     */
    private int getBlurSize() {
        return blurSize;
    }

    /**
     * Funtion to return blur block size
     * @return blur block size
     */
    private int getBlurBlockSize() {
        return blurBlockSize;
    }

    /**
     * Function to return blur c
     * @return blur c
     */
    private int getBlurC() {
        return blurC;
    }

    /**
     * Function to return scaling
     * @return scaling
     */
    private String getScaling() {
        return scaling;
    }

    /**
     * Function to return recognition
     * @return recognition
     */
    public String getRecognition() {
        return recognition;
    }

    /**
     * Function to return strict mode
     * @return strict mode
     */
    public boolean getStrictMode() {
        return strictMode;
    }
}
