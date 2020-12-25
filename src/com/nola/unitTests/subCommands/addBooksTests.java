package com.nola.unitTests.subCommands;

import com.nola.databases.BookDb;
import com.nola.subcommands.add;
import com.nola.unitTests.TestStreams;
import com.nola.unitTests.testData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class addBooksTests {
    @Test
    public void AddNewBooks(){
        var newBookCsvStream = TestStreams.GetBookCsvStream();
        var bookDb = new BookDb(testData.GetBooks());
        var originalCount = bookDb.Count();

        add.AddBooks(bookDb, newBookCsvStream);
        var newCount = bookDb.Count();

        assertEquals(3, newCount - originalCount);

    }


}
