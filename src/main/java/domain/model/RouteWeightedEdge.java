package domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jgrapht.graph.DefaultWeightedEdge;

@NoArgsConstructor
@Getter
public class RouteWeightedEdge extends DefaultWeightedEdge {
    private RoutePosition startPosition;
    private RoutePosition endPosition;

    public void setPositions(RoutePosition startPosition, RoutePosition endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
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
}
