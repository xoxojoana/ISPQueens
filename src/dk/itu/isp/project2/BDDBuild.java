/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.itu.isp.project2;

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;

/**
 *
 * @author Jinhong
 */
public class BDDBuild {

    int size;
    BDDFactory factory;

    public BDDBuild(int size, BDDFactory factory) {
        this.size = size;
        this.factory = factory;
    }

    private int idx(int x, int y) {
        return (x * size) + y;
    }

    /**
     * use the index of each slot, together with constraints only one queen in each row
     * from different angle
     * e.g for var at position [0][0] has index 0,
     * bdd && (NOT0 v NOT1) && (NOT0 v NOT2) && (NOT0 v NOT3) && (NOT0 v NOT4)             horizontal
     *      && (NOT0 v NOT5) && (NOT0 v NOT10) && (NOT0 v NOT15) && (NOT0 v NOT20)         vertical
     *      && (NOT0 v NOT6) && (NOT0 v NOT12) && (NOT0 v NOT18) && (NOT0 v NOT24)         diagonal
     *      && one queen per row rule                                                           oneQueen
     * @return BDD
     */
    public BDD build() {
        BDD bdd = factory.one();

        for (int x = 0; x < size; x++) {
            BDD oneQueen = factory.zero();

            for (int y = 0; y < size; y++) {
                oneQueen.orWith(factory.ithVar(idx(x, y)));  
                BDD var = factory.nithVar(idx(x, y));   // get negated index value 

                //horizontal
                for (int i = (y + 1); i < size; i++) {
                    bdd.andWith(var.or(factory.nithVar(idx(x, i))));
                }
                
                //vertical
                for (int i = (x + 1); i < size; i++) {
                    bdd.andWith(var.or(factory.nithVar(idx(i, y)))); 
                }
                
                //diagonal
                for (int i = 1; (y + i) < size; i++) {
                    if ((x - i) >= 0) {
                        bdd.andWith(var.or(factory.nithVar(idx(x - i, y + i))));
                    }
                    if ((x + i) < size) {
                        bdd.andWith(var.or(factory.nithVar(idx(x + i, y + i))));
                    }
                }
            }
            bdd.andWith(oneQueen); //one row has to/only can have one queen
        }
        return bdd;
    }
}
