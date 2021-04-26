package com.nola.subcommands;

import com.nola.analytics.BundleRotator;
import com.nola.databases.DbUtilities;
import com.nola.parsers.FlatObjectParser;
import com.nola.utilities.PrintUtilities;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.HashMap;

public class rotate {
    private static String commandSyntex = "nola rotate  -c (optional: class name)";
    public static void Run(String[] args){
        Options options = new Options();

        Option className = new Option("c", "class", true, "class name");
        className.setRequired(false);
        options.addOption(className);

        Option allClasses = new Option("a", "all", false, "rotation for all classes");
        allClasses.setRequired(false);
        options.addOption(allClasses);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter  = new HelpFormatter();
        CommandLine cmd;

        if(args.length == 0) {
            formatter.printHelp(commandSyntex, options);
            return;
        }

        try{
            cmd = parser.parse(options, args);
            var classBundles = DbUtilities.GetClassBundles();
            var userDb = DbUtilities.LoadUserDb();
            var bookDb = DbUtilities.LoadBookDb();
            var bundleDb = DbUtilities.LoadBundleDb();
            var transactionsDb = DbUtilities.LoadTransactionsDb(bookDb, userDb);

            var rotator = new BundleRotator(classBundles);
            for (var classBundle: classBundles) {
                var assignment = rotator.Rotate(classBundle.ClassId, bookDb, bundleDb, transactionsDb);
                PrintBundleAssignments(classBundle.ClassId,assignment);
            }

        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(commandSyntex, options);
        }
    }

    private static void PrintBundleAssignments(String classId, HashMap<String, String> assignment) {
        PrintUtilities.PrintInfoLine(FlatObjectParser.RecordSeparator);
        PrintUtilities.PrintInfoLine("Class:"+classId);
        for (var userId: assignment.keySet()) {
            var output = String.format("%20s %5s %10s", userId,"=>", assignment.get(userId));
            PrintUtilities.PrintLine(output);
        }
        PrintUtilities.PrintInfoLine(FlatObjectParser.RecordSeparator);
    }
}
