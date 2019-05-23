package com.mw.parser.engine;

import com.mw.parser.util.CustomFileUtil;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.mw.parser.exception.CSVParserException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.List;

public abstract class FileParser {

    private FileParser() {
    }

    public static <T> List<T> readCSVFile(Class<T> clazz, InputStream inputStream, char separator) throws CSVParserException {

        List<T> lines = null;

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(IOUtils.toByteArray(inputStream));
             Reader fileReader = new InputStreamReader(byteArrayInputStream)){

            try {
                lines = new CsvToBeanBuilder(fileReader)
                        .withType(clazz)
                        .withSeparator(separator)
                        .withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
                        .build().parse();
            } catch (RuntimeException e) {
                throw new CSVParserException(e.getMessage(), e);
            }

            if(CollectionUtils.isNotEmpty(lines)) {
                byteArrayInputStream.reset();

                if(!CustomFileUtil.endsWithEmptyLine(CustomFileUtil.stream2file(byteArrayInputStream))) {
                    throw new CSVParserException("Incorrect file format. Last line is not empty.");
                }
            }

        } catch (IOException e) {
            throw new CSVParserException("Unexpected error while reading file.", e);
        }

        return lines;
    }
}
