package domain.service;

import domain.model.RouteMetaData;
import util.math.RouteFinderConstants;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticPathFindingService {

    public static void logStatisticPathFindingResult(List<RouteMetaData> routeMetaDataList) {
        Optional<RouteMetaData> minVoyageDurationOptional = routeMetaDataList.stream().min(Comparator.comparing(RouteMetaData::getLegDuration));
        final double millisInHour = 3600000d;
        DoubleSummaryStatistics routeStats = routeMetaDataList.stream().map(RouteMetaData::getLegDuration).collect(Collectors.summarizingDouble(duration -> duration / millisInHour));
        if (minVoyageDurationOptional.isEmpty()) {
            return;
        }
        RouteMetaData routeMetaData = minVoyageDurationOptional.get();
        System.out.println(String.format(RouteFinderConstants.GEOJSON, routeMetaData.getPositions().stream().map(Objects::toString).collect(Collectors.toList())));
        System.out.println(String.format("Took %f hours", routeMetaData.getLegDuration() / millisInHour));
        System.out.println(String.format("All routes duration statistics in this direction:  %s", routeStats.toString()));
        System.out.println("____________________________________________________________________________________________________________________________________________");
    }
}
