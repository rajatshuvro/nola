package com.nola.databases;

import com.nola.dataStructures.Checkout;
import com.nola.dataStructures.Return;
import com.nola.parsers.FlatObjectParser;
import com.nola.utilities.PrintUtilities;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class CheckoutDb {
    private HashMap<String, Checkout> _checkouts;
    private boolean _hasReturns =false;
    public final int CheckoutLimit = 15;

    private UserDb _userDb;
    private BookDb _bookDb;

    public CheckoutDb(Iterable<Checkout> checkouts, UserDb userDb, BookDb idDb)  {
        _userDb = userDb;
        _bookDb = idDb;

        _checkouts = new HashMap<>();

        if(checkouts!=null)
            for (var checkout:
                 checkouts) {
                _checkouts.put(checkout.BookId, checkout);
            }
    }


    public Checkout GetCheckout(String bookId){
        return _checkouts.getOrDefault(bookId, null);
    }

    public boolean IsCheckedOut(String bookId){
        return _checkouts.containsKey(bookId);
    }
    public ArrayList<Checkout> ReadCheckouts(String userId){
        var checkouts = new ArrayList<Checkout>();
        for (var checkout:
             _checkouts.values()) {
            if (checkout.UserId != null && checkout.UserId.equals(userId)) checkouts.add(checkout);
        }
        return checkouts;
    }

    public boolean TryAdd(Checkout checkout)  {
        var book =_bookDb.GetBook(checkout.BookId);
        if( book == null) {
            PrintUtilities.PrintWarningLine("WARNING: Invalid book id:"+checkout.BookId+". Ignoring transaction.");
            return false;
        }
        var user = _userDb.ResolveUser(checkout.UserId, checkout.Email);

        if( user == null){
            PrintUtilities.PrintWarningLine("WARNING: Invalid user id:"+checkout.UserId+". Ignoring transaction.");
            return false;
        }

        var bookId = book.GetId();
        if(IsCheckedOut(bookId)) {
            PrintUtilities.PrintWarningLine("Can not checkout the same book twice:"+checkout.BookId);
            return false;
        }
        var checkouts = ReadCheckouts(user.Id);
        var checkoutCount = checkouts.size();
        if(checkoutCount >= CheckoutLimit)
        {
            PrintUtilities.PrintWarningLine("Checkout limit reached. Cannot issue more books to user id:"+checkout.UserId);
            return false;
        }
        _checkouts.put(bookId, checkout);

        return true;
    }

    public ArrayList<Checkout> TryAddRange(Iterable<Checkout> checkouts) {
        var validEntries = new ArrayList<Checkout>();
        for (var checkout:
             checkouts) {
            if(TryAdd(checkout)) validEntries.add(checkout);
        }

        return validEntries;
    }

    public boolean Return (Return record){
        var book = _bookDb.GetBook(record.BookId);
        if (book == null) return false;

        var bookId = book.GetId();
        if (_checkouts.containsKey(bookId)){
            _checkouts.remove(bookId);
            _hasReturns = true;
            return true;
        }
        return false;
    }

    public static final String[] HeaderLines = new String[]{
            "#Onkur library book checkout records\n",
            "#Book Id = Onkur book id. Value = <String>\n",
            "#User Id = Onkur user id. Value = <Integer>\n",
            "#Checkout Date = Checkout date. Value = <YYYY-MM-DD HH:MM:ss>\n",
            "#Due Date = Due date. Value = <YYYY-MM-DD HH:MM:ss>\n"
    };

    public void WriteReturns(OutputStream rewriteStream, boolean leaveOpen) throws IOException{
        if (!_hasReturns) return;
        if(rewriteStream == null) {
            PrintUtilities.PrintErrorLine("Pending returns require a re-write stream to be recorded.");
            throw new IOException("Missing re-write stream");
        }
        var writer =  new BufferedWriter(new OutputStreamWriter(rewriteStream));
        for (var line:
                HeaderLines) {
            writer.write(line);

        }
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        for (var checkout: _checkouts.values()) {
            writer.write(checkout.toString()+'\n');
            writer.write(FlatObjectParser.RecordSeparator+"\n");
        }

        writer.close();
        if(!leaveOpen) rewriteStream.close();
        _hasReturns = false;

    }

    public boolean HasReturns() {
        return _hasReturns;
    }
}
