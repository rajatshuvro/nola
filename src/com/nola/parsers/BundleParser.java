package com.nola.parsers;

import com.nola.dataStructures.Bundle;
import com.nola.dataStructures.Checkout;
import com.nola.utilities.TimeUtilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BundleParser {
    private InputStream _inputStream;

    private final String IdTag = "Id";
    private final String DescriptionTag = "Description";
    private final String BookIdsTag = "BookIds";
    private final String DateTag = "Date";

    public BundleParser(InputStream inputStream){
        _inputStream = inputStream;
    }


    public ArrayList<Bundle> GetBundles() throws IOException {
        ArrayList<Bundle> bundles = new ArrayList<>();
        var fobParser = new FlatObjectParser(_inputStream, new String[]{
                IdTag, DescriptionTag, BookIdsTag, DateTag
        });

        var nextSetOfValues =fobParser.GetNextRecord();
        while ( nextSetOfValues != null){
            var checkout = GetBundle(nextSetOfValues);
            if (checkout != null)  bundles.add(checkout);

            nextSetOfValues = fobParser.GetNextRecord();
        }
        fobParser.close();
        return bundles;
    }

    private Bundle GetBundle(HashMap<String, String> keyValues) {
        String id = null;
        String description = null;
        String bookIds = null;
        Date date = null;

        for (var entry: keyValues.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();

            switch (key){
                case IdTag:
                    id = value;
                    break;
                case DescriptionTag:
                    description = value;
                    break;
                case BookIdsTag:
                    bookIds = value;
                    break;
                case DateTag:
                    date = TimeUtilities.parseDateTime(value);
                    break;
            }

        }

        return new Bundle(id, description, bookIds.split(","), date);
    }
}
