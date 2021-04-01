package com.nola.testUtilities;

import com.nola.dataStructures.*;
import com.nola.databases.BookDb;
import com.nola.databases.BundleDb;
import com.nola.databases.TransactionDb;
import com.nola.databases.UserDb;
import com.nola.utilities.TimeUtilities;

import java.util.ArrayList;

public class testData {
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

    public static BundleDb GetBundleDb(){
        var bundles = new ArrayList<Bundle>();
        bundles.add(new Bundle("BUN01", "Bundle one", new String[]{"7890788-(2)", "678564-(1)"}, TimeUtilities.GetCurrentTime()));
        bundles.add(new Bundle("DUN02", "Bundle two", new String[]{"678564-(2)", "456098-(1)"}, TimeUtilities.GetCurrentTime()));
        bundles.add(new Bundle("GUN03", "Bundle three", new String[]{"7890788-(1)"}, TimeUtilities.GetCurrentTime()));

        return new BundleDb(bundles);
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

    public static ArrayList<User> GetUsers(){
        var users= new ArrayList<User>();
        users.add(User.Create("123", "Radia Khondokar","Student", "radia.khon@onkur.com", "858-283-0192"));
        users.add(User.Create("234", "Titu Mitra","Student", "titu.mitra@onkur.com", "858-283-8192"));
        users.add(User.Create("345", "Nandan Das","Citizen", "nandan.das@onkur.com", "858-293-8192"));
        users.add(User.Create("456", "Bhobo Ghure","Pagol", "akasher@thikana.ear", "858-93-8192"));
        return users;
    }

    public static BookDb GetBookDb() {
        var books = new ArrayList<Book>();
        books.add(Book.Create(7890788L,"Binoy Bormon", "Panite Jhopat Jhopat", "Sisimpur",
                2016,16, 5, "Fiction", 3, 2, null, null, "CAT12"));
        books.add(Book.Create(678564,"Binoy Bormon", "Panite Jhopat Jhopat", "Sisimpur",
                2016,16, 5, "Fiction", 3, 1, null, null, "BAT12"));
        books.add(Book.Create(678564,"Binoy Bormon", "Panite Jhopat Jhopat", "Sisimpur",
                2016,16, 5, "Fiction", 3, 2, null, null, "DOG99"));
        books.add(Book.Create(456098,"Binoy Bormon", "Panite Jhopat Jhopat", "Sisimpur",
                2016,16, 5, "Fiction", 3, 1, null, null, "PIG07"));
        books.add(Book.Create(456098,"Binoy Bormon", "Panite Jhopat Jhopat", "Sisimpur",
                2016,16, 5, "Fiction", 3, 2, null, null, "GIP09"));

        return new BookDb(books);
    }

    public static UserDb GetUserDb() {
        var users = new ArrayList<User>();
        users.add(User.Create("name.1", "name1", User.StudentRoleTag, "name1@onkur.com", "4568932678"));
        users.add(User.Create("name.2", "name2", User.StudentRoleTag, "name2@onkur.com", "4568732678"));
        users.add(User.Create("name.3", "name2", User.VolunteerRoleTag, "name3@onkur.com", "4568732676"));
        users.add(User.Create("name.4", "name3", User.VolunteerRoleTag, "name3@onkur.com", "4568732676"));
        return new UserDb(users);
    }

    public static ArrayList<Checkout> GetCheckouts(){
        var checkouts = new ArrayList<Checkout>();
        checkouts.add(new Checkout("7890788-(2)", "name.1",  TimeUtilities.parseGoogleDateTime("2020/09/30 3:20:16 PM MDT"), TimeUtilities.parseDate("2020-10-25")));
        checkouts.add(new Checkout("678564-(1)", "name.2", TimeUtilities.parseGoogleDateTime("2020/09/30 3:21:27 PM MDT"), TimeUtilities.parseDate("2020-10-29")));
        checkouts.add(new Checkout("456098-(1)", "name.3", TimeUtilities.parseGoogleDateTime("2020/09/30 3:22:04 PM MDT"), TimeUtilities.parseDate("2020-10-28")));
        checkouts.add(new Checkout("7890788-(2)", "name.1",TimeUtilities.parseGoogleDateTime("2020/09/30 3:23:30 PM MDT"), TimeUtilities.parseDate("2020-10-26")));

        return checkouts;
    }

    public static ArrayList<Checkout> GetCheckouts_shortId(){
        var checkouts = new ArrayList<Checkout>();
        checkouts.add(new Checkout("CAT12", "name.1", TimeUtilities.parseGoogleDateTime("2020/09/30 3:20:16 PM MDT"), TimeUtilities.parseDate("2020-10-25")));
        checkouts.add(new Checkout("BAT12", "name.2", TimeUtilities.parseGoogleDateTime("2020/09/30 3:21:27 PM MDT"), TimeUtilities.parseDate("2020-10-29")));
        checkouts.add(new Checkout("PIG07", "name.3", TimeUtilities.parseGoogleDateTime("2020/09/30 3:22:04 PM MDT"), TimeUtilities.parseDate("2020-10-28")));
        checkouts.add(new Checkout("CAT12", "name.1", TimeUtilities.parseGoogleDateTime("2020/09/30 3:23:30 PM MDT"), TimeUtilities.parseDate("2020-10-26")));

        return checkouts;
    }

    public static ArrayList<Checkout> GetNewCheckouts(){
        var checkouts = new ArrayList<Checkout>();
        checkouts.add(new Checkout("678564-(2)", "name.3", TimeUtilities.parseGoogleDateTime("2020/09/30 3:21:27 PM MDT"), TimeUtilities.parseDate("2020-10-29")));

        return checkouts;
    }

    public static BookDb GetBookDb_reduced() {
        var books = new ArrayList<Book>();
        books.add(Book.Create(7890788L,"Binoy Bormon", "Panite Jhopat Jhopat", "Sisimpur",
                2016,16, 5, "Fiction", 3, 2, null, null, null));
        books.add(Book.Create(456098,"Binoy Bormon", "Panite Jhopat Jhopat", "Sisimpur",
                2016,16, 5, "Fiction", 3, 1, null, null, null));

        return new BookDb(books);
    }

    public static UserDb GetUserDb_reduced() {
        var users = new ArrayList<User>();
        users.add(User.Create("234", "name1", User.StudentRoleTag, "name1@onkur.com", "4568932678"));
        users.add(User.Create("123", "name2", User.StudentRoleTag, "name2@onkur.com", "4568732678"));
        return new UserDb(users);
    }
    public static BookDb GetBookDb_transactionTest() {
        var books = new ArrayList<Book>();
        books.add(Book.Create(7890788L,"Binoy Bormon", "Panite Jhopat Jhopat", "Sisimpur",
                2016,16, 5, "Fiction", 3, 2, null, null, null));
        books.add(Book.Create(678564,"Binoy Bormon", "Panite Jhopat Jhopat", "Sisimpur",
                2016,16, 5, "Fiction", 3, 1, null, null, null));
        books.add(Book.Create(456098,"Binoy Bormon", "Panite Jhopat Jhopat", "Sisimpur",
                2016,16, 5, "Fiction", 3, 1, null, null, null));

        return new BookDb(books);
    }

    public static UserDb GetUserDb_transactionTest() {
        var users = new ArrayList<User>();
        users.add(User.Create("234", "name1", User.StudentRoleTag, "name1@onkur.com", "4568932678"));
        users.add(User.Create("123", "name2", User.StudentRoleTag, "name2@onkur.com", "4568732678"));
        users.add(User.Create("345", "name2", User.VolunteerRoleTag, "name3@onkur.com", "4568732676"));
        return new UserDb(users);
    }
    public static ArrayList<Transaction> GetTransactions(){
        var transactions = new ArrayList<Transaction>();
        transactions.add(Transaction.Create("7890788-(2)", "234", TimeUtilities.parseDateTime("2019-09-13 10:30:31"), Transaction.CheckoutTag));
        transactions.add(Transaction.Create("678564-(1)", "123", TimeUtilities.parseDateTime("2019-10-15 11:01:22"), Transaction.CheckoutTag));
        transactions.add(Transaction.Create("456098-(1)", "345", TimeUtilities.parseDateTime("2019-11-03 10:33:22"), Transaction.CheckoutTag));
        transactions.add(Transaction.Create("7890788-(2)", "234", TimeUtilities.parseDateTime("2019-11-13 10:30:25"), Transaction.ReturnTag));

        return transactions;
    }

    public static TransactionDb GetTransactionDb(){
        var transactions = GetTransactions();
        return new TransactionDb(transactions, GetUserDb_transactionTest(), GetBookDb_transactionTest());
    }

}
