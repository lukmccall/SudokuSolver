package pl.sudokusolver.server.bean;

import pl.sudokusolver.recognizerlib.Init;

import javax.annotation.PostConstruct;

public class Recognizer {
    private String openCVUrl;

    public Recognizer(String openCVUrl){
        this.openCVUrl = openCVUrl;
    }

    @PostConstruct
    public void init(){
        System.out.println("Loading openCV from " + this.openCVUrl);
        Init.init(this.openCVUrl);
    }
}
