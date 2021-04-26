package com.nola.analytics.tests;

import com.nola.analytics.BundleRotator;
import com.nola.dataStructures.ClassBundle;
import com.nola.parsers.ClassBundleParser;
import com.nola.testUtilities.testData;
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

        var assignments = BundleRotator.CreateRandomAssignments(classBundle);

        assertEquals(classBundle.UserIds.length, assignments.size());
    }

    @Test
    public void BundleScoreTest(){
        var bundleDb = testData.GetBundleDb();
        var bookDb = testData.GetBookDb();
        var transactionsDb = testData.GetTransactionDb();
        var classBundle = new ClassBundle("chorui", new String[]{"BUN01", "DUN02"}, new String[]{"123", "234", "456"});

        var scores = BundleRotator.GetBundleScores(classBundle, bookDb, bundleDb, transactionsDb);

        assertEquals(0, scores.get("456:BUN01"));
        assertEquals(1, scores.get("123:BUN01"));
        assertEquals(1, scores.get("234:BUN01"));
    }

    @Test
    public void ScoreAssignmentTest(){
        var assignment1 = new HashMap<String, String>();
        assignment1.put("234", "BUN01");
        assignment1.put("123", "DUN02");

        var assignment2 = new HashMap<String, String>();
        assignment2.put("456", "BUN01");
        assignment2.put("123", "DUN02");

        var bundleDb = testData.GetBundleDb();
        var bookDb = testData.GetBookDb();
        var transactionsDb = testData.GetTransactionDb();
        var classBundle = new ClassBundle("chorui", new String[]{"BUN01", "DUN02"}, new String[]{"123", "234", "456"});

        var bundleScores = BundleRotator.GetBundleScores(classBundle, bookDb, bundleDb, transactionsDb);

        var assignmentScore = BundleRotator.ScoreAssignment(assignment1, bundleScores);
        assertEquals(2, assignmentScore);

        assignmentScore = BundleRotator.ScoreAssignment(assignment2, bundleScores);
        assertEquals(1, assignmentScore);
    }

    @Test
    public void RotateTest(){
        var bundleDb = testData.GetBundleDb();
        var bookDb = testData.GetBookDb();
        var transactionsDb = testData.GetTransactionDb();
        var classBundle = new ClassBundle("chorui", new String[]{"BUN01", "DUN02", "GUN03"}, new String[]{"123", "234", "456"});

        var bundleList = new ArrayList<ClassBundle>();
        bundleList.add(classBundle);
        var rotator = new BundleRotator(bundleList);
        var assignments = rotator.Rotate("chorui", bookDb, bundleDb, transactionsDb);

        var bundleScores = BundleRotator.GetBundleScores(classBundle, bookDb, bundleDb, transactionsDb);

        var assignmentScore = BundleRotator.ScoreAssignment(assignments, bundleScores);
        assertEquals(0, assignmentScore);
    }
}
