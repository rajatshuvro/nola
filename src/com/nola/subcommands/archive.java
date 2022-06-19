package com.nola.subcommands;

import com.nola.databases.AppendUtilities;
import com.nola.databases.DbCommons;
import com.nola.databases.DbUtilities;
import com.nola.utilities.PrintUtilities;
import org.apache.commons.cli.*;

public class archive {
    private static String commandSyntex = "nola arc -b [obsolete books] -t [old transactions]";

    public static void Run(String[] args){
        Options options = new Options();

        Option bookOption = new Option("b", "book", false, "archive books");
        bookOption.setRequired(false);
        options.addOption(bookOption);

        Option userOption = new Option("t", "tran", false, "archive transactions");
        userOption.setRequired(false);
        options.addOption(userOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;
        if (args.length == 1) {
            formatter.printHelp(commandSyntex, options);
            return;
        }
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("book")){
                PrintUtilities.PrintInfoLine("Archiving obsolete books....");
            }
            if (cmd.hasOption("tran")){
                PrintUtilities.PrintInfoLine("Archiving old transactions....");

                var bookDb = DbUtilities.LoadBookDb();
                var userDb = DbUtilities.LoadUserDb();
                var transactionDb = DbUtilities.LoadTransactionsDb(bookDb, userDb);

                var oldTransactions = transactionDb.GetOldTransactions();
                var count = oldTransactions.size();
                PrintUtilities.PrintInfoLine("Number of old transactions:"+count);

                if (!PromptUtilities.CommitValidEntries(oldTransactions)) return;
                PrintUtilities.PrintInfoLine("Archiving transactions");
                var archiveStream = DbUtilities.GetAppendStream(DbCommons.getTransactionArchiveFilePath());
                AppendUtilities.AppendItems(oldTransactions, archiveStream);

                var latestTransactions = transactionDb.GetLatestTransactions();
                PrintUtilities.PrintWarningLine("About to re-write Transactions.fob !!");
                if (!PromptUtilities.CommitValidEntries(latestTransactions.size())) return;
                var transactionStream = DbUtilities.GetWriteStream(DbCommons.getTransactionsFilePath());
                AppendUtilities.Rewrite(transactionDb.HeaderLines, latestTransactions, transactionStream, false);
            }
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(commandSyntex, options);
        }
    }
}
