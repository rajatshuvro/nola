package com.nola;

import com.nola.subcommands.add;
import com.nola.subcommands.co;
import com.nola.utilities.PrintUtilities;

public class Main {

    public static void main(String[] args) {
        if(args.length==0) {
            PrintMainMenu();
            return;
        }

        String subCommand = args[0];
        switch (subCommand){
            case "add":
                add.Run(args);
                break;
            case "co":
                co.Run(args);
                break;
//            case "$":
//            case "search":
//                TokenSearch.Run(subArgs, dataProvider);
//                break;
//            case "label":
//                LabelPrinter.Run(subArgs, dataProvider.BookDb);
//                break;
              case "help":
                PrintMainMenu();
                break;
            default:
                System.out.println("Unrecognized command: "+subCommand+"\nType \"help\" for the help menu");
        }

    }

    private static void PrintMainMenu() {
        PrintUtilities.PrintInfoLine("ola sub-command [options] ");
        PrintUtilities.PrintInfoLine("sub-commands:");
        PrintUtilities.PrintInfoLine("\tadd            (add new books or users)");
        PrintUtilities.PrintInfoLine("\tcs             (checkout status)");
        PrintUtilities.PrintInfoLine("\tsearch/$       (search book and user records)");
        PrintUtilities.PrintInfoLine("\tfilter         (filter book database by genre, level, etc fields)");
        PrintUtilities.PrintInfoLine("\tlabel          (print out book titles and Ids)");
        PrintUtilities.PrintInfoLine("\thelp           (print this menu)");
        PrintUtilities.PrintInfoLine("\t[Type sub-command to get detailed help]");
    }
}
