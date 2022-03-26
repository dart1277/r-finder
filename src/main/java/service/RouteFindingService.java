package service;

import domain.model.RouteMetaData;
import domain.service.NodeClusterFindingService;
import dto.CsvRouteDto;
import dto.RouteMapper;
import util.csv.CsvReaderUtil;

import java.util.List;

public class RouteFindingService {

    public static void findRoute(String fileName) {
        List<CsvRouteDto> routeDtos = CsvReaderUtil.readFile(fileName, CsvRouteDto.class);
        List<RouteMetaData> routeMetaDataList = RouteMapper.INSTANCE.toDomain(routeDtos);
        NodeClusterFindingService.addNodesToRoutePositions(routeMetaDataList);

    }

}
