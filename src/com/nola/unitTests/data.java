package com.nola.unitTests;

import com.nola.dataStructures.Book;

import java.util.ArrayList;

public class data {
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

        return books;
    }


    public static ArrayList<Book> GetNewBooks() {
        var books = new ArrayList<Book>();
        books.add(Book.Create(7890788,"Rajat Shuvro Roy","Amar Baba","Bonosree books and co",
                2015, 5, 5, "General", 4,-1, null, null, null));
        books.add(Book.Create(678564,"Saber Nabil","Bhua Bhalobasha","Dakkhin Khan Publishers",
                2017, 10, 10, "Fiction", 5,-1, null, null, null));
        books.add(Book.Create(678564,"Saber Nabil","Bhua Bhalobasha","Dakkhin Khan Publishers",
                2017, 10, 10, "Fiction", 5,-1, null, null, null));
        books.add(Book.Create(456098,"Nanda Mitra","Robindra Prem","Bakura Publishers",
                2018, 15, 12, "Fiction", 6,-1, null, null, null));

        return books;
    }

    public static ArrayList<Book> GetMismatchingBook() {
        var books = new ArrayList<Book>();
        books.add(Book.Create(7890788,"Rajat Shuvro Roy","Amar Baba","Bonosree books and co",
                2015, 5, 5, "Fiction", 4,-1, null, null, null));
        books.add(Book.Create(678564,"Saber Nabil","Bhua Bhalobasha","Dakkhin Khan Publishers",
                2017, 10, 10, "Fiction", 6,-1, null, null, null));
        books.add(Book.Create(678564,"Saber Nabil","Bhua Bhalobasha","Dakkhin Khan Publishers",
                2017, 10, 10, "Fiction", 5,-1, null, null, null));
        books.add(Book.Create(456098,"Nandana Mitra","Robindra Prem","Bakura Publishers",
                2018, 15, 12, "Fiction", 6,-1, null, null, null));

        return books;
    }

}
