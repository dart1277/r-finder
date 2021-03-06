package util.math;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RouteFinderConstants {
    public static final double ROUTE_ACCURACY = 1d / 450;
    public static final String GEOJSON = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":%s}}]}";
}
