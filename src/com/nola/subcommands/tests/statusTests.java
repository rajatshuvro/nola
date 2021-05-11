package com.nola.subcommands.tests;

import com.nola.databases.CheckoutDb;
import com.nola.subcommands.status;
import org.junit.jupiter.api.Test;

import static com.nola.testUtilities.testData.*;
import static org.junit.jupiter.api.Assertions.*;


public class statusTests {
    @Test
    public void StatusByBookTest(){
        var bookDb = GetBookDb();
        var userDb = GetUserDb();
        var checkoutDb = new CheckoutDb(GetCheckouts(), userDb, bookDb);

        var checkout = status.GetBookCheckout("7890788-(2)", bookDb, checkoutDb);
        assertNotNull(checkout);

        checkout = status.GetBookCheckout("CAT12", bookDb, checkoutDb);
        assertNotNull(checkout);

        checkout = status.GetBookCheckout("HAT12", bookDb, checkoutDb);
        assertNull(checkout);

    }

    @Test
    public void StatusByUserTest(){
        var bookDb = GetBookDb();
        var userDb = GetUserDb();
        var checkoutDb = new CheckoutDb(GetCheckouts(), userDb, bookDb);

        var checkouts = status.GetUserCheckouts("name.1", userDb, checkoutDb);
        assertNotNull(checkouts);
        assertEquals(2, checkouts.size());

        checkouts = status.GetUserCheckouts("rajat", userDb, checkoutDb);
        assertNull(checkouts);
    }
}
