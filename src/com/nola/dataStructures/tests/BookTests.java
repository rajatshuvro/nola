package com.nola.dataStructures.tests;

import com.nola.dataStructures.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTests {
    @Test
    public void GetBookId(){
        Book book = Book.Create(9_789_849_195_290L,"Binoy Bormon", "Panite Jhopat Jhopat", "Sisimpur",
                2016,16, 5, "Fiction", 3, 1, null, null, "learning to swim");

        Book book2 = Book.Create(9_789_849_195_290L,"Binoy Bormon", "Panite Jhopat Jhopat", "Sisimpur",
                2016,16, 5, "Fiction/Social", 3, 2, null, null, "learning to swim");

        //assertEquals("9789849195290-FIC-3-(1)", book.GetUserFriendlyId());
        assertEquals("9789849195290-(1)", book.GetId());
        //assertEquals("9789849195290-FIC-SOC-3-(2)", book2.GetUserFriendlyId());
    }

    @Test
    public void GenerateIsbn(){
        var isbn = Book.GenerateIsbn("Chotoder Ramayan", "Upendrakishore Roy Choudhury"
                ,"Kalighat prokashoni", 1929, 35);

        assertEquals(232192935, isbn);

        isbn = Book.GenerateIsbn("Borno porichoy", "Ishwarchandra Viddyasagar"
                ,"Bengal publishers", 1881, 20);

        assertEquals(222188120, isbn);

    }
}
