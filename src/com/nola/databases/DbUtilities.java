package com.nola.databases;

import com.nola.dataStructures.Book;
import com.nola.dataStructures.Checkout;
import com.nola.dataStructures.Transaction;
import com.nola.dataStructures.User;
import com.nola.parsers.BookParser;
import com.nola.parsers.CheckoutParser;
import com.nola.parsers.TransactionParser;
import com.nola.parsers.UserParser;
import com.nola.utilities.FileUtilities;
import com.nola.utilities.PrintUtilities;

import java.io.*;
import java.util.ArrayList;

public class DbUtilities {
    public static ArrayList<Checkout> ReadCheckouts(InputStream inputStream)  {
        if(inputStream !=null){
            var parser = new CheckoutParser(inputStream);
            ArrayList<Checkout> checkouts;
            try {
                checkouts = parser.GetCheckouts();
                inputStream.close();
                return checkouts;
            } catch (IOException e) {
                PrintUtilities.PrintWarningLine("Error reading checkout fob file");
                return null;
            }

        }
        return null;
    }

    public static ArrayList<Book> ReadBooks(InputStream inputStream) {
        if(inputStream==null) return null;
        var parser = new BookParser(inputStream);
        ArrayList<Book> books;
        try {
            books = parser.GetBooks();
            inputStream.close();
            return books;
        } catch (IOException e) {
            PrintUtilities.PrintWarningLine("Error reading book fob file");
            return null;
        }
    }

    public static Iterable<User> ReadUsers(InputStream inputStream) {
        if(inputStream==null) return null;
        var parser = new UserParser(inputStream);
        ArrayList<User> users;
        try {
            users = parser.GetUsers();
            inputStream.close();
            return users;
        } catch (IOException e) {
            PrintUtilities.PrintWarningLine("Error reading book fob file");
            return null;
        }
    }

    public static Iterable<Transaction> ReadTransactions(InputStream inputStream) {
        if(inputStream==null) return null;
        var parser = new TransactionParser(inputStream);
        ArrayList<Transaction> transactions;
        try {
            transactions = parser.GetTransactions();
            inputStream.close();
            return transactions;
        } catch (IOException e) {
            PrintUtilities.PrintWarningLine("Error reading book fob file");
            return null;
        }
    }

    public static InputStream GetFileReadStream(String filePath){
        if(!FileUtilities.Exists(filePath)){
            return null;
        }
        try {
            var inputStream = new FileInputStream(filePath);
            return inputStream;
        } catch (FileNotFoundException e) {

        }
        return null;
    }

    public static BookDb LoadBookDb() {
        var filePath = DbCommons.getBooksFilePath();
        var inputStream = GetFileReadStream(filePath);
        return getBookDb(inputStream);
    }

    private static BookDb getBookDb(InputStream inputStream) {
        var books = ReadBooks(inputStream);

        var bookDb = new BookDb(books);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookDb;
    }

    public static UserDb LoadUserDb() {
        var filePath = DbCommons.getUsersFilePath();
        var inputStream = GetFileReadStream(filePath);

        return getUserDb(inputStream);
    }

    private static UserDb getUserDb(InputStream inputStream) {
        var users = ReadUsers(inputStream);
        var userDb = new UserDb(users);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userDb;
    }

    public static TransactionDb LoadTransactionsDb(BookDb bookDb, UserDb userDb) {
        var filePath = DbCommons.getTransactionsFilePath();
        var inputStream = GetFileReadStream(filePath);

        return getTransactionDb(bookDb, userDb, inputStream);
    }

    private static TransactionDb getTransactionDb(BookDb bookDb, UserDb userDb, InputStream inputStream) {
        var transactions = ReadTransactions(inputStream);
        var transactionDb = new TransactionDb(transactions, userDb, bookDb);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactionDb;
    }

    public static CheckoutDb LoadCheckoutDb(BookDb bookDb, UserDb userDb) {
        var filePath = DbCommons.getCheckoutsFilePath();
        var inputStream = GetFileReadStream(filePath);

        return getCheckoutDb(bookDb, userDb, inputStream);
    }

    private static CheckoutDb getCheckoutDb(BookDb bookDb, UserDb userDb, InputStream inputStream) {
        var checkouts = ReadCheckouts(inputStream);
        var checkoutDb = new CheckoutDb(checkouts, userDb, bookDb);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return checkoutDb;
    }

    public static OutputStream GetAppendStream(String filePath) {
        if(!FileUtilities.Exists(filePath)){
            return null;
        }
        try {
            var appendStream = new FileOutputStream(filePath,true);
            return appendStream;
        } catch (FileNotFoundException e) {

        }
        return null;
    }

    public static MainDb LoadMainDb() {
        var bookDb = LoadBookDb();
        var userDb = LoadUserDb();
        var checkoutDb = LoadCheckoutDb(bookDb, userDb);
        var transactionDb = LoadTransactionsDb(bookDb, userDb);

        return new MainDb(bookDb, userDb, checkoutDb, transactionDb);
    }


}
