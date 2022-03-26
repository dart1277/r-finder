package util.csv;

import org.supercsv.cellprocessor.ift.CellProcessor;

public interface CsvReadable {
    String[] getCsvColumnNames();

    CellProcessor[] getCellProcessors();
}
