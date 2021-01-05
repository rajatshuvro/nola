package com.nola.subcommands;

import com.nola.databases.*;
import com.nola.parsers.ReturnCsvParser;
import com.nola.utilities.FileUtilities;
import org.apache.commons.cli.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
                var writeStream = DbUtilities.GetWriteStream(DbCommons.getCheckoutsFilePath());
                var transactionStream = DbUtilities.GetAppendStream(DbCommons.getTransactionsFilePath());
                var count = AddReturns(DbUtilities.LoadCheckoutDb(bookDb, userDb), new FileInputStream(filePath),
                        writeStream, transactionStream);
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
    public static int AddReturns(CheckoutDb checkoutDb, InputStream inputStream,
                                 OutputStream writeStream, OutputStream transactionStream){
        if(inputStream == null) return 0;

        var csvParser = new ReturnCsvParser(inputStream);

        var validEntries = checkoutDb.ReturnRange(csvParser.GetReturnes());
        if(validEntries.size()>0){
            AppendUtilities.Rewrite(CheckoutDb.HeaderLines, checkoutDb.GetAllCheckouts(), writeStream, false);
            var transactions = TransactionDb.GetReturnTransactions(validEntries);
            AppendUtilities.AppendItems(transactions, transactionStream);
        }
        return validEntries.size();
    }
}
