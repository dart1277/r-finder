package domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jgrapht.graph.DefaultWeightedEdge;

@NoArgsConstructor
@Getter
public class RouteWeightedEdge extends DefaultWeightedEdge {
    private RoutePosition startPosition;
    private RoutePosition endPosition;
    private long sampleCount = 0;
    private long edgeDurationSum = 0;
    private double edgeDurationReciprocalSum = 0d;

    public double computeInitialWeight(RoutePosition initialStartPosition, RoutePosition initialEndPosition) {
        this.startPosition = initialStartPosition;
        this.endPosition = initialEndPosition;
        return computeWeight(initialStartPosition, initialEndPosition);
    }

    @Override
    public Integer getSource() {
        return (Integer) super.getSource();
    }

    @Override
    public Integer getTarget() {
        return (Integer) super.getTarget();
    }

    @Override
    public double getWeight() {
        return super.getWeight();
    }

    public double computeWeight(RoutePosition startPos, RoutePosition endPos) {
        ++sampleCount;
        long durationInMillis = startPos.getDurationInMillis(endPos);
        edgeDurationSum += durationInMillis;
        edgeDurationReciprocalSum += 1d / durationInMillis;
        return edgeDurationReciprocalSum / sampleCount;
    }
}
