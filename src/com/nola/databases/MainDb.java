package com.nola.databases;

import com.nola.utilities.FileUtilities;
import com.nola.utilities.PathUtilities;
import com.nola.utilities.PrintUtilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.nola.databases.DbCommons.BooksFileName;

public class MainDb {
    public BookDb BookDb;
    public UserDb UserDb;
    public CheckoutDb CheckoutDb;
    public TransactionDb TransactionDb;

    public MainDb(){
        LoadBookDb();
    }

    private boolean LoadBookDb() {
        String filePath = DbCommons.getBooksFilePath();
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
