package dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import util.csv.CsvReadable;
import util.csv.JsonProcessor;

import java.util.List;

@Data
@NoArgsConstructor
public class CsvRouteDto implements CsvReadable {

    // "id","from_seq","to_seq","from_port","to_port","leg_duration","count","points"

    private String id;
    private Long fromSeq;
    private Long toSeq;
    private String fromPort;
    private String toPort;
    private Long legDuration;
    private Long count;
    private List<CsvRoutePointDto> points;

    public String[] getCsvColumnNames() {
        return new String[]{"id", "fromSeq", "toSeq", "fromPort", "toPort", "legDuration", "count", "points"};
    }

    public CellProcessor[] getCellProcessors() {
        return new CellProcessor[]{new NotNull(), new ParseLong(), new ParseLong(), new NotNull(), new NotNull(),
                new ParseLong(), new ParseLong(), new JsonProcessor<>(CsvRoutePointDto::map, CsvRoutePointDto.DTO_VARIABLES_CNT)};
    }

}
