package com.nola.databases.tests;

import com.nola.dataStructures.Bundle;
import com.nola.parsers.BundleCsvParser;
import com.nola.testUtilities.TestStreams;
import com.nola.testUtilities.testData;
import com.nola.utilities.TimeUtilities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BundleDbTests {
    @Test
    public void AddBundleTest(){
        var bookDb = testData.GetBookDb();
        var bundle1= new Bundle("KK-01", "kakatua bundle 1", new String[]{"CAT12", "BAT12"}, TimeUtilities.GetCurrentTime());
        var bundle2= new Bundle("KK-02", "kakatua bundle 1", new String[]{"DOG99", "PIG07"}, TimeUtilities.GetCurrentTime());
        var bundleDb = testData.GetBundleDb();
        var originalCount = bundleDb.Count();

        assertNotNull(bundleDb.TryAdd(bundle1, bookDb));
        assertNotNull(bundleDb.TryAdd(bundle2, bookDb));

        var newCount = bundleDb.Count();

        assertEquals(2, newCount - originalCount);
    }

    @Test
    public void AddBundle_fail_due_to_duplicate(){
        var bookDb = testData.GetBookDb();
        var bundleDb = testData.GetBundleDb();

        var bundleWithDuplicate = new Bundle("MBA-01", "ma baba ami bundle 1", new String[]{"7890788-(2)", "GIP09"}, TimeUtilities.GetCurrentTime()) ;

        assertNull(bundleDb.TryAdd(bundleWithDuplicate, bookDb));
    }

    @Test
    public void FindBundleTest(){

    }

    @Test
    public void CheckoutBundleTest(){

    }

    @Test
    public void ReturnBundleTest(){

    }
}
