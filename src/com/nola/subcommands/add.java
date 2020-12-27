package com.nola.subcommands;

import com.nola.dataStructures.Book;
import com.nola.databases.*;
import com.nola.parsers.BookCsvParser;
import com.nola.parsers.CheckoutCsvParser;
import com.nola.parsers.ReturnCsvParser;
import com.nola.utilities.FileUtilities;
import org.apache.commons.cli.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class add {
    private static String commandSyntex = "nola add  -b [books CSV file] -u [users CSV file] -c [checkout CSV file] -r [return CSV file]";
    public static void Run(String[] args) {
        Options options = new Options();

        Option bookOption = new Option("b", "book", true, "file with book records");
        bookOption.setRequired(false);
        options.addOption(bookOption);

        Option userOption = new Option("u", "user", true, "file with user records");
        userOption.setRequired(false);
        options.addOption(userOption);

        Option checkoutOption = new Option("c", "co", true, "file with checkout records");
        checkoutOption.setRequired(false);
        options.addOption(checkoutOption);

        Option returnOption = new Option("r", "ret", true, "file with return records");
        returnOption.setRequired(false);
        options.addOption(returnOption);

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
                    System.out.println("Specified file does not exist: "+filePath);
                }

                var count = AddBooks(DbUtilities.LoadBookDb(), new FileInputStream(filePath));
                System.out.println("Number of new books added: "+count);
            }
            if (cmd.hasOption("co")){
                var filePath = cmd.getOptionValue("co");
                if(!FileUtilities.Exists(filePath)){
                    System.out.println("Specified file does not exist: "+filePath);
                }

                var bookDb = DbUtilities.LoadBookDb();
                var userDb = DbUtilities.LoadUserDb();
                var count = AddCheckouts(DbUtilities.LoadCheckoutDb(bookDb, userDb), new FileInputStream(filePath));
                System.out.println("Number of successful checkouts: "+count);
            }
            if (cmd.hasOption("ret")){
                var filePath = cmd.getOptionValue("ret");
                if(!FileUtilities.Exists(filePath)){
                    System.out.println("Specified file does not exist: "+filePath);
                }

                var bookDb = DbUtilities.LoadBookDb();
                var userDb = DbUtilities.LoadUserDb();
                var count = AddReturns(DbUtilities.LoadCheckoutDb(bookDb, userDb), new FileInputStream(filePath));
                System.out.println("Number of successful returns: "+count);
            }
        }
        catch (ParseException | IOException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(commandSyntex, options);
        }
    }

    public static int AddBooks(BookDb bookDb, InputStream inputStream){
        if(inputStream == null) return 0;

        var csvParser = new BookCsvParser(inputStream);

        var validEntries = new ArrayList<Book>();
        for (var book: csvParser.GetBooks()) {
            var id = bookDb.Add(book);
            if (id != null) validEntries.add(book);
        }
        if(validEntries.size()>0){
            var appendStream = DbUtilities.GetAppendStream(DbCommons.getBooksFilePath());
            if(AppendUtilities.AppendItems(validEntries, appendStream)) return validEntries.size();
            else return 0;
        }
        return 0;
    }

    public static int AddCheckouts(CheckoutDb checkoutDb, InputStream inputStream){
        if(inputStream == null) return 0;

        var csvParser = new CheckoutCsvParser(inputStream);

        var validEntries = checkoutDb.TryAddRange(csvParser.GetCheckouts());
        if(validEntries.size()>0){
            var appendStream = DbUtilities.GetAppendStream(DbCommons.getCheckoutsFilePath());
            AppendUtilities.AppendItems(validEntries, appendStream);
            var transactions = TransactionDb.GetCheckoutTransactions(validEntries);
            var transactionStream = DbUtilities.GetAppendStream(DbCommons.getTransactionsFilePath());
            AppendUtilities.AppendItems(transactions, transactionStream);
        }
        return validEntries.size();
    }

    public static int AddReturns(CheckoutDb checkoutDb, InputStream inputStream){
        if(inputStream == null) return 0;

        var csvParser = new ReturnCsvParser(inputStream);

        var validEntries = checkoutDb.ReturnRange(csvParser.GetReturnes());
        if(validEntries.size()>0){
            var writeStream = DbUtilities.GetWriteStream(DbCommons.getCheckoutsFilePath());
            AppendUtilities.Rewrite(CheckoutDb.HeaderLines, checkoutDb.GetAllCheckouts(), writeStream, false);
            var transactions = TransactionDb.GetReturnTransactions(validEntries);
            var transactionStream = DbUtilities.GetAppendStream(DbCommons.getTransactionsFilePath());
            AppendUtilities.AppendItems(transactions, transactionStream);
        }
        return validEntries.size();
    }
}
