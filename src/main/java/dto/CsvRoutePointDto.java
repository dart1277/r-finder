package dto;

import domain.model.RoutePosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import util.math.NumberConversionUtil;
import util.math.RouteFinderConstants;

import java.util.List;

@Slf4j
@Data
@AllArgsConstructor
public class CsvRoutePointDto {

    private double lon;
    private double lat;
    private long timestamp;
    private double speed;
    private double positionAccuracy;


    public static CsvRoutePointDto map(List<Double> array) {
        double lon = NumberConversionUtil.mapDouble(array, 0);
        double lat = NumberConversionUtil.mapDouble(array, 1);
        long timestamp = NumberConversionUtil.mapDouble(array, 2).longValue();
        double speed = NumberConversionUtil.mapDouble(array, 3);
        return new CsvRoutePointDto(lon, lat, timestamp, speed, RouteFinderConstants.ROUTE_ACCURACY);
    }

    public RoutePosition toRoutePosition(double accuracy) {
        return new RoutePosition(lon, lat, timestamp, accuracy);
    }
}
