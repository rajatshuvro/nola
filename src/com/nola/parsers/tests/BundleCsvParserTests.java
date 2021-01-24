package com.nola.parsers.tests;

import com.nola.parsers.BundleCsvParser;
import com.nola.testUtilities.TestStreams;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BundleCsvParserTests {
    @Test
    public void GetBundles() throws IOException {
        var parser = new BundleCsvParser(TestStreams.GetBundleCsvStream());

        var bundles = parser.GetBundles();
        assertEquals(2, bundles.size());
        assertEquals("Id:           KK-01\n" +
                "Description:  Kokil Bundle 1\n" +
                "BookIds:      CAT12,BAT12\n" +
                "Date:         2021-01-23 10:08:42", bundles.get(0).toString());
        assertEquals("Id:           KK-02\n" +
                "Description:  Kakatua Bundle 2\n" +
                "BookIds:      DOG99,PIG07\n" +
                "Date:         2021-01-23 10:09:21", bundles.get(1).toString());
    }
}
