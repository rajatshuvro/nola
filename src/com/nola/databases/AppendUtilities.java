package com.nola.databases;

import com.nola.dataStructures.Book;
import com.nola.parsers.FlatObjectParser;
import com.nola.utilities.PrintUtilities;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class AppendUtilities {

    public static boolean AppendBooks(ArrayList<Book> books, OutputStream appendStream){
        if (books == null || books.size() ==0 || appendStream == null) return true;
        var appender = new BufferedWriter(new OutputStreamWriter(appendStream));

        try{
            for (var book: books) {
                appender.write(book.toString()+'\n');
                appender.write(FlatObjectParser.RecordSeparator +'\n');
            }
            appender.close();
            return true;
        }
        catch (IOException e){
            PrintUtilities.PrintErrorLine("ERROR appending to book stream.");
        }
        return false;
    }
}
