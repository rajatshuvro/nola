package com.nola.subcommands.tests;

import com.nola.databases.BookDb;
import com.nola.databases.BundleDb;
import com.nola.subcommands.add;
import com.nola.testUtilities.TestStreams;
import com.nola.testUtilities.testData;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

public class addBundlesTests {
    @Test
    public void AddBundles(){
        var bookDb = testData.GetBookDb();
        var newBundleCsvStream = TestStreams.GetBundleCsvStream();
        var bundleDb = testData.GetBundleDb();
        var originalCount = bundleDb.Count();

        var appendStream = new ByteArrayOutputStream();
        add.AddBundles(bookDb, bundleDb, newBundleCsvStream, appendStream, true);
        var newCount = bundleDb.Count();

        assertEquals(2, newCount - originalCount);

    }
}
