package com.nola.unitTests.dataBases;

import com.nola.dataStructures.Checkout;
import com.nola.databases.CheckoutDb;
import com.nola.utilities.TimeUtilities;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static com.nola.unitTests.testData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CheckoutDbTests {
    @Test
    public void Checkout_already_in_circulation(){
        var chekoutDb = new CheckoutDb(GetCheckouts(), GetUserDb(), GetBookDb());

        assertTrue(chekoutDb.IsCheckedOut("7890788-(2)"));
    }

    @Test
    public void Checkout_with_short_id(){
        var chekoutDb = new CheckoutDb(null,  GetUserDb(), GetBookDb());
        chekoutDb.TryAddRange(GetCheckouts_shortId());

        assertTrue(chekoutDb.IsCheckedOut("7890788-(2)"));
    }
    @Test
    public void Checkout_new_book(){
        var chekoutDb = new CheckoutDb(GetCheckouts(),  GetUserDb(), GetBookDb());
        chekoutDb.TryAddRange(GetNewCheckouts());
        assertTrue(chekoutDb.IsCheckedOut("678564-(2)"));
    }

    @Test
    public void Checkout_new_book_without_userid(){
        var checkoutDb = new CheckoutDb(null, GetUserDb(), GetBookDb());
        checkoutDb.TryAddRange(GetCheckouts_without_userid());

        assertTrue(checkoutDb.IsCheckedOut("7890788-(2)"));
        assertFalse(checkoutDb.IsCheckedOut("678564-(1)"));
        assertEquals("name.4", checkoutDb.GetCheckout("456098-(1)").UserId);
    }

    @Test
    public void Checkout_invalid_user(){
        var chekoutDb = new CheckoutDb(GetCheckouts(), GetUserDb(), GetBookDb());
        var invalidCheckout = new Checkout("678564-(2)", "name.10","name10@onkur.com", TimeUtilities.parseGoogleDateTime("2020/09/30 3:21:27 PM MDT"), TimeUtilities.parseDate("2020-10-29") );

        assertFalse(chekoutDb.TryAdd(invalidCheckout));
    }

    @Test
    public void Checkout_invalid_book(){
        var chekoutDb = new CheckoutDb(GetCheckouts(), GetUserDb(), GetBookDb());
        var invalidCheckout = new Checkout("678564-(3)", "name.3","name1@onkur.com",TimeUtilities.parseGoogleDateTime("2020/09/30 3:21:27 PM MDT"), TimeUtilities.parseDate("2020-10-29") );

        assertFalse(chekoutDb.TryAdd(invalidCheckout));
    }

}
