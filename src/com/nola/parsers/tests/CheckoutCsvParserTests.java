package com.nola.parsers.tests;

import com.nola.parsers.CheckoutCsvParser;
import com.nola.testUtilities.TestStreams;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutCsvParserTests {
    @Test
    public void GetCheckouts() throws IOException {
        var parser = new CheckoutCsvParser(TestStreams.GetCheckoutCsvStream());

        var checkouts = parser.GetCheckouts();
        assertEquals(4, checkouts.size());
        assertEquals("678564-(1)", checkouts.get(1).Id);
        assertEquals("name.2", checkouts.get(1).UserId);
    }
}
