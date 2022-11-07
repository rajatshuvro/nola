package com.nola.subcommands;

import com.nola.dataStructures.Checkout;
import com.nola.dataStructures.Transaction;
import com.nola.databases.*;
import com.nola.parsers.CheckoutCsvParser;
import com.nola.utilities.FileUtilities;
import com.nola.utilities.PrintUtilities;
import org.apache.commons.cli.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class co {
    private static String commandSyntex = "nola co -b [book checkout CSV file] -B [bundle checkout CSV file]";
    public static void Run(String[] args) {
        Options options = new Options();

        Option bookOption = new Option("b", "book", true, "file with book checkout records");
        bookOption.setRequired(false);
        options.addOption(bookOption);

        Option bundleOption = new Option("B", "bundle", true, "file with bundle checkout records");
        bundleOption.setRequired(false);
        options.addOption(bundleOption);

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

                var inputStream = new FileInputStream(filePath);
                if(inputStream == null) {
                    PrintUtilities.PrintWarningLine("No entries to checkout");
                    return ;
                }

                var csvParser = new CheckoutCsvParser(inputStream);

                var bookDb = DbUtilities.LoadBookDb();
                var userDb = DbUtilities.LoadUserDb();

                var appendStream = DbUtilities.GetAppendStream(DbCommons.getCheckoutsFilePath());
                var transactionStream = DbUtilities.GetAppendStream(DbCommons.getTransactionsFilePath());
                var count = CheckoutBooks(DbUtilities.LoadTransactionsDb(bookDb, userDb), csvParser.GetCheckouts(),
                        appendStream, transactionStream, false);
                appendStream.close();
                transactionStream.close();
                System.out.println("Number of successful checkouts: "+count);
            }
            if (cmd.hasOption("bundle")){
                var filePath = cmd.getOptionValue("bundle");
                if(!FileUtilities.Exists(filePath)){
                    System.out.println("Specified file does not exist: "+filePath);
                }

                var inputStream = new FileInputStream(filePath);
                if(inputStream == null) {
                    PrintUtilities.PrintWarningLine("No entries to checkout");
                    return ;
                }

                var csvParser = new CheckoutCsvParser(inputStream);

                var bookDb = DbUtilities.LoadBookDb();
                var userDb = DbUtilities.LoadUserDb();
                var bundleDb = DbUtilities.LoadBundleDb();

                var checkouts = GetBundleCheckouts(csvParser.GetCheckouts(), bundleDb);

                var appendStream = DbUtilities.GetAppendStream(DbCommons.getCheckoutsFilePath());
                var transactionStream = DbUtilities.GetAppendStream(DbCommons.getTransactionsFilePath());
                var count = CheckoutBooks(DbUtilities.LoadTransactionsDb(bookDb, userDb), checkouts,
                        appendStream, transactionStream, false);
                appendStream.close();
                transactionStream.close();
                System.out.println("Number of successful checkouts: "+count);
            }
        }
        catch (ParseException | IOException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(commandSyntex, options);
        }
    }

    public static ArrayList<Transaction> GetBundleCheckouts(ArrayList<Transaction> bundleCheckouts, BundleDb bundleDb) {
        var bookCheckouts = new ArrayList<Transaction>();
        for (var bundleCheckout: bundleCheckouts) {
            var bundle = bundleDb.GetBookBundle(bundleCheckout.BookId);
            if (bundle == null) {
                PrintUtilities.PrintWarningLine("Invalid bundle id:"+bundleCheckout.Id);
                continue;
            }
            for (var bookId: bundle.BookIds) {
                bookCheckouts.add(new Transaction(bookId, bundleCheckout.UserId, bundleCheckout.Date, Transaction.CheckoutTag));
            }
        }
        return bookCheckouts;
    }

    public static int CheckoutBooks(TransactionDb transactionDb, ArrayList<Transaction> checkouts,
                                    OutputStream appendStream, OutputStream transactionStream, boolean forceCommit){

        var validEntries = transactionDb.AddCheckouts(checkouts);
        if(validEntries.size()>0){
            if(!forceCommit && !PromptUtilities.CommitValidEntries(validEntries)) return 0;

            AppendUtilities.AppendItems(validEntries, appendStream);
            var transactions = TransactionDb.GetCheckoutTransactions(validEntries);
            AppendUtilities.AppendItems(transactions, transactionStream);
        }
        return validEntries.size();
    }


}
