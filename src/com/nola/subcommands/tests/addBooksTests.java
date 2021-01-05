package com.nola.subcommands.tests;

import com.nola.databases.BookDb;
import com.nola.subcommands.add;
import com.nola.testUtilities.TestStreams;
import com.nola.testUtilities.testData;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

public class addBooksTests {
    @Test
    public void AddNewBooks(){
        var newBookCsvStream = TestStreams.GetBookCsvStream();
        var bookDb = new BookDb(testData.GetBooks());
        var originalCount = bookDb.Count();

        var appendStream = new ByteArrayOutputStream();
        add.AddBooks(bookDb, newBookCsvStream, appendStream);
        var newCount = bookDb.Count();

        assertEquals(3, newCount - originalCount);

    }
}
