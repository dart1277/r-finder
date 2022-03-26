package service;

import domain.model.PortCode;
import domain.model.RouteMetaData;
import domain.model.RoutePosition;
import domain.model.RouteWeightedEdge;
import domain.service.GraphBuildingService;
import domain.service.GraphPathFindingService;
import domain.service.NodeClusterFindingService;
import dto.CsvRouteDto;
import dto.RouteMapper;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import util.csv.CsvReaderUtil;
import util.math.RouteFinderConstants;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class GraphRouteFindingService {

    public static void findRoute(String fileName, double positionAccuracy) {
        List<CsvRouteDto> routeDtos = CsvReaderUtil.readFile(fileName, CsvRouteDto.class);
        List<RouteMetaData> routeMetaDataList = RouteMapper.INSTANCE.toDomainWithAccuracy(routeDtos, positionAccuracy);
        Map<Boolean, List<RouteMetaData>> routePositionsFromBremerhavenToHamburgPartitionMap = routeMetaDataList.stream()
                .collect(Collectors.partitioningBy(routeMetaData -> routeMetaData.getFromPort() == PortCode.DEBRV));

        // find routes from Bremerhaven to Hamburg
        log.info("find routes from Bremerhaven to Hamburg");
        findSrcDestRoute(routePositionsFromBremerhavenToHamburgPartitionMap.get(Boolean.TRUE));
        // find return routes from Hamburg to Bremerhaven
        log.info("find return routes from Hamburg to Bremerhaven");
        findSrcDestRoute(routePositionsFromBremerhavenToHamburgPartitionMap.get(Boolean.FALSE));

    }

    private static void findSrcDestRoute(List<RouteMetaData> routeMetaDataList) {
        int nodeCount = NodeClusterFindingService.addNodesToRoutePositions(routeMetaDataList);
        DefaultDirectedWeightedGraph<Integer, RouteWeightedEdge> weightedGraph = GraphBuildingService.buildGraph(routeMetaDataList, nodeCount);
        GraphPath<Integer, RouteWeightedEdge> path = GraphPathFindingService
                .findSrcDestPath(weightedGraph, RoutePosition.FIRST_ASSIGNED_NODE_NO - 1, nodeCount + 1);

        List<String> edgeStrings = path.getEdgeList().stream()
                .filter(edge -> Objects.nonNull(edge.getStartPosition()) && Objects.nonNull(edge.getEndPosition()))
                .flatMap(edge -> Stream.of(edge.getStartPosition().toString(), edge.getEndPosition().toString()/*, " " + edge.getSource(), " " + edge.getTarget(), " " + edge.getWeight()*/ ))
                .collect(Collectors.toList());
        log.info(String.format(RouteFinderConstants.GEOJSON, edgeStrings));

    }

}
