package com.nola.databases;


public class MainDb {
    public BookDb BookDb;
    public UserDb UserDb;
    public CheckoutDb CheckoutDb;
    public TransactionDb TransactionDb;

    public MainDb(BookDb bookDb, UserDb userDb, CheckoutDb checkoutDb, TransactionDb transactionDb){
        BookDb = bookDb;
        UserDb = userDb;
        CheckoutDb = checkoutDb;
        TransactionDb = transactionDb;
    }


}
