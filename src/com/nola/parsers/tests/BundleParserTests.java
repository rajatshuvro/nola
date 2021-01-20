package com.nola.parsers.tests;

import com.nola.parsers.BundleParser;
import com.nola.testUtilities.TestStreams;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BundleParserTests {
    @Test
    public void ParseBundles() throws IOException {
        var inputStream = TestStreams.GetBundleStream();
        var parser = new BundleParser(inputStream);

        var bundles = parser.GetBundles();
        assertEquals(2, bundles.size());

        assertEquals("Id:           UKI33\n" +
                "Description:  Kokil Bundle 1\n" +
                "BookIds:      7890788-(1),678564-(1)\n" +
                "Date:         2021-01-03 10:33:10", bundles.get(0).toString());
    }

}
