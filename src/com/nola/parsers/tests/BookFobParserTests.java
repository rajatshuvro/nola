package com.nola.parsers.tests;

import com.nola.parsers.BookParser;
import com.nola.testUtilities.TestStreams;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BookFobParserTests {
    @Test
    public void ParseBook() throws IOException {
        var stream = TestStreams.GetBooksStream();
        var bookParser = new BookParser(stream);
        stream.close();
        var books = bookParser.GetBooks();
        var count = books.size();
        assertEquals(4, count);
        assertEquals("LILDD", books.get(0).ShortId);
        assertNull(books.get(2).ExpiryDate);
    }


    @Test
    public void ParseAntiqueBooks() throws IOException{
        var stream = TestStreams.GetAntiqueBooksStream();
        var bookParser = new BookParser(stream);
        var books = bookParser.GetBooks();
        stream.close();
        var count = books.size();
        assertEquals(2, count);
        assertEquals(855609383, books.get(0).Isbn);
        assertEquals(640080796, books.get(1).Isbn);

    }

}
