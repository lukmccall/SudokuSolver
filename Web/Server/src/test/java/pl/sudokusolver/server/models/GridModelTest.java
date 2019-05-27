package pl.sudokusolver.server.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridModelTest {
    int grid[][] = { { 0, 9, 0, 0, 0, 0, 8, 5, 3 },
            { 0, 0, 0, 8, 0, 0, 0, 0, 4 },
            { 0, 0, 8, 2, 0, 3, 0, 6, 9 },
            { 5, 7, 4, 0, 0, 2, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 9, 0, 0, 6, 3, 7 },
            { 9, 4, 0, 1, 0, 8, 5, 0, 0 },
            { 7, 0, 0, 0, 0, 6, 0, 0, 0 },
            { 6, 8, 2, 0, 0, 0, 0, 9, 0 } };

    @Test
    void constructorTestTrue(){
        GridModel a = new GridModel(0,grid);
        assertEquals(grid, a.sudoku);
        assertEquals(0,a.status);
    }

    @Test
    void constructorTestFalse(){
        int grid2[][] = { { 0, 9, 0, 0, 0, 0, 8, 5, 3 },
                { 0, 0, 0, 8, 0, 0, 0, 0, 4 },
                { 0, 0, 8, 2, 0, 3, 0, 6, 9 },
                { 5, 7, 4, 0, 0, 2, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 9, 1, 1, 6, 3, 7 },
                { 9, 4, 0, 1, 0, 8, 5, 0, 0 },
                { 7, 0, 0, 0, 0, 6, 0, 0, 0 },
                { 6, 8, 2, 0, 0, 0, 0, 9, 0 } };
        GridModel a = new GridModel(0,grid);
        assertNotEquals(9,a.status);
        assertNotEquals(grid2,a.sudoku);
    }


}