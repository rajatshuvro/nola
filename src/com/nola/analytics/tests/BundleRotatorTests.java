package com.nola.analytics.tests;

import com.nola.analytics.BundleRotator;
import com.nola.dataStructures.Bundle;
import com.nola.dataStructures.ClassBundle;
import com.nola.dataStructures.Transaction;
import com.nola.databases.BundleDb;
import com.nola.databases.TransactionDb;
import com.nola.parsers.ClassBundleParser;
import com.nola.utilities.TimeUtilities;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BundleRotatorTests {

    @Test
    public void ReadClassBundles() throws IOException {
        var parser = new ClassBundleParser(dataProviders.GetClassBundleStream());

        var bundles = parser.GetClassBundles();

        assertEquals(1, bundles.size());
        assertEquals("Chorui", bundles.get(0).ClassId);
    }

    @Test
    public void RandomAssignmentTest(){
        var classBundle = new ClassBundle("kak", new String[]{"bun", "gun", "hat", "mat", "chair", "table"},
                new String[]{"cat", "bat", "dog", "hog"});

        var assignments = new HashMap<String, String>();
        BundleRotator.MakeRandomAssignments(assignments, classBundle);

        assertEquals(classBundle.UserIds.length, assignments.size());
    }

    @Test
    public void ScoreAssignmentsTest(){
        var assignment1 = new HashMap<String, String>();
        assignment1.put("cat", "bun");
        assignment1.put("bat", "gun");
        assignment1.put("dog", "hat");
        assignment1.put("hog", "mat");

        var bundles = new ArrayList<Bundle>();
        bundles.add(new Bundle("bun", "pack of buns", new String[]{"bread", "loaf", "bagle"}, TimeUtilities.GetCurrentTime()));
        bundles.add(new Bundle("gun", "rack of guns", new String[]{"pistol", "machine gun", "hand gun"}, TimeUtilities.GetCurrentTime()));
        bundles.add(new Bundle("hat", "bundle of hats", new String[]{"cap", "sun hat", "cowboy hat"}, TimeUtilities.GetCurrentTime()));
        bundles.add(new Bundle("mat", "pile of mats", new String[]{"irani", "flying", "jute carpet"}, TimeUtilities.GetCurrentTime()));
        var bundleDb = new BundleDb(bundles);

        var transactions = new ArrayList<Transaction>();
        //var transactionsDb = new TransactionDb(transactions)
    }
}
