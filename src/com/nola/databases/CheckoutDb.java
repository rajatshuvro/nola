package com.nola.databases;

import com.nola.dataStructures.Checkout;
import com.nola.dataStructures.Return;
import com.nola.utilities.PrintUtilities;

import java.util.ArrayList;
import java.util.Arrays;
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

    public ArrayList<Checkout> GetUserCheckouts(String userId){
        var checkouts = new ArrayList<Checkout>();
        for (var checkout:
             _checkouts.values()) {
            if (checkout.UserId != null && checkout.UserId.equals(userId)) checkouts.add(checkout);
        }
        return checkouts;
    }

    public Checkout TryAdd(Checkout checkout)  {
        var book =_bookDb.GetBook(checkout.BookId);
        if( book == null) {
            PrintUtilities.PrintWarningLine("WARNING: Invalid book id:"+checkout.BookId+". Ignoring transaction.");
            return null;
        }
        var user = _userDb.ResolveUser(checkout.UserId, checkout.Email);

        if( user == null){
            PrintUtilities.PrintWarningLine("WARNING: Invalid user id:"+checkout.UserId+". Ignoring transaction.");
            return null;
        }

        var bookId = book.GetId();
        if(IsCheckedOut(bookId)) {
            PrintUtilities.PrintWarningLine("Can not checkout the same book twice:"+checkout.BookId);
            return null;
        }
        var checkouts = GetUserCheckouts(user.Id);
        var checkoutCount = checkouts.size();
        if(checkoutCount >= CheckoutLimit)
        {
            PrintUtilities.PrintWarningLine("Checkout limit reached. Cannot issue more books to user id:"+checkout.UserId);
            return null;
        }
        _checkouts.put(bookId, checkout);

        return new Checkout(bookId, user.Id, user.Email, checkout.CheckoutDate, checkout.DueDate);
    }

    public ArrayList<Checkout> TryAddRange(Iterable<Checkout> checkouts) {
        var validEntries = new ArrayList<Checkout>();
        for (var checkout: checkouts) {
            var newCheckout = TryAdd(checkout);
            if(newCheckout != null) validEntries.add(newCheckout);
        }

        return validEntries;
    }

    public Return Return (Return record){
        var book = _bookDb.GetBook(record.BookId);
        if (book == null) return null;

        var bookId = book.GetId();
        if (_checkouts.containsKey(bookId)){
            _checkouts.remove(bookId);
            _hasReturns = true;
            return new Return(bookId, record.DateTime);
        }
        return null;
    }

    public Checkout[] GetAllCheckouts(){
        var checkouts = new Checkout[_checkouts.size()];
        var i=0;
        for (var checkout: _checkouts.values()) {
            checkouts[i++]= checkout;
        }
        Arrays.sort(checkouts);
        return checkouts;
    }

    public static final String[] HeaderLines = new String[]{
            "#Onkur library book checkout records\n",
            "#Book Id = Onkur book id. Value = <String>\n",
            "#User Id = Onkur user id. Value = <Integer>\n",
            "#Checkout Date = Checkout date. Value = <YYYY-MM-DD HH:MM:ss>\n",
            "#Due Date = Due date. Value = <YYYY-MM-DD HH:MM:ss>\n"
    };

    public boolean HasReturns() {
        return _hasReturns;
    }

    public ArrayList<Return> ReturnRange(ArrayList<Return> records) {
        var validEntries = new ArrayList<Return>();
        for (var record: records) {
            var newReturn =Return(record);
            if (newReturn != null) validEntries.add(newReturn);
        }
        return validEntries;
    }
}
