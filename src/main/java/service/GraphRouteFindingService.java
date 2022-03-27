package service;

import domain.model.RouteMetaData;
import domain.model.RoutePosition;
import domain.model.RouteWeightedEdge;
import domain.service.GraphBuildingService;
import domain.service.GraphPathFindingService;
import domain.service.NodeClusterFindingService;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import util.math.RouteFinderConstants;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class GraphRouteFindingService {

    public static void findRoute(Map<Boolean, List<RouteMetaData>> routePartitionMap) {

        // find routes from Bremerhaven to Hamburg
        log.info("find routes from Bremerhaven to Hamburg");
        GraphPath<Integer, RouteWeightedEdge> pathToHamburg = findSrcDestRoute(routePartitionMap.get(Boolean.TRUE));
        logSolutionToConsole(pathToHamburg);
        // find return routes from Hamburg to Bremerhaven
        log.info("find return routes from Hamburg to Bremerhaven");
        GraphPath<Integer, RouteWeightedEdge> pathToBremerhaven = findSrcDestRoute(routePartitionMap.get(Boolean.FALSE));
        logSolutionToConsole(pathToBremerhaven);
    }

    private static GraphPath<Integer, RouteWeightedEdge> findSrcDestRoute(List<RouteMetaData> routeMetaDataList) {
        int nodeCount = NodeClusterFindingService.addNodesToRoutePositions(routeMetaDataList);
        DefaultDirectedWeightedGraph<Integer, RouteWeightedEdge> weightedGraph = GraphBuildingService.buildGraph(routeMetaDataList, nodeCount);
        return GraphPathFindingService
                .findSrcDestPath(weightedGraph, RoutePosition.FIRST_ASSIGNED_NODE_NO - 1, nodeCount + 1);
    }

    private static void logSolutionToConsole(GraphPath<Integer, RouteWeightedEdge> path) {
        if (Objects.nonNull(path)) {
            Predicate<RouteWeightedEdge> routeWeightedEdgePositionNonNullPredicate = edge -> Objects.nonNull(edge.getStartPosition()) && Objects.nonNull(edge.getEndPosition());

            List<String> edgeStrings = path.getEdgeList().stream()
                    .filter(routeWeightedEdgePositionNonNullPredicate)
                    .flatMap(edge -> Stream.of(edge.getStartPosition().toString(), edge.getEndPosition().toString()/*, " " + edge.getSource(), " " + edge.getTarget(), " " + edge.getWeight()*/))
                    .collect(Collectors.toList());
            System.out.println(String.format(RouteFinderConstants.GEOJSON, edgeStrings));

            double pathTime = path.getEdgeList().stream()
                    .filter(routeWeightedEdgePositionNonNullPredicate)
                    .map(RouteWeightedEdge::getEdgeDurationSum)
                    .mapToDouble(x -> x)
                    .sum();
            final double millisInHour = 3600000d;
            System.out.println(String.format("Took %f hours", pathTime / millisInHour));
        } else {
            log.error("No path found");
        }
        System.out.println("____________________________________________________________________________________________________________________________________________");
    }

}
