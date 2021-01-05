package com.nola.parsers.tests;

import com.nola.parsers.TransactionParser;
import com.nola.testUtilities.TestStreams;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionParserTests {
    @Test
    public void ParseTransactionsTest() throws IOException {
        var parser = new TransactionParser(TestStreams.GetTransactionsStream());
        var transactions = parser.GetTransactions();

        assertEquals(4, transactions.size());

        assertEquals("7890788-(2)", transactions.get(0).BookId);
        assertEquals("Checkout", transactions.get(0).Type);
    }
}
