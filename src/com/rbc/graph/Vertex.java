package com.rbc.graph;


import java.util.Map;

/**
 * Created by yuwang on 5/23/17.
 */

public class Vertex {
    private String vertexId;
    private Map<String, Double> neigborsWithWeight;
    private Map<String, Double> neigborsWithProb;
    private Boolean isVisited;

    public Vertex() {
    }

    public Boolean getVisited() {
        return isVisited;
    }

    public void setVisited(Boolean visited) {
        isVisited = visited;
    }

    public String getVertexId() {
        return vertexId;
    }

    public void setVertexId(String vertexId) {
        this.vertexId = vertexId;
    }

    public Map<String, Double> getNeigborsWithProb() {
        return neigborsWithProb;
    }

    public void setNeigborsWithProb(Map<String, Double> neigborsWithProb) {
        this.neigborsWithProb = neigborsWithProb;
    }

    public Map<String, Double> getNeigborsWithWeight() {
        return neigborsWithWeight;
    }

    public void setNeigborsWithWeight(Map<String, Double> neigborsWithWeight) {
        this.neigborsWithWeight = neigborsWithWeight;
    }


}
