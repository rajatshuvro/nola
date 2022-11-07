package com.nola.subcommands;

import com.nola.dataStructures.Checkout;
import com.nola.dataStructures.Transaction;
import com.nola.databases.*;
import com.nola.parsers.FlatObjectParser;
import com.nola.utilities.PrintUtilities;
import org.apache.commons.cli.*;

import java.util.ArrayList;

public class status {
    private static String commandSyntex = "nola status  -b [books id] -u [users id]";
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
            if (cmd.hasOption("book")){
                var id = cmd.getOptionValue("book");
                var bookDb = DbUtilities.LoadBookDb();
                var userDb = DbUtilities.LoadUserDb();
                var checkoutDb = DbUtilities.LoadTransactionsDb(bookDb, userDb);

                var checkout = GetBookCheckout(id, bookDb, checkoutDb);
                if (checkout == null){
                    PrintUtilities.PrintInfoLine("No checkout found for book:"+id);
                }
                else PrintUtilities.PrintInfoLine(checkout.toString());
            }

            if (cmd.hasOption("user")){
                var id = cmd.getOptionValue("user");
                var userDb = DbUtilities.LoadUserDb();
                var bookDb = DbUtilities.LoadBookDb();
                var transactionDb = DbUtilities.LoadTransactionsDb(bookDb, userDb);
                var transactions = GetUserCheckouts(id, userDb, transactionDb);
                if (transactions == null || transactions.size()==0){
                    PrintUtilities.PrintInfoLine("No checkout found for user:"+id);
                }
                else {
                    for (var transaction: transactions) {
                        PrintUtilities.PrintInfoLine("Title:\t\t"+bookDb.GetBook(transaction.BookId).Title);
                        PrintUtilities.PrintInfoLine(transaction.toString());
                        PrintUtilities.PrintWarningLine(FlatObjectParser.RecordSeparator);
                    }

                }
            }
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(commandSyntex, options);
        }
    }

    public static Transaction GetBookCheckout(String id, BookDb bookDb, TransactionDb transactionDb){
        var book = bookDb.GetBook(id);
        if (book == null){
            PrintUtilities.PrintWarningLine("Invalid book id:"+id);
            return null;
        }

        return transactionDb.GetPendingCheckout(book.GetId());
    }

    public static ArrayList<Transaction> GetUserCheckouts(String id, UserDb userDb, TransactionDb transactionDb){
        var user = userDb.GetUser(id);
        if (user == null){
            PrintUtilities.PrintWarningLine("Invalid user id:"+id);
            return null;
        }
        return transactionDb.GetPendingCheckouts(user.Id);

    }

}
