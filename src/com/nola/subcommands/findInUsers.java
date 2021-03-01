package com.nola.subcommands;

import com.nola.NativeSearch.ISearchDocument;
import com.nola.NativeSearch.InvertedIndex;
import com.nola.databases.DbUtilities;

import java.util.ArrayList;

import static com.nola.subcommands.FindUtilities.*;

public class findInUsers {
    private static String commandSyntax = "nola find-u [search text]";

    public static void Run(String[] args){
        if(args.length <= 1) {
            System.out.println(commandSyntax);
            return;
        }
        String queryText = ConstructQuery(args);

        var bookDb = DbUtilities.LoadUserDb();
        var books = new ArrayList<ISearchDocument>(bookDb.GetAllUsers());

        InvertedIndex searchIndex = getInvertedIndex(books);
        ArrayList<String> results = search(queryText, books, searchIndex);

        OutputResults(results, "-----------------------Showing top 5 search results --------------------");

    }

}
