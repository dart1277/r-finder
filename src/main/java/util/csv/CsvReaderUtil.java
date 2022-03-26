package util.csv;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CsvReaderUtil {

    public static <T extends CsvReadable> List<T> readFile(String fileName, Class<T> clazz) {
        try {
            return tryReadFile(fileName, clazz);
        } catch (Exception ex) {
            log.error(String.format("Error reading file %s %s", fileName, ex.getMessage()), ex);
        }
        return Collections.emptyList();
    }

    private static <T extends CsvReadable> List<T> tryReadFile(String fileName, Class<T> clazz) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ICsvBeanReader reader = null;
        List<T> out = new ArrayList<>();
        try {
            reader = new CsvBeanReader(new FileReader(fileName), CsvPreference.STANDARD_PREFERENCE);
            reader.getHeader(true);
            T row = clazz.getDeclaredConstructor().newInstance();
            while ((row = reader.read(clazz, row.getCsvColumnNames(), row.getCellProcessors())) != null) {
                out.add(row);
            }
        } finally {
            if (Objects.nonNull(reader)) {
                reader.close();
            }
        }
        return out;
    }

}
