package com.nola.databases.tests;

import com.nola.databases.BookDb;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.nola.testUtilities.testData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BookDbTests {
    @Test
    public void AddBooks() {
        var bookDb = new BookDb(GetBooks());

        for(var book: GetNewBooks())
            bookDb.Add(book);

        assertEquals(3, bookDb.GetCopyCount(7890788));
        assertEquals(3, bookDb.GetCopyCount(678564));
        assertEquals(2, bookDb.GetCopyCount(456098));

    }

    @Test
    public void AddBooks_details_mismatch() throws IOException {
        var bookDb = new BookDb(GetBooks());

        for(var book: GetMismatchingBook())
            bookDb.Add(book);

        assertEquals(5, bookDb.Count());
    }

    @Test
    public void SearchByIsbn() throws IOException{
        var bookDb = new BookDb(GetBooks());
        assertEquals(2, bookDb.GetBooks(7890788L).size());
        assertEquals(0, bookDb.GetBooks(12345678L).size());
    }
    @Test
    public void LatestCopyNumber() throws IOException{
        var bookDb = new BookDb(GetBooks());
        assertEquals(2, bookDb.GetCopyCount(7890788L));
        assertEquals(1, bookDb.GetCopyCount(678564L));
        assertEquals(0, bookDb.GetCopyCount(123456L));
    }
    @Test
    public void Search(){
        var bookDb = new BookDb(GetBooks());
        var result = bookDb.Filter("Fiction", -1);
        assertEquals(2, result.size());

        result = bookDb.Filter(null, 4);
        assertEquals(2, result.size());

        result = bookDb.Filter("Fiction", 4);
        assertEquals(0, result.size());

        result = bookDb.Filter("Fiction", 5);
        assertEquals(1, result.size());
    }

    @Test
    public void IsBookIdValid(){
        assertTrue(BookDb.IsValidId("1223456-(3)"));
        assertFalse(BookDb.IsValidId("12345667"));
        assertFalse(BookDb.IsValidId("12345-(0)"));
        assertFalse(BookDb.IsValidId("12345-(-1)"));
        assertFalse(BookDb.IsValidId("0-(3)"));
    }

}

