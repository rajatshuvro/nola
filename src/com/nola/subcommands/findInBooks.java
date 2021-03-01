package com.nola.subcommands;

import com.nola.parsers.FlatObjectParser;
import java.util.ArrayList;

public class findInBooks {
    private static String commandSyntax = "nola find-b [search text]";

    public static void Run(String[] args){
        if(args.length <= 1) {
            System.out.println(commandSyntax);
            return;
        }
        String queryText = ConstructQuery(args);

    }

    private static void OutputResults(ArrayList<String> results, String banner) {
        if (results.size() <= 0) return;

        System.out.println(banner);
        for (var result: results) {
            System.out.println(result);
            System.out.println(FlatObjectParser.RecordSeparator);
        }

        results.clear();
    }

    private static String ConstructQuery(String[] args) {
        var  queryText = args[1];
        for (var i=2; i < args.length; i++ ){
            queryText+= ' ' + args[i];
        }
        return queryText;
    }
}
