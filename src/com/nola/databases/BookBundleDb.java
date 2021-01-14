package com.nola.databases;

import com.nola.dataStructures.BookBundle;
import com.nola.dataStructures.IdMap;

import java.util.ArrayList;
import java.util.HashMap;

public class BookBundleDb {
    private HashMap<String, BookBundle> _bookBundles;
    private IdDb _idDb;

    public BookBundleDb(Iterable<BookBundle> bundles, IdDb idDb){
        _bookBundles = new HashMap<>();
        _idDb = idDb;

        for (var bundle: bundles
             ) {
            _bookBundles.put(bundle.Id, bundle);
            _idDb.TryAdd(bundle.Id, bundle.Description);
        }
    }

    public BookBundle GetBookBundle(String id){
        return _bookBundles.containsKey(id)? _bookBundles.get(id) : null;
    }

    public BookBundle FindBundle(String bookId){
        for (var bundle: _bookBundles.values()) {
            if(bundle.contains(bookId)) return bundle;
        }
        return null;
    }

    public String TryAdd(String[] bookIds, String description, BookDb bookDb){
        var validIds = new ArrayList<String>();
        for (var bookId: bookIds) {
            var book = bookDb.GetBook(bookId);
            if (book == null) return null;
            validIds.add(book.GetId());
        }
        var id = _idDb.GenerateShortId();
        _bookBundles.put(id, new BookBundle(id, description, (String[]) validIds.toArray()));
        return id;
    }
}
