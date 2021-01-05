package com.nola.unitTests.subCommands;

import com.nola.databases.CheckoutDb;
import com.nola.subcommands.co;
import com.nola.unitTests.TestStreams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static com.nola.unitTests.testData.GetBookDb;
import static com.nola.unitTests.testData.GetUserDb;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class coTests {
    @Test
    public void AddCheckouts(){
        var checkoutDb = new CheckoutDb(null, GetUserDb(), GetBookDb());

        var appendStream = new ByteArrayOutputStream();
        var transactionStream = new ByteArrayOutputStream();
        co.AddCheckouts(checkoutDb, TestStreams.GetCheckoutCsvStream(), appendStream, transactionStream);

        var checkouts = checkoutDb.GetAllCheckouts();
        assertEquals(4, checkouts.length);

        var transactionsString = transactionStream.toString();
        Assertions.assertTrue(transactionsString.contains("Checkout"));
    }
}
