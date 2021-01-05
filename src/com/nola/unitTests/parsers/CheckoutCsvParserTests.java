package com.nola.unitTests.parsers;

import com.nola.parsers.CheckoutCsvParser;
import com.nola.unitTests.TestStreams;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutCsvParserTests {
    @Test
    public void GetCheckouts() throws IOException {
        var parser = new CheckoutCsvParser(TestStreams.GetCheckoutCsvStream());

        var checkouts = parser.GetCheckouts();
        assertEquals(4, checkouts.size());
        assertEquals("678564-(1)", checkouts.get(1).BookId);
        assertEquals("name.2", checkouts.get(1).UserId);
    }
}