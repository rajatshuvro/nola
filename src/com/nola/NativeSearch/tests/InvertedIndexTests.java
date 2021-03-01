package com.nola.NativeSearch.tests;

import com.nola.NativeSearch.InvertedIndex;
import com.nola.NativeSearch.JaroWinkler;
import com.nola.NativeSearch.SmithWaterman;
import com.nola.dataStructures.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class InvertedIndexTests {
    public static ArrayList<Book> GetBooks() {
        var books = new ArrayList<Book>();
        books.add(Book.Create(7890788,"Rajat Shuvro Roy","Amar Baba","Bonosree books and co",
                2015, 5, 5, "General", 4,1, null, null, null));
        books.add(Book.Create(7890788,"Rajat Shuvro Roy","Amar Baba","Bonosree books and co",
                2015, 5, 5, "General", 4,2, null, null, null));
        books.add(Book.Create(678564,"Saber Nabil","Bhua Bhalobasha","Dakkhin Khan Publishers",
                2017, 10, 10, "Fiction", 5,1, null, null, null));
        books.add(Book.Create(456098,"Nanda Mitra","Robindra Prem","Bakura Publishers",
                2018, 15, 12, "Fiction", 6,1, null, null, null));
        books.add(Book.Create(9789849195290l,"Binoy bormon","Panite jopath jopath","Sisemi workshop",
                2017, 16, 10, "Science", 5,1, null, null, null));
        books.add(Book.Create(421201710l,"Limia dewan","Phritibir sobcheye Boro pitha","Brac",
                2017, 15, 12, "General", 6,1, null, null, null));

        return books;
    }

    @Test
    public void JWscoringTest() throws IOException {
        var books = GetBooks();

        var invertedIndex = new InvertedIndex(new JaroWinkler());
        for(var book:books){
            invertedIndex.Add(book.GetContent());
        }

        var topDocs = invertedIndex.Search("amar bonosre");

        Assertions.assertEquals(2, topDocs.length);
        Assertions.assertArrayEquals(new int[]{0,1,}, topDocs);

        topDocs = invertedIndex.Search("jhopat jhopat");
        Assertions.assertEquals(1, topDocs.length);
        Assertions.assertArrayEquals(new int[]{4}, topDocs);
    }
    @Test
    public void SWscoringTest() throws IOException {
        var books = GetBooks();

        var invertedIndex = new InvertedIndex(new SmithWaterman());
        for(var book:books){
            invertedIndex.Add(book.GetContent());
        }

        var topDocs = invertedIndex.Search("amar bonosre");

        Assertions.assertEquals(2, topDocs.length);
        Assertions.assertArrayEquals(new int[]{0,1,}, topDocs);

        topDocs = invertedIndex.Search("jhopat jhopat");
        Assertions.assertEquals(1, topDocs.length);
        Assertions.assertArrayEquals(new int[]{4}, topDocs);
    }

}
