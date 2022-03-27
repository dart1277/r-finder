package domain.service;

import domain.model.RouteMetaData;
import domain.model.RoutePosition;
import domain.model.RouteWeightedEdge;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GraphBuildingService {

    public static DefaultDirectedWeightedGraph<Integer, RouteWeightedEdge> buildGraph(List<RouteMetaData> routeMetaDataList, final int nodeCount) {
        sortRoutePositionLists(routeMetaDataList);
        DefaultDirectedWeightedGraph<Integer, RouteWeightedEdge> weightedGraph = new DefaultDirectedWeightedGraph<>(RouteWeightedEdge.class);

        addVertices(weightedGraph, nodeCount);
        addEdges(routeMetaDataList, weightedGraph);
        addSrcAndDestNodes(routeMetaDataList, nodeCount, weightedGraph);
        return weightedGraph;
    }

    private static void addSrcAndDestNodes(List<RouteMetaData> routeMetaDataList, int nodeCount, DefaultDirectedWeightedGraph<Integer, RouteWeightedEdge> weightedGraph) {
        final int startNodeNo = RoutePosition.FIRST_ASSIGNED_NODE_NO - 1;
        final int endNodeNo = nodeCount + 1;
        weightedGraph.addVertex(startNodeNo);
        weightedGraph.addVertex(endNodeNo);
        Set<Pair<Integer, Integer>> srcDest = findSourceAndDestinationNodes(routeMetaDataList);
        srcDest.forEach(srcDestPair -> {
            RouteWeightedEdge edge = weightedGraph.addEdge(startNodeNo, srcDestPair.getFirst());
            if (Objects.nonNull(edge)) {
                weightedGraph.setEdgeWeight(edge, 0d);
            }
            edge = weightedGraph.addEdge(srcDestPair.getSecond(), endNodeNo);
            if (Objects.nonNull(edge)) {
                weightedGraph.setEdgeWeight(edge, 0d);
            }
        });
    }

    private static void addEdges(List<RouteMetaData> routeMetaDataList, DefaultDirectedWeightedGraph<Integer, RouteWeightedEdge> weightedGraph) {
        routeMetaDataList.stream().map(RouteMetaData::getPositions).forEach(routePositions -> {
            final int positionCount = routePositions.size();
            if (positionCount < 2) {
                return;
            }
            addEdges(routePositions, weightedGraph, positionCount);
        });
    }

    private static void addEdges(List<RoutePosition> routePositions, DefaultDirectedWeightedGraph<Integer, RouteWeightedEdge> weightedGraph, int positionCount) {
        for (int positionNo = 1; positionNo < positionCount; ++positionNo) {
            RoutePosition startPos = routePositions.get(positionNo - 1);
            RoutePosition endPos = routePositions.get(positionNo);
            int startVertex = startPos.getRouteNodeNo();
            int endVertex = endPos.getRouteNodeNo();
            double weight;
            RouteWeightedEdge edge = weightedGraph.getEdge(startVertex, endVertex);
            if (Objects.isNull(edge)) {
                RouteWeightedEdge newEdge = weightedGraph.addEdge(startVertex, endVertex);
                weight = newEdge.computeInitialWeight(startPos, endPos);
            } else {
                weight = edge.computeWeight(startPos, endPos);
            }
            weightedGraph.setEdgeWeight(startVertex, endVertex, weight);
        }
    }

    private static void addVertices(DefaultDirectedWeightedGraph<Integer, RouteWeightedEdge> weightedGraph, int nodeCount) {
        for (int vertexNo = 1; vertexNo <= nodeCount; ++vertexNo) {
            weightedGraph.addVertex(vertexNo);
        }
    }

    private static Set<Pair<Integer, Integer>> findSourceAndDestinationNodes(List<RouteMetaData> routeMetaDataList) {
        return routeMetaDataList.stream().map(RouteMetaData::getPositions).filter(Predicate.not(List::isEmpty))
                .map(positions -> new Pair<>(positions.get(0).getRouteNodeNo(), positions.get(positions.size() - 1).getRouteNodeNo())).collect(Collectors.toSet());
    }

    private static void sortRoutePositionLists(List<RouteMetaData> routeMetaDataList) {
        routeMetaDataList.forEach(routeMetaData -> routeMetaData.getPositions()
                .sort(Comparator.comparing(
                        RoutePosition::getTimestamp)));
    }

}
