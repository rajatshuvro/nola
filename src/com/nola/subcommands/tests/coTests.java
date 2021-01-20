package com.nola.subcommands.tests;

import com.nola.databases.CheckoutDb;
import com.nola.parsers.CheckoutCsvParser;
import com.nola.subcommands.co;
import com.nola.testUtilities.TestStreams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static com.nola.testUtilities.testData.GetBookDb;
import static com.nola.testUtilities.testData.GetUserDb;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class coTests {
    @Test
    public void AddCheckouts(){
        var checkoutDb = new CheckoutDb(null, GetUserDb(), GetBookDb());

        var appendStream = new ByteArrayOutputStream();
        var transactionStream = new ByteArrayOutputStream();
        var csvParser = new CheckoutCsvParser(TestStreams.GetCheckoutCsvStream());
        co.CheckoutBooks(checkoutDb, csvParser.GetCheckouts(), appendStream, transactionStream, true);

        var checkouts = checkoutDb.GetAllCheckouts();
        assertEquals(4, checkouts.length);

        var transactionsString = transactionStream.toString();
        Assertions.assertTrue(transactionsString.contains("Checkout"));
    }
}
