package com.nola.subcommands;

import com.nola.utilities.PrintUtilities;

import java.util.ArrayList;
import java.util.Scanner;

public class PromptUtilities {
    public static<T> boolean CommitValidEntries(ArrayList<T> validEntries){
        PrintUtilities.PrintInfoLine(validEntries.size()+ "valid entries found.");
        PrintUtilities.PrintInfoLine("Would you like to commit the valid entries?(y) or (n)");
        Scanner in = new Scanner(System.in);
        String command = in.nextLine().toLowerCase();
        return (command.equals("y") || command.equals("yes"));
    }
}
