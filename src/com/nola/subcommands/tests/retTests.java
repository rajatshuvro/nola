package com.nola.subcommands.tests;

import com.nola.databases.CheckoutDb;
import com.nola.parsers.CheckoutCsvParser;
import com.nola.subcommands.ret;
import com.nola.testUtilities.TestStreams;
import com.nola.testUtilities.testData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static com.nola.testUtilities.testData.GetBookDb;
import static com.nola.testUtilities.testData.GetUserDb;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class retTests {
    @Test
    public void AddReturns(){
        var parser = new CheckoutCsvParser(TestStreams.GetCheckoutCsvStream());

        var checkoutDb = new CheckoutDb(parser.GetCheckouts(), GetUserDb(), GetBookDb());

        var rewriteStream = new ByteArrayOutputStream();
        var transactionStream = new ByteArrayOutputStream();
        ret.AddReturns(checkoutDb, null, TestStreams.GetReturnCsvStream(), rewriteStream, transactionStream, true);

        var checkouts = checkoutDb.GetAllCheckouts();
        assertEquals(2, checkouts.length);

        var transactionsString = transactionStream.toString();
        var checkoutString = rewriteStream.toString();

        Assertions.assertTrue(transactionsString.contains("Return"));
        Assertions.assertTrue(checkoutString.contains("DOG99"));
    }
    @Test
    public void ReturnBundles(){
        var checkoutDb = testData.GetCheckoutDb();
        var bundleDb = testData.GetBundleDb();
        var rewriteStream = new ByteArrayOutputStream();
        var transactionStream = new ByteArrayOutputStream();

        var checkouts = checkoutDb.GetAllCheckouts();
        assertEquals(4, checkouts.length);

        ret.AddReturns(checkoutDb, bundleDb, TestStreams.GetMixedReturnCsvStream(), rewriteStream, transactionStream, true);

        checkouts = checkoutDb.GetAllCheckouts();
        assertEquals(1, checkouts.length);

        var transactionsString = transactionStream.toString();
        var checkoutString = rewriteStream.toString();

        Assertions.assertTrue(transactionsString.contains("Return"));
        Assertions.assertFalse(checkoutString.contains("7890788-(1)"));
    }
}
