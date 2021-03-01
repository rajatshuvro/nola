package com.nola.subcommands;

import com.nola.NativeSearch.ISearchDocument;
import com.nola.NativeSearch.InvertedIndex;
import com.nola.NativeSearch.SmithWaterman;
import com.nola.parsers.FlatObjectParser;
import com.nola.utilities.PrintUtilities;

import java.util.ArrayList;

public class FindUtilities {
    private static final int ResultCount = 5;

    public static void OutputResults(ArrayList<String> results, String banner) {
        if (results.size() <= 0) return;

        System.out.println(banner);
        for (var result: results) {
            PrintUtilities.PrintLine(result);
            PrintUtilities.PrintDelimiterLine(FlatObjectParser.RecordSeparator);
        }

        results.clear();
    }

    public static String ConstructQuery(String[] args) {
        var  queryText = args[1];
        for (var i=2; i < args.length; i++ ){
            queryText+= ' ' + args[i];
        }
        return queryText;
    }

    public static ArrayList<String> search(String queryText, ArrayList<ISearchDocument> documents, InvertedIndex searchIndex) {
        var topDocs = searchIndex.Search(queryText);
        var results = new ArrayList<String>();

        for(var i=0; i < ResultCount && i < topDocs.length; i++)
            results.add(documents.get(topDocs[i]).toString());
        return results;
    }

    public static InvertedIndex getInvertedIndex(ArrayList<ISearchDocument> documents) {
        var searchIndex = new InvertedIndex(new SmithWaterman());
        for (var book: documents) {
            searchIndex.Add(book.GetContent());
        }
        return searchIndex;
    }
}
