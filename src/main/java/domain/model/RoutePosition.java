package domain.model;

import lombok.Getter;
import lombok.Setter;
import util.math.RoundingUtil;

import java.util.Objects;

@Getter
@Setter
public class RoutePosition implements Comparable<RoutePosition> {

    public static final int NO_ASSIGNED_NODE_NO = -1;
    public static final int FIRST_ASSIGNED_NODE_NO = 1;

    private final double lon;
    private final double lat;
    private final long timestamp;
    private double speed;
    private final double positionAccuracy;
    private int routeNodeNo = NO_ASSIGNED_NODE_NO;
    private RouteMetaData metaData;

    public RoutePosition(double lon, double lat, long timestamp, double speed, double positionAccuracy) {
        this.positionAccuracy = positionAccuracy;
        this.lon = RoundingUtil.round(lon, positionAccuracy);
        this.lat = RoundingUtil.round(lat, positionAccuracy);
        this.timestamp = timestamp;
        this.speed = speed;
    }

    public long getDurationInMillis(RoutePosition endPosition) {
        return endPosition.getTimestamp() - getTimestamp();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutePosition position = (RoutePosition) o;
        return Math.abs(lon - position.getLon()) < positionAccuracy && Math.abs(lat - position.getLat()) < positionAccuracy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lon, lat);
    }

    @Override
    public String toString() {
        return String.format("[%.5f, %.5f]", lon, lat);
    }

    @Override
    public int compareTo(RoutePosition o) {
        return Long.compare(timestamp, o.timestamp);
    }
}
