package domain.service;

import domain.model.RouteWeightedEdge;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;

public class GraphPathFindingService {

    public static GraphPath<Integer, RouteWeightedEdge> findSrcDestPath(DefaultDirectedWeightedGraph<Integer, RouteWeightedEdge> weightedGraph, final int srcNodeNo, final int destNodeNo) {
        DijkstraShortestPath<Integer, RouteWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(weightedGraph);
        return dijkstraShortestPath.getPath(srcNodeNo, destNodeNo);
    }

}
