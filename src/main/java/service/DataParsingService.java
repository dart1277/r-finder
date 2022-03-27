package service;

import domain.model.PortCode;
import domain.model.RouteMetaData;
import dto.CsvRouteDto;
import dto.RouteMapper;
import util.csv.CsvReaderUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataParsingService {
    /**
     * if map key is true List of RouteMetaData for voyage starting in Bremerhaven is returned
     *
     * @param fileName
     * @param positionAccuracy numeric parameter used to reduce the data set to computationally efficient number of global coordinates
     * @return Map Boolean -> List<RouteMetaData>
     */

    public static Map<Boolean, List<RouteMetaData>> parseRouteData(String fileName, double positionAccuracy) {
        List<CsvRouteDto> routeDtos = CsvReaderUtil.readFile(fileName, CsvRouteDto.class);
        List<RouteMetaData> routeMetaDataList = RouteMapper.INSTANCE.toDomainWithAccuracy(routeDtos, positionAccuracy);
        return routeMetaDataList.stream()
                .collect(Collectors.partitioningBy(routeMetaData -> routeMetaData.getFromPort() == PortCode.DEBRV));
    }
}
