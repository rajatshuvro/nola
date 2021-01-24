package com.nola.parsers;

import com.nola.dataStructures.Book;
import com.nola.dataStructures.Bundle;
import com.nola.dataStructures.Checkout;
import com.nola.utilities.PrintUtilities;
import com.nola.utilities.TimeUtilities;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BundleCsvParser {
    private InputStreamReader _reader;
    public final String TimeTag = "Timestamp";
    private final String IdTag = "Id";
    private final String DescriptionTag = "Description";
    private final String BookIdsTag = "Book Ids";

    public BundleCsvParser(InputStream stream){
        _reader = new InputStreamReader(stream);
    }

    public void Close() throws IOException {
        _reader.close();
    }

    public ArrayList<Bundle> GetBundles(){
        var bundles = new ArrayList<Bundle>();
        Iterable<CSVRecord> records = null;
        try {
            records = CSVFormat.RFC4180.withHeader(TimeTag, IdTag, DescriptionTag, BookIdsTag).parse(_reader);
        } catch (IOException e) {
            PrintUtilities.PrintErrorLine("Failed to parse checkout CSV file.");
            return null;
        }
        var isHeaderRecord = true;
        for (CSVRecord record : records) {
            //the first line is also reported as entry. We need to skip it
            if(isHeaderRecord) {
                isHeaderRecord = false;
                continue;
            }

            var timeStamp = record.get(TimeTag).trim();
            var id = record.get(IdTag);
            var description = record.get(DescriptionTag);
            var bookIds = record.get(BookIdsTag).split(",");
            var trimmedIds = new String[bookIds.length];
            for (var i=0; i < bookIds.length; i++){
                trimmedIds[i]= bookIds[i].trim();
            }
            var date = TimeUtilities.parseGoogleDateTime(timeStamp);

            bundles.add(new Bundle(id, description, trimmedIds, date));
        }
        return bundles;
    }
}
