package util.csv;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.util.CsvContext;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JsonProcessor<U> extends CellProcessorAdaptor implements StringCellProcessor {

    private final Function<List<Double>, U> mapper;

    private Gson gson = new Gson();


    @Override
    public <T> T execute(Object value, CsvContext context) {
        if (value instanceof String) {
            String strValue = (String) value;
            strValue = strValue.replaceAll("\\]\\s*\\[", "], [");
            List<List<Double>> list = gson.fromJson(strValue, new TypeToken<List<List<Double>>>() {
            }.getType());
            return (T) list.stream().map(mapper::apply).collect(Collectors.toList()); // List<U> is T
        }
        return null;
    }
}

