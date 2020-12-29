package com.nola.subcommands;

import com.nola.databases.*;
import com.nola.parsers.CheckoutCsvParser;
import com.nola.parsers.ReturnCsvParser;
import com.nola.utilities.FileUtilities;
import org.apache.commons.cli.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class co {
    private static String commandSyntex = "nola co -f [checkout CSV file]";
    public static void Run(String[] args) {
        Options options = new Options();

        Option checkoutOption = new Option("f", "file", true, "file with checkout records");
        checkoutOption.setRequired(false);
        options.addOption(checkoutOption);

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
                var appendStream = DbUtilities.GetAppendStream(DbCommons.getCheckoutsFilePath());
                var transactionStream = DbUtilities.GetAppendStream(DbCommons.getTransactionsFilePath());
                var count = AddCheckouts(DbUtilities.LoadCheckoutDb(bookDb, userDb), new FileInputStream(filePath),
                        appendStream, transactionStream);
                System.out.println("Number of successful checkouts: "+count);
            }
        }
        catch (ParseException | IOException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(commandSyntex, options);
        }
    }
    public static int AddCheckouts(CheckoutDb checkoutDb, InputStream inputStream,
                                   OutputStream appendStream, OutputStream transactionStream){
        if(inputStream == null) return 0;

        var csvParser = new CheckoutCsvParser(inputStream);

        var validEntries = checkoutDb.TryAddRange(csvParser.GetCheckouts());
        if(validEntries.size()>0){
            AppendUtilities.AppendItems(validEntries, appendStream);
            var transactions = TransactionDb.GetCheckoutTransactions(validEntries);
            AppendUtilities.AppendItems(transactions, transactionStream);
        }
        return validEntries.size();
    }


}
