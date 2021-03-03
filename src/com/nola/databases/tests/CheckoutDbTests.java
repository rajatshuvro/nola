package com.nola.databases.tests;

import com.nola.dataStructures.Checkout;
import com.nola.dataStructures.Return;
import com.nola.databases.AppendUtilities;
import com.nola.databases.CheckoutDb;
import com.nola.parsers.CheckoutParser;
import com.nola.parsers.ReturnCsvParser;
import com.nola.testUtilities.TestStreams;
import com.nola.utilities.TimeUtilities;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.nola.testUtilities.testData.*;
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
    public void Checkout_invalid_user(){
        var chekoutDb = new CheckoutDb(GetCheckouts(), GetUserDb(), GetBookDb());
        var invalidCheckout = new Checkout("678564-(2)", "name.10", TimeUtilities.parseGoogleDateTime("2020/09/30 3:21:27 PM MDT"), TimeUtilities.parseDate("2020-10-29") );

        assertNull(chekoutDb.TryAdd(invalidCheckout));
    }

    @Test
    public void Checkout_invalid_book(){
        var chekoutDb = new CheckoutDb(GetCheckouts(), GetUserDb(), GetBookDb());
        var invalidCheckout = new Checkout("678564-(3)", "name.3",TimeUtilities.parseGoogleDateTime("2020/09/30 3:21:27 PM MDT"), TimeUtilities.parseDate("2020-10-29") );

        assertNull(chekoutDb.TryAdd(invalidCheckout));
    }

    @Test
    public void Bulk_return(){
        var csvParser = new ReturnCsvParser(TestStreams.GetReturnCsvStream());
        var checkoutDb = new CheckoutDb(GetCheckouts(), GetUserDb(), GetBookDb());

        for (var returnRecord: csvParser.GetReturnes()) {
            assertNotNull(checkoutDb.Return(returnRecord));
        }

        assertNull(checkoutDb.Return(new Return("1234567-(3)", TimeUtilities.GetCurrentTime())));
    }

    @Test
    public void Bulk_return_shortId(){
        var csvParser = new ReturnCsvParser(TestStreams.GetReturnCsvStream_shortId());
        var checkoutDb = new CheckoutDb(GetCheckouts(), GetUserDb(), GetBookDb());

        for (var returnRecord: csvParser.GetReturnes()) {
            assertNotNull(checkoutDb.Return(returnRecord));
        }
        assertNull(checkoutDb.Return(new Return("1234567-(3)", TimeUtilities.GetCurrentTime())));
    }

    @Test
    public void Return_write() throws IOException {
        var csvParser = new ReturnCsvParser(TestStreams.GetReturnCsvStream());
        var checkoutDb = new CheckoutDb(GetCheckouts(), GetUserDb(), GetBookDb());

        for (var bookId: csvParser.GetReturnes()) {
            assertNotNull(checkoutDb.Return(bookId));
        }
        var allCheckouts = checkoutDb.GetAllCheckouts();

        //write out returns
        var memStream = new ByteArrayOutputStream();
        AppendUtilities.Rewrite(CheckoutDb.HeaderLines, allCheckouts, memStream, true);

        //read re-written checkouts
        var buffer = memStream.toByteArray();
        memStream.close();
        var readStream = new ByteArrayInputStream(buffer);

        var checkoutParser = new CheckoutParser(readStream);
        checkoutDb = new CheckoutDb(checkoutParser.GetCheckouts(), GetUserDb(), GetBookDb());
        assertNull(checkoutDb.Return(new Return("1234567-(3)", TimeUtilities.GetCurrentTime())));
    }
}
