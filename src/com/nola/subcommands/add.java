package com.nola.subcommands;

import com.nola.dataStructures.Book;
import com.nola.dataStructures.Bundle;
import com.nola.dataStructures.User;
import com.nola.databases.*;
import com.nola.parsers.BookCsvParser;
import com.nola.parsers.BundleCsvParser;
import com.nola.parsers.UserCsvParser;
import com.nola.utilities.FileUtilities;
import com.nola.utilities.PrintUtilities;
import org.apache.commons.cli.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class add {
    private static String commandSyntex = "nola add  -b [books CSV file] -B [bundles CSV file] -u [users CSV file]";
    public static void Run(String[] args) {
        Options options = new Options();

        Option bookOption = new Option("b", "book", true, "file with book records");
        bookOption.setRequired(false);
        options.addOption(bookOption);

        Option bundleOption = new Option("B", "bundle", true, "file with bundle records");
        bundleOption.setRequired(false);
        options.addOption(bundleOption);

        Option userOption = new Option("u", "user", true, "file with user records");
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
                var filePath = cmd.getOptionValue("book");
                if(!FileUtilities.Exists(filePath)){
                    PrintUtilities.PrintErrorLine("Specified file does not exist: "+filePath);
                }
                var appendStream = DbUtilities.GetAppendStream(DbCommons.getBooksFilePath());
                var count = AddBooks(DbUtilities.LoadBookDb(), new FileInputStream(filePath), appendStream, false);
                appendStream.close();
                System.out.println("Number of new books added: "+count);
            }
            if (cmd.hasOption("bundle")){
                var filePath = cmd.getOptionValue("bundle");
                if(!FileUtilities.Exists(filePath)){
                    PrintUtilities.PrintErrorLine("Specified file does not exist: "+filePath);
                }
                var appendStream = DbUtilities.GetAppendStream(DbCommons.getBundleFilePath());
                var count = AddBundles(DbUtilities.LoadBookDb(), DbUtilities.LoadBundleDb(), new FileInputStream(filePath), appendStream, false);
                appendStream.close();
                System.out.println("Number of new bundles added: "+count);
            }
            if (cmd.hasOption("user")){
                var filePath = cmd.getOptionValue("user");
                if(!FileUtilities.Exists(filePath)){
                    PrintUtilities.PrintErrorLine("Specified file does not exist: "+filePath);
                }
                var appendStream = DbUtilities.GetAppendStream(DbCommons.getUsersFilePath());
                var userParser = new UserCsvParser( new FileInputStream(filePath));
                var newUsers = userParser.GetUsers();
                var count = AddUsers(DbUtilities.LoadUserDb(), newUsers, appendStream, false);
                appendStream.close();
                System.out.println("Number of new users added: "+count);
            }
        }
        catch (ParseException | IOException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(commandSyntex, options);
        }
    }

    public static int AddUsers(UserDb userDb, ArrayList<User> users, OutputStream appendStream, boolean forceCommit) {
        if(users == null || users.size() ==0 || appendStream == null) return 0;

        var validEntries = new ArrayList<User>();
        for (var user : users) {
            var newUser = userDb.AddNewUser(user.Name, user.Role, user.Email, user.Phone);
            if (newUser != null) validEntries.add(newUser);
        }

        if(validEntries.size()>0){
            if(!forceCommit && !PromptUtilities.CommitValidEntries(validEntries)) return 0;

            AppendUtilities.AppendItems(validEntries, appendStream);
        }
        return validEntries.size();
    }

    public static int AddBundles(BookDb bookDb, BundleDb bundleDb, InputStream inputStream, OutputStream appendStream, boolean forceCommit) {
        if(inputStream == null || appendStream == null) return 0;

        var csvParser = new BundleCsvParser(inputStream);

        var validEntries = new ArrayList<Bundle>();
        for (var bundle: csvParser.GetBundles()) {
            var newBundle = bundleDb.TryAdd(bundle, bookDb);
            if (newBundle != null) validEntries.add(newBundle);
        }

        if(validEntries.size()>0){
            if(!forceCommit && !PromptUtilities.CommitValidEntries(validEntries)) return 0;

            AppendUtilities.AppendItems(validEntries, appendStream);
        }
        return validEntries.size();
    }

    public static int AddBooks(BookDb bookDb, InputStream inputStream, OutputStream appendStream, boolean forceCommit){
        if(inputStream == null || appendStream == null) return 0;

        var csvParser = new BookCsvParser(inputStream);

        var validEntries = new ArrayList<Book>();
        for (var book: csvParser.GetBooks()) {
            var newBook = bookDb.Add(book);
            if (newBook != null) validEntries.add(newBook);
        }

        if(validEntries.size()>0){
            if(!forceCommit && !PromptUtilities.CommitValidEntries(validEntries)) return 0;

            AppendUtilities.AppendItems(validEntries, appendStream);
        }
        return validEntries.size();
    }

}
