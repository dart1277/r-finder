package domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteMetaData {

    private String shipId;
    private Long fromSeq;
    private Long toSeq;
    private PortCode fromPort;
    private PortCode toPort;
    private Long legDuration;
    private Long count;
    private List<RoutePosition> positions;

}
