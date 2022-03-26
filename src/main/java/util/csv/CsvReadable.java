package util.csv;

import org.supercsv.cellprocessor.ift.CellProcessor;

public interface CsvReadable {
    public String[] getCsvColumnNames();

    public CellProcessor[] getCellProcessors();
}
