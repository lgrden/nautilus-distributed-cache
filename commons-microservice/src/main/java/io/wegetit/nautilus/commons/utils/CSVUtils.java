package io.wegetit.nautilus.commons.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Slf4j
public class CSVUtils {

    private CSVUtils() {}

    public static <T> List<T> loadFile(Class<T> type, String fileName) {
        try {
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = mapper.schemaFor(type).withColumnSeparator(',').withHeader();
            InputStream is = CSVUtils.class.getResourceAsStream(fileName);
            MappingIterator<T> readValues = mapper.readerFor(type).with(schema).readValues(is);
            return readValues.readAll();
        } catch (Exception e) {
            log.error("Error occurred while loading object list from {}", fileName, e);
            return Collections.emptyList();
        }
    }
}
