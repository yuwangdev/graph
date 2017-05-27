package com.rbc;

import com.rbc.Utils.Printer;
import com.rbc.graph.Vertex;
import com.rbc.solver.SolverFromScratch;

import java.util.Map;


//
///**
// * Created by yuwang on 5/23/17.
// */
public class DemoFromScratch {
    public static void main(String[] argus) {
        long before = System.currentTimeMillis();
        SolverFromScratch solverFromScratch = new SolverFromScratch();
        Map<String, Vertex> graph = solverFromScratch.initializeGraph("res/large_g.csv");
        Printer.writeToCsvFile("large_m.csv", solverFromScratch.getDistanceExpectationTable(graph));
        System.out.println("Time elapsed in seconds for large case: " + (System.currentTimeMillis() - before) / 1000);

    }
}
