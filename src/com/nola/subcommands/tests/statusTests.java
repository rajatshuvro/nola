package com.nola.subcommands.tests;

import com.nola.databases.CheckoutDb;
import com.nola.databases.TransactionDb;
import com.nola.subcommands.status;
import org.junit.jupiter.api.Test;

import static com.nola.testUtilities.testData.*;
import static org.junit.jupiter.api.Assertions.*;


public class statusTests {
    @Test
    public void StatusByBookTest(){
        var bookDb = GetBookDb();
        var userDb = GetUserDb();
        var transactionDb = new TransactionDb(GetCheckouts(), userDb, bookDb);

        var checkout = status.GetBookCheckout("7890788-(2)", bookDb, transactionDb);
        assertNotNull(checkout);

        checkout = status.GetBookCheckout("CAT12", bookDb, transactionDb);
        assertNotNull(checkout);

        checkout = status.GetBookCheckout("HAT12", bookDb, transactionDb);
        assertNull(checkout);

    }

    @Test
    public void StatusByUserTest(){
        var bookDb = GetBookDb();
        var userDb = GetUserDb();
        var transactionDb = new TransactionDb(GetCheckouts(), userDb, bookDb);

        var checkouts = status.GetUserCheckouts("name.1", userDb, transactionDb);
        assertNotNull(checkouts);
        assertEquals(2, checkouts.size());

        checkouts = status.GetUserCheckouts("rajat", userDb, transactionDb);
        assertNull(checkouts);
    }
}
