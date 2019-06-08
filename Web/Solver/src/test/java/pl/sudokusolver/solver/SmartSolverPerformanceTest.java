package pl.sudokusolver.solver;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class SmartSolverPerformanceTest {

    /**
     Avg time: 31.232704402515722 ms
     */
    @Test
    @Ignore
    void performance() throws IOException {
        SmartSolver smartSolver = new SmartSolver();
        int all = 159;
        int allTime = 0;
        for(int i = 0; i < all; i++) {

            String path = "../../Data/TestImgs/" + i + ".dat";
            int[][] grid = _TestUtility_.getGridFromDat(path);

            long startTime = System.currentTimeMillis();
            smartSolver.solve(grid);
            long endTime = System.currentTimeMillis();
            long currDuration = endTime - startTime;
            allTime += currDuration;
        }
        System.out.println("Avg time: " + (double)allTime/(double)all);
    }
}