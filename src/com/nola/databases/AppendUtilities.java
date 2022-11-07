package com.nola.databases;

import com.nola.dataStructures.Book;
import com.nola.dataStructures.Checkout;
import com.nola.parsers.FlatObjectParser;
import com.nola.utilities.PrintUtilities;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class AppendUtilities {

    public static<T> boolean AppendItems(ArrayList<T> items, OutputStream appendStream){
        if (items == null || appendStream == null) return true;
        var appender = new BufferedWriter(new OutputStreamWriter(appendStream));

        try{
            for (var item: items) {
                appender.write(item.toString()+'\n');
                appender.write(FlatObjectParser.RecordSeparator +'\n');
            }
            appender.close();
            return true;
        }
        catch (IOException e){
            PrintUtilities.PrintErrorLine("ERROR appending items to stream. Type:"+items.get(0).getClass().getName());
        }
        return false;
    }

    public static<T> boolean Rewrite(String[] headerLines, T[] items, OutputStream writeStream, boolean leaveOpen) {
        if (items == null || writeStream == null) return true;
        var writer = new BufferedWriter(new OutputStreamWriter(writeStream));

        try{
            for (var line:
                 headerLines) {
                writer.write(line);
            }
            writer.write(FlatObjectParser.RecordSeparator +'\n');

            for (var item: items) {
                writer.write(item.toString()+'\n');
                writer.write(FlatObjectParser.RecordSeparator +'\n');
            }
            if(!leaveOpen) writer.close();
            return true;
        }
        catch (IOException e){
            PrintUtilities.PrintErrorLine("ERROR writing items to stream. Type:"+items[0].getClass().getName());
        }
        return false;
    }

    public static<T> boolean Rewrite(String[] headerLines, ArrayList<T> items, OutputStream writeStream, boolean leaveOpen) {
        if (items == null || writeStream == null) return true;
        var writer = new BufferedWriter(new OutputStreamWriter(writeStream));

        try{
            for (var line:
                    headerLines) {
                writer.write(line);
            }
            writer.write(FlatObjectParser.RecordSeparator +'\n');

            for (var item: items) {
                writer.write(item.toString()+'\n');
                writer.write(FlatObjectParser.RecordSeparator +'\n');
            }
            if(!leaveOpen) writer.close();
            return true;
        }
        catch (IOException e){
            PrintUtilities.PrintErrorLine("ERROR writing items to stream. Type:"+items.get(0).getClass().getName());
        }
        return false;
    }
}
