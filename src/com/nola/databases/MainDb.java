package com.nola.databases;

import com.nola.utilities.FileUtilities;
import com.nola.utilities.PathUtilities;
import com.nola.utilities.PrintUtilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainDb {
    public final String UsersFileName = "Users.fob";
    public final String BooksFileName = "Books.fob";
    public final String TransactionsFileName = "Transactions.fob";
    public final String CheckoutsFileName = "Checkouts.fob";

    private String _dataDir;
    public BookDb BookDb;
    public UserDb UserDb;
    public CheckoutDb CheckoutDb;
    public TransactionDb TransactionDb;

    public MainDb(String dataDir){
        _dataDir = dataDir;
    }

    private boolean LoadBookDb() {
        String filePath = PathUtilities.combine(_dataDir, BooksFileName);//DataDir+ File.separatorChar+BooksFileName;
        var inputStream = DbUtilities.GetFileReadStream(filePath);
        if (inputStream == null)
        {
            PrintUtilities.PrintErrorLine("could not find file:"+filePath);
            return false;
        }

        LoadBookDb(inputStream);
        return true;
    }
    //public for unit testing
    public boolean LoadBookDb(InputStream inputStream){
        BookDb = new BookDb(DbUtilities.ReadBooks(inputStream));
        return true;
    }
}
