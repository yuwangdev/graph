package com.rbc.solver;


import com.google.common.collect.Maps;
import com.rbc.Utils.RandomNumberGenerator;
import com.rbc.graph.Vertex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by yuwang on 5/23/17.
 */
public class SolverFromScratch {
    private final String vertexPrefix = "v";
    private Map<String, Boolean> connectionCache;

    public Map<String, Vertex> initializeGraph(String filePath) {
        Map<String, Vertex> graph = new HashMap<>();
        List<List<Double>> rawData = new ArrayList<>();

        try {
            rawData = getRawDataFromCsv(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < rawData.size(); i++) {
            Vertex vertex = new Vertex();
            vertex.setVertexId(vertexPrefix + i);
            graph.put(vertexPrefix + i, vertex);
        }


        for (int i = 0; i < rawData.size(); i++) {

            List<Double> row = rawData.get(i);
            Vertex vertex = graph.get(vertexPrefix + i);
            Map<String, Double> neigbors = new HashMap<>();

            for (int j = 0; j < row.size(); j++) {
                if (row.get(j) > 0.0) {
                    neigbors.put(vertexPrefix + j, row.get(j));

                }

            }
            vertex.setNeigborsWithWeight(neigbors);

        }

        for (String a : graph.keySet()) {
            Vertex v = graph.get(a);
            double total = 0.0;
            Map<String, Double> neigbors = v.getNeigborsWithWeight();
            for (String b : neigbors.keySet()) {
                total += neigbors.get(b);
            }
            Map<String, Double> neigborsWithProb = new HashMap<>();

            for (String b : neigbors.keySet()) {
                neigborsWithProb.put(b, neigbors.get(b) / total);
            }
            v.setNeigborsWithProb(neigborsWithProb);


        }
        this.connectionCache = prepareConnectionCache(graph);

        return graph;
    }

    public Map<String, Boolean> prepareConnectionCache(Map<String, Vertex> graph) {
        Map<String, Boolean> map = Maps.newHashMap();
        for (int i = 0; i < graph.size(); i++) {
            for (int j = 0; j < graph.size(); j++) {
                map.put(vertexPrefix + i + "-" + vertexPrefix + j, checkPathExisted(vertexPrefix + i, vertexPrefix + j, graph));
            }
        }
        return map;
    }


    public List<List<Double>> getRawDataFromCsv(String filePath) throws IOException {
        List<List<Double>> result = new ArrayList<>();

        BufferedReader csv = new BufferedReader(new FileReader(filePath));

        String data;
        while ((data = csv.readLine()) != null) {
            List<Double> temp = new ArrayList<>();
            String[] row = data.split(",");

            for (String a : row) {
                temp.add(Double.parseDouble(a));
            }
            result.add(temp);
        }

//        for (int i = 0; i < result.size(); i++) {
//            List<Double> temp5 = result.get(i);
//            System.out.println(temp5);
//
//
//        }


        return result;

    }


    public int computeDistancePerIteraionBetweenTwoPoint(String t1, String t2, Map<String, Vertex> graph) {

        if (t1 == t2) {
            return 0;
        }

        if (!this.connectionCache.get(t1 + "-" + t2)) {
            return -1;
        }

        int distance = 0;

        String current = t1;
        while (!current.equalsIgnoreCase(t2)) {
            Map<String, Double> neigborsWithProb = graph.get(current).getNeigborsWithProb();
            current = jumpToNextPoint(neigborsWithProb);
            distance++;

            if (distance == 10000) {
                break;
            }
        }


        return distance;
    }

    public boolean checkPathExisted(String t1, String t2, Map<String, Vertex> graph) {
        if (t1.equalsIgnoreCase(t2)) {
            return true;
        }

        for (String k : graph.keySet()) {
            Vertex vertex = graph.get(k);
            vertex.setVisited(false);
        }

        Queue<String> queue = new LinkedList<>();
        queue.add(t1);
        graph.get(t1).setVisited(true);
        while (!queue.isEmpty()) {
            String currentString = queue.poll();
            if (currentString.equalsIgnoreCase(t2)) {
                return true;
            }
            Vertex current = graph.get(currentString);
            current.setVisited(true);
            Map<String, Double> neigbor = current.getNeigborsWithProb();
            for (String nk : neigbor.keySet()) {
                if (nk.equalsIgnoreCase(t2)) {
                    return true;
                } else {
                    if (!graph.get(nk).getVisited()) {
                        queue.add(nk);
                    }

                }
            }

        }


        return false;
    }

    public String jumpToNextPoint(Map<String, Double> neigborsWithProb) {
        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
        for (String t : neigborsWithProb.keySet()) {
            randomNumberGenerator.addNumber(t, neigborsWithProb.get(t));
        }

        return randomNumberGenerator.getDistributedRandomNumber();

    }

    public double computeDistanceExpectaionBetweenTwoPoint(String t1, String t2, Map<String, Vertex> graph) {

        final int iteration = 1000;
        int epoch = 0;
        double sum = 0.0;
        while (epoch < 1000) {
            sum += computeDistancePerIteraionBetweenTwoPoint(t1, t2, graph);
            epoch++;
        }
        System.out.println("computeDistanceExpectaionBetweenTwoPoint: " + t1 + "-" + t2 + "= " + sum / iteration);
        return sum > 0 ? sum / iteration : 0.0;
    }


    public String[][] getDistanceExpectationTable(Map<String, Vertex> graph) {

        int dimension = graph.size();
        System.out.println("dimension of graph: " + dimension);
        Double[][] resultTable = new Double[dimension][dimension];
        String[][] resultTableString = new String[dimension][dimension];

        for (int i = 0; i < resultTable.length; i++) {
            resultTable[i][i] = 0.0;
        }

        for (int i = 0; i < resultTable.length; i++) {
            for (int j = 0; j < resultTable.length; j++) {
                resultTable[i][j] = computeDistanceExpectaionBetweenTwoPoint(vertexPrefix + i, vertexPrefix + j, graph);
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
