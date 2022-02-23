package io.wegetit.nautilus.commons.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class CSVUtils {

    private CSVUtils() {}

    public static <T> List<T> loadFile(String fileName, Function<CSVRecord, T> f) {
        List<T> data = new ArrayList<>();
        try {
            InputStream is = CSVUtils.class.getClassLoader().getResourceAsStream(fileName);
            Reader reader = new InputStreamReader(is);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : records) {
                data.add(f.apply(record));
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error occurred while reading csv file " + fileName, e);
        }
        return data;
    }
}
