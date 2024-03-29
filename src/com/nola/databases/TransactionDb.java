package com.nola.databases;

import com.nola.dataStructures.Book;
import com.nola.dataStructures.Checkout;
import com.nola.dataStructures.Return;
import com.nola.dataStructures.Transaction;
import com.nola.parsers.FlatObjectParser;
import com.nola.utilities.PrintUtilities;
import com.nola.utilities.TimeUtilities;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class TransactionDb {
    private ArrayList<Transaction> _transactions;
    private HashMap<String, Transaction> _latestBookTransactions;
    private UserDb _userDb;
    private BookDb _bookDb;
    public final int CheckoutLimit = 15;

    public TransactionDb(Iterable<Transaction> transactions, UserDb userDb, BookDb bookDb){
        //transactions are assumed to ordered by increasing timestamps
        _userDb = userDb;
        _bookDb = bookDb;
        _transactions = new ArrayList<>();
        _latestBookTransactions = new HashMap<>();

        if(transactions == null) return;
        for (Transaction record: transactions) {
            if(_bookDb.GetBook(record.BookId)== null) {
                PrintUtilities.PrintWarningLine("WARNING: Invalid book id:"+record.BookId+". Ignoring transaction.");
                continue;
            }
            if(record.Type.equals(Transaction.CheckoutTag) && _userDb.GetUser(record.UserId) == null){
                PrintUtilities.PrintWarningLine("WARNING: Invalid user id:"+record.UserId+". Ignoring transaction.");
                continue;
            }
            _transactions.add(record);
            //updating latest transaction
            if(!_latestBookTransactions.containsKey(record.BookId)){
                _latestBookTransactions.put(record.BookId, record);
                continue;
            }
            //update existing record
            var existingRecord = _latestBookTransactions.get(record.BookId);
            if(existingRecord.OlderThan(record)) _latestBookTransactions.replace(record.BookId, record);
        }
    }

    public static ArrayList<Transaction> GetCheckoutTransactions(ArrayList<Transaction> checkouts) {
        var transactions = new ArrayList<Transaction>();
        for (var checkout:
             checkouts) {
            transactions.add(Transaction.Create(checkout.BookId, checkout.UserId, TimeUtilities.GetCurrentTime(), Transaction.CheckoutTag));
        }
        return transactions;
    }
    public static ArrayList<Transaction> GetReturnTransactions(ArrayList<Return> returns) {
        var transactions = new ArrayList<Transaction>();
        for (var record:
                returns) {
            transactions.add(Transaction.Create(record.BookId, null, record.DateTime, Transaction.ReturnTag));
        }
        return transactions;
    }

    public Transaction GetLatest(String bookId){
        if(_latestBookTransactions.containsKey(bookId)) return _latestBookTransactions.get(bookId);
        return null;
    }

    public ArrayList<Transaction> GetUserActivity(String userId){
        var transactions = new ArrayList<Transaction>();
        for(var transaction: _transactions){
            if(transaction.UserId.equals(userId)) transactions.add(transaction);
        }
        return transactions;
    }

    public ArrayList<Transaction> GetBookActivity(long isbn){
        var transactions = new ArrayList<Transaction>();
        for(var transaction: _transactions){
            var bookIsbn = Book.GetIsbn(transaction.BookId);
            if( bookIsbn!= isbn) continue;
            transactions.add(transaction);
        }
        return transactions;
    }

    public Transaction Get(int index){
        if(index < 0 || index >= _transactions.size()) return null;
        return _transactions.get(index);
    }

    public Transaction AddCheckout(String bookId, String userId) {
        var date = TimeUtilities.GetCurrentTime();
        var checkouts = GetPendingCheckouts(userId);
        var checkoutCount = checkouts==null? 0: checkouts.size();
        /*if(checkoutCount >= CheckoutLimit)
        {
            PrintUtilities.PrintWarningLine("Checkout limit reached. Cannot issue more books to user id:"+userId);
            return null;
        }*/
        bookId = Book.GetReducedId(bookId);
        var transaction =Transaction.Create(bookId, userId, date, Transaction.CheckoutTag);
        return Add(transaction)? transaction: null;
    }

    public ArrayList<Transaction> AddCheckouts(ArrayList<Transaction> checkouts) {

        var transactions = new ArrayList<Transaction>();
        for (var checkout: checkouts) {
            var transaction = AddCheckout(checkout);
            if(transaction != null)
            {
                transactions.add(transaction);
                PrintUtilities.PrintSuccessLine(checkout.BookId +" has been checked out by "+ checkout.UserId);
            }
            else PrintUtilities.PrintWarningLine("Checkout attempt was unsuccessful!!");
        }

        return transactions;
    }

    public Transaction AddCheckout(Transaction checkout) {
        return AddCheckout(checkout.BookId, checkout.UserId);
    }
    public ArrayList<Return> Return(ArrayList<Return> returnRecords){
        var validEntries = new ArrayList<Return>();
        for (var record:
             returnRecords) {
            if (Return(record)) validEntries.add(record);
        }
        return validEntries;
    }
    public boolean Return(Return record){
        var transaction = GetLatest(record.BookId);
        if(transaction == null) {
            PrintUtilities.PrintErrorLine("Could not locate a checkout for: "+record.BookId);
            return false;
        }

        var userId = transaction.UserId;
        if(Add(Transaction.Create(record.BookId, userId, record.DateTime, Transaction.ReturnTag))){
            return true;
        }
        return false;
    }

    public int AddRange(Iterable<Transaction> transactions){
        var count =0;
        for (var transaction: transactions) {
            if(Add(transaction)) count++;
        }
        return count;
    }

    public boolean Add(Transaction record) {
        //make sure the book exists in the book database and the user in user database
        if(_bookDb.GetBook(record.BookId)== null){
            PrintUtilities.PrintWarningLine("WARNING:Unknown book id: "+ record.BookId);
            return false;
        }
        if(_userDb.GetUser(record.UserId) == null){
            PrintUtilities.PrintWarningLine("WARNING:Unknown user id: "+ record.UserId);
            return false;
        }
        if(!_latestBookTransactions.containsKey(record.BookId)){
            _latestBookTransactions.put(record.BookId, record);
            _transactions.add(record);
            //AppendTransactions(record);
            return true;
        }
        //if the latest transaction is of type checkout you cannot add another checkout and similarly for return
        var latestRecord = _latestBookTransactions.get(record.BookId);
        if (latestRecord.Type.equals(record.Type)) {
            PrintUtilities.PrintWarningLine("WARNING: cannot "+record.Type+ " a book that is already in that state. Book id:" + record.BookId);
            return false;
        }

        _latestBookTransactions.replace(record.BookId, record);
        _transactions.add(record);
        //AppendTransactions(record);
        return true;
    }

    public final String[] HeaderLines = new String[]{
        "#Onkur library book lending records\n",
        "#Book Id = Onkur book id. Value = <String>\n",
        "#User Id = Onkur user id. Value = <Integer>\n",
        "#Date = Date of transaction. Value = <YYYY-MM-DD HH:MM:ss>\n",
        "#Type = Type of transaction. Value = Checkout/Return\n"
    };
    /// Write out all the records into an output stream
    public void Write(OutputStream stream) throws IOException {
        var writer = new BufferedWriter(new OutputStreamWriter(stream));
        //header lines
        for (String line: HeaderLines) {
            writer.write(line);
        }
        //not required, but for aesthetics
        writer.write(FlatObjectParser.RecordSeparator+'\n');
        for (Transaction record: _transactions) {
            writer.write(record.toString()+'\n');
            writer.write(FlatObjectParser.RecordSeparator+'\n');
        }
        writer.close();
    }

    public String GetBookStatus(String bookId) {
        if(_latestBookTransactions.containsKey(bookId)) return _latestBookTransactions.get(bookId).Type;
        return Transaction.UnknownTag;
    }
    public ArrayList<Transaction> GetPendingCheckouts(String userId) {
        var checkouts = new ArrayList<Transaction>();
        for (Transaction record: GetPendingCheckouts()) {
            if(!userId.equals(record.UserId)) continue;
            checkouts.add(record);
        }
        return checkouts.size()==0? null: checkouts;
    }
    public ArrayList<Transaction> GetPendingCheckouts() {
        var checkouts = new ArrayList<Transaction>();
        for (Transaction record: _latestBookTransactions.values()) {
            if(record.Type.equals(Transaction.CheckoutTag))
                checkouts.add(record);
        }
        return checkouts;
    }

    public void AppendTransactions(Iterable<Transaction> transactions, OutputStream appendStream) throws IOException {
        var  appender = new BufferedWriter(new OutputStreamWriter(appendStream));
        if(transactions == null ) return;

        for (var transaction: transactions
        ) {
            appender.write(transaction.toString()+'\n');
            appender.write(FlatObjectParser.RecordSeparator +'\n');
        }
        appender.close();

    }

    //returns all closed transactions that are older than a year
    // and not the latest for a book
    public ArrayList<Transaction> GetOldTransactions() {
        var oldTransactions = new ArrayList<Transaction>();
        for (var transaction:
             _transactions) {
            var bookId = transaction.BookId;
            if (_latestBookTransactions.containsKey(bookId)){
                var latest= _latestBookTransactions.get(bookId);
                // the latest transaction for a book is never archived
                if (latest.Id==transaction.Id) continue;
            }
            oldTransactions.add(transaction);
        }
        return oldTransactions;
    }

    public ArrayList<Transaction> GetLatestTransactions() {
        var latest = new ArrayList<Transaction>();
        for (var transaction:
             _latestBookTransactions.values()) {
            latest.add(transaction);
        }
        return latest;
    }

    public Transaction GetPendingCheckout(String bookId) {
        if(_latestBookTransactions.containsKey(bookId)) return _latestBookTransactions.get(bookId);
        else return null;
    }

}
