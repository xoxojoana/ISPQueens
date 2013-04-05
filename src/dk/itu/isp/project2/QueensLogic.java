/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.itu.isp.project2;

/**
 *
 * @author Jinhong
 */
import net.sf.javabdd.*;

public class QueensLogic {

    private int size = 5; //default value
    private int[][] board;
    private int nodes = 200000;
    private int cache = 20000;
    static BDDFactory factory;
    static BDD bdd;
    BDDBuild Builder;

    public void initializeGame(int size) {
        this.size = size;
        this.board = new int[size][size];
        
        factory = JFactory.init(nodes, cache);
        factory.setVarNum(size * size);

        //build init BDD
        Builder = new BDDBuild(size, factory);
        bdd = Builder.build();
    }

    public int[][] getGameBoard() {
        return board;
    }

    private boolean validSlot(int x, int y) {
        if (board[x][y] == 0 || board[x][y] == 1) {
            return true;
        }
        return false;
    }

    private void placeQueen(int x, int y) {
        if (validSlot(x, y)) {
            bdd = bdd.restrict(factory.ithVar(x * size + y)); //add the new bdd 
            board[x][y] = 1;
        }
    }

    private void updateBoard() {
        for (int i = 0; i < size; i++) {
            int emptySlot = -1, count = 0;
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    BDD tmp = bdd.restrict(factory.ithVar(i * size + j)); //test if slot can be used in further steps
                    if (tmp.isZero()) {
                        board[i][j] = -1;
                        bdd = bdd.restrict(factory.nithVar(i * size + j)); //negation
                    } else {
                        emptySlot = j;
                        count++;
                    }
                }
            }
            if (count == 1) {
                placeQueen(i, emptySlot); // if there is only one useful slot, place the queen directly 
            }
        }
    }

    public boolean insertQueen(int column, int row) {
        placeQueen(column, row);
        updateBoard();
        return true;
    }
}
