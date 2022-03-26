package domain.model;

import lombok.Getter;
import lombok.Setter;
import util.math.MathConstants;
import util.math.RoundingUtil;

import java.util.Objects;

@Getter
@Setter
public class RoutePosition {
    private double lon;
    private double lat;
    private long timestamp;
    private int routeNodeNo = -1;
    private RouteMetaData metaData;

    public RoutePosition(double lon, double lat, long timestamp) {
        this.lon = RoundingUtil.round(lon);
        this.lat = RoundingUtil.round(lat);
        this.timestamp = timestamp;
    }

    public double getDistance(RoutePosition routePositionB) {
        return (lon - routePositionB.getLon()) * (lon - routePositionB.getLon()) + (lat - routePositionB.getLat()) * (lat - routePositionB.getLat());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutePosition position = (RoutePosition) o;
        return Math.abs(lon - position.getLon()) < MathConstants.EPSILON && Math.abs(lat - position.getLat()) < MathConstants.EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lon, lat);
    }
}
