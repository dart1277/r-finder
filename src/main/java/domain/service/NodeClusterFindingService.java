package domain.service;

import domain.model.RouteMetaData;
import domain.model.RoutePosition;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NodeClusterFindingService {

    public static int addNodesToRoutePositions(List<RouteMetaData> routeMetaDataList) {
        List<RoutePosition> positions = routeMetaDataList.stream()
                .flatMap(routeMetaData -> routeMetaData.getPositions().stream())
                .collect(Collectors.toList());

        Map<RoutePosition, Integer> routeNodeMap = new HashMap<>();
        int nodeCount = 0;
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

        return routeNodeMap.size();
/*        Map<Integer, List<RoutePosition>> byNode = positions.stream().collect(Collectors.groupingBy(RoutePosition::getRouteNodeNo));
        DoubleSummaryStatistics collect = byNode.values().stream().collect(Collectors.summarizingDouble(List::size));
        System.out.println(collect);
        System.out.println(routeNodeMap.size());*/
/*        System.out.println(new Date(System.currentTimeMillis()));
        UnionFind<RoutePosition> unionFind = new UnionFind<>(new HashSet<>(positions));
        int positionCount  = positions.size();

        for (int pos = 0; pos < positionCount - 1; ++pos) {
            for (int secPos = pos + 1; secPos < positionCount; ++secPos) {
                RoutePosition routePositionA = positions.get(pos);
                RoutePosition routePositionB = positions.get(secPos);
                if (routePositionA.getDistance(routePositionB) < adjacencyRadiusSquared) {
                    unionFind.union(routePositionA, routePositionB);
                }

            }
        }

        for (RoutePosition position : positions) {
            position.setParent(unionFind.find(position));
        }
        */
    }

/*    private static List<RoutePosition> mapRoutePostions(List<CsvRouteDto> routeDtos) {
        return routeDtos.stream()
                .flatMap(dto -> dto.getPoints().stream())
                .map(dto -> dto.toRoutePosition())
                .collect(Collectors.toList());
    }*/
}
