package pl.sudokusolver.solver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class _TestUtility_ {
    public static int[][] getGridFromDat(String url) throws IOException {
        int[][] grid = new int[9][9];
        try (BufferedReader br = new BufferedReader(new FileReader(url))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '.') {
                        grid[index / 9][ index % 9] = 0;
                        index++;
                    }
                    if ('0' <= line.charAt(i) && line.charAt(i) <= '9') {
                        grid[index / 9][ index % 9] = Character.getNumericValue(line.charAt(i));
                        index++;
                    }
                }
            }
        }
        return grid;
    }
}
