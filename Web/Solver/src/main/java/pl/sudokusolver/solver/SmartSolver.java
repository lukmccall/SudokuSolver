package pl.sudokusolver.solver;

import pl.sudokusolver.solver.utility.Pair;
import pl.sudokusolver.solver.utility.Utility;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class SmartSolver implements ISolver {
    /**
     * Wyznacza możliwe wartości pól.
     */
    private LinkedList[][] canPlace = new LinkedList[9][9];
    /**
     * Wyznacza pola do wypełnienia oraz iteratory do poszczególnych list w <code>canPlace</code>.
     */
    private LinkedList<Pair<Pair<Integer, Integer>, Iterator>> changeAble
                                                                = new LinkedList<>();
    /**
     * @param sudoku - grid wejściowy
     * @return <code>true</code> gdy da się rozwiązać sudoku, <code>false</code> w przeciwnym wypadku
     */
    final public boolean preSolve(final int[][] sudoku){
        for(int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++){
                if(sudoku[i][j] != 0) canPlace[i][j] = null;
                else {
                    canPlace[i][j] = new LinkedList<Integer>();

                    for(int number = 1; number <= 9; number++)
                        if(Utility.canPlaceDigit(sudoku, i,j, number))
                            canPlace[i][j].add(number);

                    if(canPlace[i][j].size() == 0) return false;
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

        if(!preSolve(sudoku)) return false;
        if(changeAble.size() == 0) return true;

        ListIterator<Pair<Pair<Integer, Integer>, Iterator>> it = changeAble.listIterator();
        while (it.hasNext()){
            Pair<Pair<Integer, Integer>, Iterator> element = it.next();

            int x = element.getFirst().getFirst();
            int y = element.getFirst().getSecond();
            Iterator innerIt = element.getSecond();

            boolean place = false;
            while (innerIt.hasNext()){
                int nextValue = (int) innerIt.next();
                if(Utility.canPlaceDigit(sudoku, x, y, nextValue)) {
                    sudoku[x][y] = nextValue;
                    place = true;
                    break;
                }
            }
            if(!place){
                sudoku[x][y] = 0;
                if (canPlace[x][y] == null){System.out.println("cos"); System.out.println(x+" "+y); };
                element.setSecond(canPlace[x][y].iterator());
                it.previous();
                if(!it.hasPrevious()) return false;
                it.previous();
            }
        }

        return true;
    }
}
