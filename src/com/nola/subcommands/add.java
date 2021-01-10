package com.nola.subcommands;

import com.nola.dataStructures.Book;
import com.nola.databases.*;
import com.nola.parsers.BookCsvParser;
import com.nola.utilities.FileUtilities;
import com.nola.utilities.PrintUtilities;
import org.apache.commons.cli.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class add {
    private static String commandSyntex = "nola add  -b [books CSV file] -u [users CSV file]";
    public static void Run(String[] args) {
        Options options = new Options();

        Option bookOption = new Option("b", "book", true, "file with book records");
        bookOption.setRequired(false);
        options.addOption(bookOption);

        Option userOption = new Option("u", "user", true, "file with user records");
        userOption.setRequired(false);
        options.addOption(userOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        if (args.length == 1) {
            formatter.printHelp(commandSyntex, options);
            return;
        }

        try{
            cmd = parser.parse(options, args);
            if (cmd.hasOption("book")){
                var filePath = cmd.getOptionValue("book");
                if(!FileUtilities.Exists(filePath)){
                    PrintUtilities.PrintErrorLine("Specified file does not exist: "+filePath);
                }
                var appendStream = DbUtilities.GetAppendStream(DbCommons.getBooksFilePath());
                var count = AddBooks(DbUtilities.LoadBookDb(), new FileInputStream(filePath), appendStream, false);
                appendStream.close();
                System.out.println("Number of new books added: "+count);
            }
        }
        catch (ParseException | IOException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(commandSyntex, options);
        }
    }

    public static int AddBooks(BookDb bookDb, InputStream inputStream, OutputStream appendStream, boolean forceCommit){
        if(inputStream == null || appendStream == null) return 0;

        var csvParser = new BookCsvParser(inputStream);

        var validEntries = new ArrayList<Book>();
        for (var book: csvParser.GetBooks()) {
            var newBook = bookDb.Add(book);
            if (newBook != null) validEntries.add(newBook);
        }

        if(validEntries.size()>0){
            if(!forceCommit && !PromptUtilities.CommitValidEntries(validEntries)) return 0;

            AppendUtilities.AppendItems(validEntries, appendStream);
        }
        return validEntries.size();
    }

}
