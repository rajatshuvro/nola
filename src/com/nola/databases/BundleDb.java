package com.nola.databases;

import com.nola.dataStructures.Bundle;
import com.nola.parsers.FlatObjectParser;
import com.nola.parsers.ParserUtilities;
import com.nola.utilities.PrintUtilities;
import com.nola.utilities.TimeUtilities;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;

public class BundleDb {
    private HashMap<String, Bundle> _bookBundles;
    private HashMap<String, String> _bookBundleMembership;
    private IdDb _idDb;

    public BundleDb(Iterable<Bundle> bundles){
        _bookBundles = new HashMap<>();
        _bookBundleMembership = new HashMap<>();
        _idDb = new IdDb();

        for (var bundle: bundles
             ) {
            _bookBundles.put(bundle.Id, bundle);
            for (var bookId: bundle.BookIds) {
                _bookBundleMembership.put(bookId, bundle.Id);
            }
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
        var bookIds = new String[bundle.BookIds.length];
        var i=0;
        for (var bookId: bundle.BookIds) {
            var book = bookDb.GetBook(bookId);
            if (book == null) {
                PrintUtilities.PrintErrorLine("Invalid book id: "+ bookId+ " in bundle: "+ bundle.Id);
                return null;
            }
            if (_bookBundleMembership.containsKey(bookId)){
                PrintUtilities.PrintErrorLine(bookId+" already belongs to bundle "+ _bookBundleMembership.get(bookId));
                return null;
            }
            bookIds[i++]= book.GetId();
        }
        var id = ParserUtilities.IsNullOrEmpty(bundle.Id)? _idDb.GenerateShortId(): bundle.Id;

        var newBundle = new Bundle(id, bundle.Description, bookIds, TimeUtilities.GetCurrentTime());
        _bookBundles.put(id, newBundle);
        return newBundle;
    }

    public int Count() {
        return _bookBundles.size();
    }

    public void PrintAll(BookDb bookDb, OutputStreamWriter writer) throws IOException {
        for (var bundle: _bookBundles.values()) {
            writer.write("Bundle ID:\t"+bundle.Id+'\n');
            for (var bookId: bundle.BookIds) {
                var book = bookDb.GetBook(bookId);
                writer.write(book.ShortId+"\t\t"+ bookDb.GetBook(bookId).Title+'\n');
            }
            writer.write(FlatObjectParser.RecordSeparator+'\n');

        }
        writer.flush();
    }
}
