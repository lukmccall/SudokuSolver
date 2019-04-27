package pl.sudokusolver.recognizerlib;

public class _TestUtility_ {
    public static String getPathToResource(String res){
        return _TestUtility_.class.getResource(res).getPath().substring(1);
    }
}
