package com.mw.parser.engine;

import com.mw.parser.database.model.FileRecord;
import com.mw.parser.exception.CSVParserException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.InputStream;
import java.util.List;

public class FileParserTest {

    @Test
    public void shouldPass() {
        InputStream inputStream = FileParserTest.class.getClassLoader().getResourceAsStream("correct.csv");

        try {
            List<FileRecord> lines = FileParser.readCSVFile(FileRecord.class, inputStream, ',');

            Assert.assertEquals(lines.size(), 6);

        } catch (CSVParserException e) {
            e.printStackTrace();
        }
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void shouldFailWith_EmptyLastLine() throws CSVParserException {
        expectedEx.expect(CSVParserException.class);
        expectedEx.expectMessage("Incorrect file format. Last line is not empty.");

        InputStream inputStream = FileParserTest.class.getClassLoader().getResourceAsStream("bad_missing_last_line.csv");
        List<FileRecord> lines = FileParser.readCSVFile(FileRecord.class, inputStream, ',');
    }

    @Test
    public void shouldFailWith_DateFormat() throws CSVParserException {
        expectedEx.expect(CSVParserException.class);
        expectedEx.expectMessage("Error parsing CSV line: 7. [9,5,6,2019-04-07TEST20:22:38]");

        InputStream inputStream = FileParserTest.class.getClassLoader().getResourceAsStream("bad_wrong_data.csv");
        List<FileRecord> lines = FileParser.readCSVFile(FileRecord.class, inputStream, ',');
    }
}
