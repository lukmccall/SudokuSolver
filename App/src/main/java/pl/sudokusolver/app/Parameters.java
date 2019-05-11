package pl.sudokusolver.app;

import javafx.scene.image.Image;

import java.io.InputStream;

public class Parameters {

    private double lineThickness;
    private double proging;
    private double distance;
    private double gauss;

    public Parameters(){
        this.lineThickness = 1.0;
        this.proging = 1.0;
        this.distance = 1.0;
        this.gauss = 1.0;
    }

    public void set(double a, double b, double c, double d){
        this.lineThickness = a;
        this.proging = b;
        this.distance = c;
        this.gauss = d;
    }

    public void set(Parameters parameters){
        this.lineThickness = parameters.getLineThickness();
        this.proging = parameters.getProging();
        this.distance = parameters.getDistance();
        this.gauss = parameters.getGauss();
    }

    public double getLineThickness(){
        return lineThickness;
    }

    public double getProging() {
        return proging;
    }

    public double getDistance() {
        return distance;
    }

    public double getGauss() {
        return gauss;
    }
}
