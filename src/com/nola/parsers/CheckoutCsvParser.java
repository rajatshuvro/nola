package com.nola.parsers;

import com.nola.dataStructures.Book;
import com.nola.dataStructures.Checkout;
import com.nola.dataStructures.Transaction;
import com.nola.utilities.PrintUtilities;
import com.nola.utilities.TimeUtilities;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CheckoutCsvParser {
    private InputStreamReader _reader;
    public final String TimeTag = "Timestamp";
    public final String ResourceIdTag = "Resource Id";
    public final String UserIdTag = "User id";
    public final String DueDateTag = "Due Date";

    public CheckoutCsvParser(InputStream stream){
        _reader = new InputStreamReader(stream);
    }

    public void Close() throws IOException {
        _reader.close();
    }

    public ArrayList<Transaction> GetCheckouts() {
        var checkouts = new ArrayList<Transaction>();
        Iterable<CSVRecord> records = null;
        try {
            records = CSVFormat.RFC4180.withHeader(TimeTag, ResourceIdTag, UserIdTag).parse(_reader);
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
            var resIds = record.get(ResourceIdTag).split(",");
            for (var resourceId: resIds) {
                resourceId = resourceId.trim();
                resourceId = Book.GetReducedId(resourceId);

                var userId = record.get(UserIdTag).trim();
                var checkoutDate = TimeUtilities.parseGoogleDateTime(timeStamp);

                checkouts.add(new Transaction(resourceId, userId, checkoutDate, Transaction.CheckoutTag));
            }

        }
        return checkouts;
    }

}
