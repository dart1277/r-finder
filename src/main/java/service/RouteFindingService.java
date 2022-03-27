package service;

import domain.model.RouteMetaData;
import domain.service.StatisticPathFindingService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class RouteFindingService {

    public static void findRoute(String fileName, double positionAccuracy) {
        GraphRouteFindingService.findRoute(DataParsingService.parseRouteData(fileName, positionAccuracy));
        Map<Boolean, List<RouteMetaData>> routeDataMap = DataParsingService.parseRouteData(fileName, positionAccuracy);
        System.out.println("Fastest voyage from Bremerhaven to Hamburg by minimum time");
        StatisticPathFindingService.logStatisticPathFindingResult(routeDataMap.get(Boolean.TRUE));
        System.out.println("Fastest voyage from Hamburg to Bremerhaven  by minimum time");
        StatisticPathFindingService.logStatisticPathFindingResult(routeDataMap.get(Boolean.FALSE));

    }


}
