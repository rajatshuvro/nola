package com.nola.subcommands;

import com.nola.dataStructures.Book;
import com.nola.databases.BookDb;
import com.nola.databases.DbUtilities;
import com.nola.utilities.PrintUtilities;
import com.nola.utilities.TimeUtilities;
import org.apache.commons.cli.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class label {
    private static String commandSyntex = "nola label  -a [after entry date(YYYY-MM-DD)] -b [before entry date(YYYY-MM-DD)] -o [output file path]";

    public static void Run(String[] args){
        Options options = new Options();

        Option afterOption = new Option("a", "aft", true, "after entry date");
        afterOption.setRequired(false);
        options.addOption(afterOption);

        Option beforeOption = new Option("b", "bef", true, "before entry date");
        beforeOption.setRequired(false);
        options.addOption(beforeOption);

        Option outOption = new Option("o", "out", true, "output file path");
        outOption.setRequired(true);
        options.addOption(outOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter  = new HelpFormatter();
        CommandLine cmd;

        if(args.length==1) {
            formatter.printHelp(commandSyntex, options);
            return;
        }

        try {
            cmd = parser.parse(options, args);
            Date afterDate = cmd.hasOption('a')? TimeUtilities.parseDate(cmd.getOptionValue('a')): null;
            Date beforeDate = cmd.hasOption('b')?TimeUtilities.parseDate(cmd.getOptionValue('b')): null;

            var outFilePath  = cmd.getOptionValue("out");
            var outputStream = new FileOutputStream(outFilePath);
            var writer       = new OutputStreamWriter(outputStream);
            var bookDb = DbUtilities.LoadBookDb();
            WriteLabels(bookDb.GetAllBooks(), writer, afterDate, beforeDate);
            System.out.println("Printed out labels to output file.");
            writer.close();
        }
        catch (ParseException | IOException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(commandSyntex, options);
        }
    }

    private static void WriteLabels(Iterable<Book> books, OutputStreamWriter writer, Date afterDate, Date beforeDate) throws IOException {
        var bookList = GetSortedBooks(books, afterDate, beforeDate);

        PrintUtilities.PrintLine(bookList.size() + " book labels to print.");
        for (var book: bookList ) {
            writer.write(book.Title+'\n'+book.GetUserFriendlyId()+'\n'+book.ShortId+"\n\n");
        }
    }

    public static ArrayList<Book> GetSortedBooks(Iterable<Book> books, Date afterDate, Date beforeDate) {
        ArrayList<Book> bookList = new ArrayList<>();
        for(var book: books){
            if(afterDate!=null && book.EntryDate.before(afterDate))  continue;
            if(beforeDate !=null && book.EntryDate.after(beforeDate)) continue;
            bookList.add(book);
        }
        Collections.sort(bookList);
        return bookList;
    }
}
