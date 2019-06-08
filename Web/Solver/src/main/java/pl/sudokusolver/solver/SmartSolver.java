package pl.sudokusolver.solver;

import pl.sudokusolver.solver.utility.Pair;
import pl.sudokusolver.solver.utility.Utility;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Implementation of semi-brutal sudoku solver algorithm
 */
public class SmartSolver implements ISolver {
    /**
     * shows which number can be place.
     */
    private LinkedList[][] canPlace = new LinkedList[9][9];

    /**
     * shows which place can be change and show what is on that place right now.
     */
    private LinkedList<Pair<Pair<Integer, Integer>, Iterator>> changeAble
                                                                = new LinkedList<>();
    /**
     * Supplementing helper data structures.
     * @param sudoku sudoku grid
     * @return <code>true</code> if alg found solution, otherwise return <code>false</code>.
     */
    final public boolean preSolve(final int[][] sudoku){
        for(int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++){
                // this place is completed on entry
                if(sudoku[i][j] != 0) canPlace[i][j] = null;
                else {
                    canPlace[i][j] = new LinkedList<Integer>();
                    // what we can place here
                    for(int number = 1; number <= 9; number++)
                        if(Utility.canPlaceDigit(sudoku, i,j, number))
                            canPlace[i][j].add(number);

                    // unsolved sudoku
                    if(canPlace[i][j].size() == 0) return false;
                    // maybe we can put something into grid right now
                    if(canPlace[i][j].size() == 1){
                        sudoku[i][j] = (int) canPlace[i][j].get(0);
                    }else{
                        changeAble.add(new Pair<>(new Pair<>(i, j), canPlace[i][j].iterator()));
                    }
                }
            }
        return true;
    }

    @Override
    final public boolean solve(final int[][] sudoku) {
        canPlace = new LinkedList[9][9];
        changeAble = new LinkedList<>();

        // prepare helpers
        if(!preSolve(sudoku)) return false;
        if(changeAble.size() == 0) return true;

        // try to remind back tracking algorithm
        ListIterator<Pair<Pair<Integer, Integer>, Iterator>> it = changeAble.listIterator();
        while (it.hasNext()){
            // go forward
            Pair<Pair<Integer, Integer>, Iterator> element = it.next();

            int x = element.getFirst().getFirst();
            int y = element.getFirst().getSecond();
            Iterator innerIt = element.getSecond();

            // try to put something to grid
            boolean place = false;
            while (innerIt.hasNext()){
                int nextValue = (int) innerIt.next();
                if(Utility.canPlaceDigit(sudoku, x, y, nextValue)) {
                    sudoku[x][y] = nextValue;
                    place = true;
                    break;
                }
            }
            // if we could't put digit we need to go back
            if(!place){
                sudoku[x][y] = 0;
                element.setSecond(canPlace[x][y].iterator());
                it.previous();
                if(!it.hasPrevious()) return false;
                it.previous();
            }
        }
        return true;
    }
}
