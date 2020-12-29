package com.nola.unitTests.subCommands;

import com.nola.databases.CheckoutDb;
import com.nola.parsers.CheckoutCsvParser;
import com.nola.subcommands.co;
import com.nola.subcommands.ret;
import com.nola.unitTests.TestStreams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static com.nola.unitTests.testData.GetBookDb;
import static com.nola.unitTests.testData.GetUserDb;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class retTests {
    @Test
    public void AddReturns(){
        var parser = new CheckoutCsvParser(TestStreams.GetCheckoutCsvStream());

        var checkoutDb = new CheckoutDb(parser.GetCheckouts(), GetUserDb(), GetBookDb());

        var rewriteStream = new ByteArrayOutputStream();
        var transactionStream = new ByteArrayOutputStream();
        ret.AddReturns(checkoutDb, TestStreams.GetReturnCsvStream(), rewriteStream, transactionStream);

        var checkouts = checkoutDb.GetAllCheckouts();
        assertEquals(2, checkouts.length);

        var transactionsString = transactionStream.toString();
        var checkoutString = rewriteStream.toString();

        Assertions.assertTrue(transactionsString.contains("Return"));
        Assertions.assertTrue(checkoutString.contains("DOG99"));
    }
}
