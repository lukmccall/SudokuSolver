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
        Init.init("C:\\opencv4.0.1\\opencv\\build\\java\\x64");
    }
}
