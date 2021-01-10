package com.nola.subcommands;

import com.nola.dataStructures.Checkout;
import com.nola.databases.BookDb;
import com.nola.databases.CheckoutDb;
import com.nola.databases.DbUtilities;
import com.nola.databases.UserDb;
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
                var checkoutDb = DbUtilities.LoadCheckoutDb(bookDb, userDb);

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
                var checkoutDb = DbUtilities.LoadCheckoutDb(bookDb, userDb);
                var checkouts = GetUserCheckouts(id, userDb, checkoutDb);
                if (checkouts == null){
                    PrintUtilities.PrintInfoLine("No checkout found for user:"+id);
                }
                else {
                    for (var checkout: checkouts) {
                        PrintUtilities.PrintInfoLine(checkout.toString());
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

    public static Checkout GetBookCheckout(String id, BookDb bookDb, CheckoutDb checkoutDb){
        var book = bookDb.GetBook(id);
        if (book == null){
            PrintUtilities.PrintWarningLine("Invalid book id:"+id);
            return null;
        }

        return checkoutDb.GetCheckout(book.GetId());
    }

    public static ArrayList<Checkout> GetUserCheckouts(String id, UserDb userDb, CheckoutDb checkoutDb){
        var user = userDb.GetUser(id);
        if (user == null){
            PrintUtilities.PrintWarningLine("Invalid user id:"+id);
            return null;
        }
        return checkoutDb.GetUserCheckouts(user.Id);

    }

}
