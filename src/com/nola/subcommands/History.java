package com.nola.subcommands;

import com.nola.dataStructures.Transaction;
import com.nola.databases.BookDb;
import com.nola.databases.DbCommons;
import com.nola.databases.DbUtilities;
import com.nola.databases.TransactionDb;
import com.nola.parsers.FlatObjectParser;
import com.nola.utilities.FileUtilities;
import com.nola.utilities.PrintUtilities;
import org.apache.commons.cli.*;

import java.io.FileInputStream;
import java.io.IOException;

public class History {
    private static String commandSyntex = "nola hist -u [user id] -b [book id]";
    public static void Run(String[] args) {
        Options options = new Options();

        Option bookOption = new Option("b", "book", true, "book id/short id");
        bookOption.setRequired(false);
        options.addOption(bookOption);

        Option userOption = new Option("u", "user", true, "user id");
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
            if (cmd.hasOption("user")){
                var userId = cmd.getOptionValue("user");
                var bookDb = DbUtilities.LoadBookDb();
                var userDb = DbUtilities.LoadUserDb();
                var transactionDb = DbUtilities.LoadTransactionsDb(bookDb, userDb);

                PrintUserHistory(userId, bookDb, transactionDb);
            }

        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(commandSyntex, options);
        }

    }

    private static void PrintUserHistory(String userId, BookDb bookDb, TransactionDb transactionDb) {
        var transactions = transactionDb.GetUserActivity(userId);
        if (transactions == null){
            PrintUtilities.PrintInfoLine("No transactions found for user:" + userId);
        }
        else {
            PrintUtilities.PrintInfoLine("USER ID:"+ userId);
            PrintUtilities.PrintDelimiterLine(FlatObjectParser.RecordSeparator);
            for (var transaction: transactions) {

                if(! transaction.Type.equals(Transaction.CheckoutTag)) continue;

                var bookId = transaction.BookId;
                var book = bookDb.GetBook(bookId);
                PrintUtilities.PrintLine(book.Title);
            }
        }
    }
}
