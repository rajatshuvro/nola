package com.nola.parsers.tests;

import com.nola.parsers.BookCsvParser;
import com.nola.testUtilities.TestStreams;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookCsvParserTests {
    @Test
    public void GetCsvBookRecord() throws IOException {
        var parser = new BookCsvParser(TestStreams.GetBookCsvStream());

        var books = parser.GetBooks();
        assertEquals(3, books.size());
    }
}
