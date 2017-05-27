package com.rbc.solver;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import org.jgrapht.traverse.RandomWalkIterator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by yuwang on 5/26/17.
 */
public class SolverByJGraphT {

    private final String VERTEX_PREFIX = "v";
    private Graph<String, DefaultWeightedEdge> graph;
    private int dimension;
    private Map<String, Boolean> connectionCache;
    private final int MAX_RANDOM_WALK_STEP = 1000;
    private final int ITERATION = 1000;


    public List<List<Double>> getRawDataFromCsv(String filePath) throws IOException {
        List<List<Double>> result = Lists.newArrayList();
        BufferedReader csv = new BufferedReader(new FileReader(filePath));
        String data;
        while ((data = csv.readLine()) != null) {
            List<Double> temp = Lists.newArrayList();
            String[] row = data.split(",");
            for (String a : row) {
                temp.add(Double.parseDouble(a));
            }
            result.add(temp);
        }
        this.dimension = result.size();
        return result;
    }


    public Graph<String, DefaultWeightedEdge> initializeGraph(String filePath) {
        ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> listenableUndirectedWeightedGraph = new ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        List<List<Double>> rawData = Lists.newArrayList();

        try {
            rawData = getRawDataFromCsv(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < rawData.size(); i++) {
            listenableUndirectedWeightedGraph.addVertex(VERTEX_PREFIX + i);
        }

        for (int i = 0; i < rawData.size(); i++) {
            List<Double> row = rawData.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (row.get(j) > 0.0) {
                    listenableUndirectedWeightedGraph.addEdge(VERTEX_PREFIX + i, VERTEX_PREFIX + j);
                    listenableUndirectedWeightedGraph.setEdgeWeight(listenableUndirectedWeightedGraph.getEdge(VERTEX_PREFIX + i, VERTEX_PREFIX + j), row.get(j));
                }
            }
        }
        this.graph = listenableUndirectedWeightedGraph;
        this.connectionCache = prepareConnectionCache();

        return listenableUndirectedWeightedGraph;
    }

    public Map<String, Boolean> prepareConnectionCache() {
        Map<String, Boolean> map = Maps.newHashMap();
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                map.put(VERTEX_PREFIX + i + "-" + VERTEX_PREFIX + j, checkPathExisted(VERTEX_PREFIX + i, VERTEX_PREFIX + j));
            }
        }
        return map;
    }

    public boolean checkPathExisted(String t1, String t2) {
        if (t1.equalsIgnoreCase(t2)) {
            return true;
        }
        ConnectivityInspector<String, DefaultWeightedEdge> connectivityInspector = new ConnectivityInspector<String, DefaultWeightedEdge>((UndirectedGraph) this.graph);
        return connectivityInspector.pathExists(t1, t2);
    }

    public int computeDistancePerIteraionBetweenTwoPoint(String t1, String t2) {

        if (t1.equals(t2)) {
            return 0;
        }

        if (!this.connectionCache.get(t1 + "-" + t2)) {
            System.out.println("There is no path between: " + t1 + " and " + t2);
            return -1;
        }

        int distance = 0;

        RandomWalkIterator<String, DefaultWeightedEdge> randomWalkIterator = new RandomWalkIterator<String, DefaultWeightedEdge>((Graph) this.graph, t1, true, MAX_RANDOM_WALK_STEP);

        while (randomWalkIterator.hasNext()) {
            String currentVertex = randomWalkIterator.next();
            distance++;
            if (currentVertex.equals(t2)) {
                break;
            }
            if (!currentVertex.equals(t2) && distance == 1000) {
                break;
            }
        }

        return distance;
    }


    public double computeDistanceExpectaionBetweenTwoPoint(String t1, String t2) {

        int epoch = 0;
        double sum = 0.0;
        while (epoch < 1000) {
            sum += computeDistancePerIteraionBetweenTwoPoint(t1, t2);
            epoch++;
        }
        //System.out.println("Distance expectaion between: " + t1 + " and " + t2 + " = " + sum / ITERATION);
        return sum >= 0.0 ? sum / ITERATION : -1.0;
    }


    public String[][] getDistanceExpectationTable() {
        System.out.println("dimension of graph: " + this.dimension);
        Double[][] resultTable = new Double[this.dimension][this.dimension];
        String[][] resultTableString = new String[this.dimension][this.dimension];

        for (int i = 0; i < resultTable.length; i++) {
            resultTable[i][i] = 0.0;
        }

        for (int i = 0; i < resultTable.length; i++) {
            for (int j = 0; j < resultTable.length; j++) {
                resultTable[i][j] = computeDistanceExpectaionBetweenTwoPoint(VERTEX_PREFIX + i, VERTEX_PREFIX + j);
            }
        }

        for (int i = 0; i < resultTable.length; i++) {
            for (int j = 0; j < resultTable.length; j++) {
                resultTableString[i][j] = String.valueOf(resultTable[i][j]);
            }
        }

        return resultTableString;
    }


}
