package com.nola.subcommands;

import com.nola.dataStructures.Return;
import com.nola.databases.*;
import com.nola.parsers.ReturnCsvParser;
import com.nola.utilities.FileUtilities;
import org.apache.commons.cli.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ret {
    private static String commandSyntex = "nola ret -f [checkout CSV file]";
    public static void Run(String[] args) {
        Options options = new Options();

        Option returnOption = new Option("f", "file", true, "file with return records");
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
            if (cmd.hasOption("file")){
                var filePath = cmd.getOptionValue("file");
                if(!FileUtilities.Exists(filePath)){
                    System.out.println("Specified file does not exist: "+filePath);
                }

                var bookDb = DbUtilities.LoadBookDb();
                var userDb = DbUtilities.LoadUserDb();
                //var checkoutDb = DbUtilities.LoadCheckoutDb(bookDb, userDb);
                var transactionDb = DbUtilities.LoadTransactionsDb(bookDb, userDb);
                var bundleDb = DbUtilities.LoadBundleDb();
                var writeStream = DbUtilities.GetWriteStream(DbCommons.getCheckoutsFilePath());
                var transactionStream = DbUtilities.GetAppendStream(DbCommons.getTransactionsFilePath());
                var count = AddReturns(transactionDb, bundleDb, new FileInputStream(filePath),
                        transactionStream, false);
                writeStream.close();
                transactionStream.close();
                System.out.println("Number of successful returns: "+count);
            }
        }
        catch (ParseException | IOException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(commandSyntex, options);
        }
    }
    public static int AddReturns(TransactionDb transactionDb, BundleDb bundleDb, InputStream inputStream,
                                 OutputStream transactionStream, boolean forceCommit){
        if(inputStream == null) return 0;
        var existingCheckouts = transactionDb.GetPendingCheckouts();
        var csvParser = new ReturnCsvParser(inputStream);

        var expandedEntries = ExpandBundleEntries(csvParser.GetReturns(), bundleDb);
        var validEntries = transactionDb.Return(expandedEntries);
        if(!forceCommit && !PromptUtilities.CommitValidEntries(validEntries)) {
            return 0;
        }

        var transactions = TransactionDb.GetReturnTransactions(validEntries);
        AppendUtilities.AppendItems(transactions, transactionStream);
        return validEntries.size();
    }

    private static ArrayList<Return> ExpandBundleEntries(ArrayList<Return> returns, BundleDb bundleDb) {
        if (bundleDb == null) return returns;

        var returnEntires = new ArrayList<Return>();
        for (var record:
             returns) {
            if (bundleDb.GetBookBundle(record.BookId) == null) {
                returnEntires.add(record);
                continue;
            }
            //we have a bundle
            for (var bookId:bundleDb.GetBookBundle(record.BookId).BookIds) {
                returnEntires.add(new Return(bookId, record.DateTime));
            }
        }
        return returnEntires;
    }
}
