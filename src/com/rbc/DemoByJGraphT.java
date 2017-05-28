package com.rbc;


import com.rbc.Utils.Printer;
import com.rbc.solver.SolverByJGraphT;

/**
 * Created by yuwang on 5/23/17.
 */
public class DemoByJGraphT {
    public static void main(String[] argus) {
        SolverByJGraphT solverByJGraphT = new SolverByJGraphT();
        long before = System.currentTimeMillis();
        solverByJGraphT.initializeGraph("res/large_g.csv");
        Printer.writeToCsvFile("large.csv", solverByJGraphT.getDistanceExpectationTable());
        System.out.println("Time elapsed in seconds for small case: " + (System.currentTimeMillis() - before) / 1000);

    }


}