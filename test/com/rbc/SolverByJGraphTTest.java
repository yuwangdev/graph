package com.rbc;

import com.rbc.Utils.Printer;
import com.rbc.solver.SolverByJGraphT;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Created by yuwang on 5/26/17.
 */
public class SolverByJGraphTTest {

    private final String fileName = "res/small_g.csv";
    private final int dimension = 20;
    private SolverByJGraphT solver = new SolverByJGraphT();

    @Test
    public void testGetRawDataFromCsv() throws Exception {
        List<List<Double>> results = solver.getRawDataFromCsv("res/small_g.csv");
        Assert.assertEquals(results.size(), dimension);
        for (List<Double> result : results) {
            Assert.assertEquals(result.size(), dimension);
            for (Double r : result) {
                System.out.print(r + " ");
            }
            System.out.println("");
        }
    }

    @Test
    public void testInitializeGraph() throws Exception {
        Graph<String, DefaultWeightedEdge> graph = this.solver.initializeGraph(this.fileName);
        Set<DefaultWeightedEdge> edges = graph.edgesOf("v0");
        for (DefaultWeightedEdge defaultWeightedEdge : edges) {
            System.out.println(graph.getEdgeSource(defaultWeightedEdge));
            System.out.println(graph.getEdgeTarget(defaultWeightedEdge));
            System.out.println(graph.getEdgeWeight(defaultWeightedEdge));
        }

        edges = graph.edgesOf("v19");
        for (DefaultWeightedEdge defaultWeightedEdge : edges) {
            System.out.println(graph.getEdgeSource(defaultWeightedEdge));
            System.out.println(graph.getEdgeTarget(defaultWeightedEdge));
            System.out.println(graph.getEdgeWeight(defaultWeightedEdge));
        }

    }

    @Test
    public void testCheckPathExisted() throws Exception {
        Graph<String, DefaultWeightedEdge> graph = this.solver.initializeGraph(this.fileName);
        System.out.println(this.solver.checkPathExisted("v17", "v19"));
        System.out.println(this.solver.checkPathExisted("v18", "v19"));
        System.out.println(this.solver.checkPathExisted("v1", "v19"));
        Set<DefaultWeightedEdge> edges = graph.edgeSet();
        for (DefaultWeightedEdge defaultWeightedEdge : edges) {
            System.out.println(defaultWeightedEdge);
        }
    }

    @Test
    public void testComputeDistancePerIteraionBetweenTwoPoint() throws Exception {
        this.solver.initializeGraph(this.fileName);
        System.out.println(this.solver.computeDistancePerIteraionBetweenTwoPoint("v17", "v19"));

    }

    @Test
    public void testComputeDistanceExpectaionBetweenTwoPoint() throws Exception {

    }

    @Test
    public void testGetDistanceExpectationTable() throws Exception {
        this.solver.initializeGraph(this.fileName);
        String[][] re = this.solver.getDistanceExpectationTable();
    }


    @Test
    public void testWriteToCsvFile() throws Exception {
        long before = System.currentTimeMillis();
        this.solver.initializeGraph(this.fileName);
        Printer.writeToCsvFile("small.csv", this.solver.getDistanceExpectationTable());
        System.out.println("Time elapsed in seconds: " + (System.currentTimeMillis()-before)/1000);


    }
}