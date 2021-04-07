package com.nola;

import com.nola.subcommands.*;
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
            case "ret":
                ret.Run(args);
                break;
            case "status":
                status.Run(args);
                break;
            case "label":
                label.Run(args);
                break;
            case "rotate":
                rotate.Run(args);
                break;
            case "find-b":
                findInBooks.Run(args);
                break;
            case "find-u":
                findInUsers.Run(args);
                break;
            case "help":
                PrintMainMenu();
                break;
            default:
                System.out.println("Unrecognized command: "+subCommand+"\nType \"help\" for the help menu");
        }

    }

    private static void PrintMainMenu() {
        PrintUtilities.PrintInfoLine("nola sub-command [options] ");
        PrintUtilities.PrintInfoLine("sub-commands:");
        PrintUtilities.PrintInfoLine("\tadd            (add new books or users)");
        PrintUtilities.PrintInfoLine("\tco             (checkout books)");
        PrintUtilities.PrintInfoLine("\tret            (return books)");
        PrintUtilities.PrintInfoLine("\tstatus         (checkout status)");
        PrintUtilities.PrintInfoLine("\tlabel          (print out labels)");
        PrintUtilities.PrintInfoLine("\trotate         (rotate bundles for classes)");
        PrintUtilities.PrintInfoLine("\tfind-b         (search in books)");
        PrintUtilities.PrintInfoLine("\tfind-u         (search in users)");
        PrintUtilities.PrintInfoLine("\thelp           (print this menu)");
        PrintUtilities.PrintInfoLine("\t[Type sub-command to get detailed help]");
    }
}
