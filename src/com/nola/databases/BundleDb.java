package com.nola.databases;

import com.nola.dataStructures.Bundle;
import com.nola.parsers.ParserUtilities;
import com.nola.utilities.TimeUtilities;

import java.util.ArrayList;
import java.util.HashMap;

public class BundleDb {
    private HashMap<String, Bundle> _bookBundles;
    private IdDb _idDb;

    public BundleDb(Iterable<Bundle> bundles){
        _bookBundles = new HashMap<>();
        _idDb = new IdDb();

        for (var bundle: bundles
             ) {
            _bookBundles.put(bundle.Id, bundle);
            _idDb.TryAdd(bundle.Id, bundle.Description);
        }
    }

    public Bundle GetBookBundle(String id){
        return _bookBundles.containsKey(id)? _bookBundles.get(id) : null;
    }

    public Bundle FindBundle(String bookId){
        for (var bundle: _bookBundles.values()) {
            if(bundle.contains(bookId)) return bundle;
        }
        return null;
    }

    public static final String[] HeaderLines = new String[]{
            "#Onkur library book bundle records\n",
            "#Id = Onkur book bundle id. Value = <String>\n",
            "#Description = Bundle description. Value = <Integer>\n",
            "#Book Ids = Comma separated book ids. Value = <YYYY-MM-DD HH:MM:ss>\n",
            "#Date = Entry date. Value = <YYYY-MM-DD HH:MM:ss>\n"
    };

    public Bundle TryAdd(Bundle bundle, BookDb bookDb){
        for (var bookId: bundle.BookIds) {
            var book = bookDb.GetBook(bookId);
            if (book == null) return null;
        }
        var id = ParserUtilities.IsNullOrEmpty(bundle.Id)? _idDb.GenerateShortId(): bundle.Id;

        var newBundle = new Bundle(id, bundle.Description, bundle.BookIds, TimeUtilities.GetCurrentTime());
        _bookBundles.put(id, newBundle);
        return newBundle;
    }

    public int Count() {
        return _bookBundles.size();
    }
}
