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

    public static Iterable<User> ReadUsers(FileInputStream inputStream) {
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

    public static Iterable<Transaction> ReadTransactions(FileInputStream inputStream) {
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
        var parser = new BookParser(inputStream);

        try {
            var bookDb = new BookDb(parser.GetBooks());
            inputStream.close();
            return bookDb;
        } catch (IOException e) {
            PrintUtilities.PrintErrorLine("Failed to load Book DB.");
            e.printStackTrace();
        }
        return null;
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
}
