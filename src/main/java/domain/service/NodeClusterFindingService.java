package domain.service;

import domain.model.RouteMetaData;
import domain.model.RoutePosition;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NodeClusterFindingService {

    public static int addNodesToRoutePositions(List<RouteMetaData> routeMetaDataList) {
        List<RoutePosition> positions = mapRoutePositionData(routeMetaDataList);
        Map<RoutePosition, Integer> routeNodeMap = updateNodeAssignments(positions);
        return routeNodeMap.size();
    }

    private static Map<RoutePosition, Integer> updateNodeAssignments(List<RoutePosition> positions) {
        Map<RoutePosition, Integer> routeNodeMap = new HashMap<>();
        int nodeCount = RoutePosition.FIRST_ASSIGNED_NODE_NO;
        for (RoutePosition position : positions) {
            Integer routeNodeNo = routeNodeMap.get(position);
            if (Objects.nonNull(routeNodeNo)) {
                position.setRouteNodeNo(routeNodeNo);
            } else {
                routeNodeMap.put(position, nodeCount);
                position.setRouteNodeNo(nodeCount);
                nodeCount++;
            }

        }
        return routeNodeMap;
    }

    private static List<RoutePosition> mapRoutePositionData(List<RouteMetaData> routeMetaDataList) {
        return routeMetaDataList.stream()
                .map(RouteMetaData::getPositions)
                .filter(Predicate.not(List::isEmpty))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
