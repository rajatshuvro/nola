package com.nola.parsers;

import com.nola.dataStructures.Book;
import com.nola.dataStructures.Return;
import com.nola.utilities.PrintUtilities;
import com.nola.utilities.TimeUtilities;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReturnCsvParser {
    private InputStreamReader _reader;
    public final String TimeTag = "Timestamp";
    public final String ResourceIdTag = "Resource id";

    public ReturnCsvParser(InputStream stream){
        _reader = new InputStreamReader(stream);
    }

    public void Close() throws IOException {
        _reader.close();
    }

    public ArrayList<Return> GetReturns() {
        var returns = new ArrayList<Return>();
        Iterable<CSVRecord> records = null;
        try {
            records = CSVFormat.RFC4180.withHeader(TimeTag, ResourceIdTag).parse(_reader);
        } catch (IOException e) {
            PrintUtilities.PrintErrorLine("Failed to parse return CSV stream");
        }
        var isHeaderRecord = true;
        for (CSVRecord record : records) {
            //the first line is also reported as entry. We need to skip it
            if(isHeaderRecord) {
                isHeaderRecord = false;
                continue;
            }

            var dateTime = TimeUtilities.parseGoogleDateTime(record.get(TimeTag));
            var resourceId = record.get(ResourceIdTag).trim();
            for (var resId: resourceId.split(",")) {
                resourceId = Book.GetReducedId(resId.trim());
                returns.add(new Return(resourceId, dateTime));
            }

        }
        return returns;
    }

}
