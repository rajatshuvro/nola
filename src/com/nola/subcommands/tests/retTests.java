package com.nola.subcommands.tests;

import com.nola.databases.TransactionDb;
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

        var transactionDb = new TransactionDb(parser.GetCheckouts(), GetUserDb(), GetBookDb());

        var rewriteStream = new ByteArrayOutputStream();
        var transactionStream = new ByteArrayOutputStream();
        ret.AddReturns(transactionDb, null, TestStreams.GetReturnCsvStream(), transactionStream, true);

        var checkouts = transactionDb.GetPendingCheckouts();
        assertEquals(2, checkouts.size());

        var transactionsString = transactionStream.toString();

        Assertions.assertTrue(transactionsString.contains("Return"));
    }
    @Test
    public void ReturnBundles(){
        var transactionDb = testData.GetCheckoutDb();
        var bundleDb = testData.GetBundleDb();
        var rewriteStream = new ByteArrayOutputStream();
        var transactionStream = new ByteArrayOutputStream();

        var checkouts = transactionDb.GetPendingCheckouts();
        assertEquals(4, checkouts.size());

        ret.AddReturns(transactionDb, bundleDb, TestStreams.GetMixedReturnCsvStream(), transactionStream, true);

        checkouts = transactionDb.GetPendingCheckouts();
        assertEquals(1, checkouts.size());

        var transactionsString = transactionStream.toString();
        var checkoutString = rewriteStream.toString();

        Assertions.assertTrue(transactionsString.contains("Return"));
        Assertions.assertFalse(checkoutString.contains("7890788-(1)"));
    }

    @Test
    public void ReturnMultipleBundles(){
        var transactionDb = testData.GetCheckoutDb();
        var bundleDb = testData.GetBundleDb();
        var transactionStream = new ByteArrayOutputStream();

        var checkouts = transactionDb.GetPendingCheckouts();
        assertEquals(4, checkouts.size());

        ret.AddReturns(transactionDb, bundleDb, TestStreams.GetMultiEntryReturnCsvStream(), transactionStream, true);

        checkouts = transactionDb.GetPendingCheckouts();
        assertEquals(1, checkouts.size());

        var transactionsString = transactionStream.toString();

        Assertions.assertTrue(transactionsString.contains("Return"));
        Assertions.assertTrue(transactionsString.contains("7890788-(2)"));
    }
}
