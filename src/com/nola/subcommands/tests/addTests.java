package com.nola.subcommands.tests;

import com.nola.databases.BookDb;
import com.nola.parsers.UserCsvParser;
import com.nola.subcommands.add;
import com.nola.testUtilities.TestStreams;
import com.nola.testUtilities.testData;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

public class addTests {
    @Test
    public void AddNewBooks(){
        var newBookCsvStream = TestStreams.GetBookCsvStream();
        var bookDb = new BookDb(testData.GetBooks());
        var originalCount = bookDb.Count();

        var appendStream = new ByteArrayOutputStream();
        add.AddBooks(bookDb, newBookCsvStream, appendStream, true);
        var newCount = bookDb.Count();

        assertEquals(3, newCount - originalCount);

    }

    @Test
    public void AddBundles(){
        var bookDb = testData.GetBookDb();
        var newBundleCsvStream = TestStreams.GetBundleCsvStream();
        var bundleDb = testData.GetBundleDb();
        var originalCount = bundleDb.Count();

        var appendStream = new ByteArrayOutputStream();
        add.AddBundles(bookDb, bundleDb, newBundleCsvStream, appendStream, true);
        var newCount = bundleDb.Count();

        assertEquals(2, newCount - originalCount);

    }

    @Test
    public void AddUsers(){
        var userDb = testData.GetUserDb();
        var newUsersCsvStream = TestStreams.GetNewUserCsvStream();
        var parser = new UserCsvParser(newUsersCsvStream);
        var appendStream = new ByteArrayOutputStream();

        var count = add.AddUsers(userDb, parser.GetUsers(), appendStream, true );

        assertEquals(3, count);
    }
}
