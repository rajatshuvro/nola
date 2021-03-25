package com.nola.databases;

import com.nola.dataStructures.ClassBundle;

import java.util.*;

public class BundleRotator {
    private HashMap<String, ClassBundle> _classBundles;

    public BundleRotator(Iterable<ClassBundle> classBundles){
        _classBundles = new HashMap<>();
        for (var classBundle: classBundles) {
            _classBundles.put(classBundle.ClassId, classBundle);
        }

    }

    public HashMap<String, String> Rotate(String classId, BundleDb bundleDb, TransactionDb transactionDb){
        if (!_classBundles.containsKey(classId)) return null;
        var classBundle = _classBundles.get(classId);
        var assignments = new HashMap<String, String>();

        MakeRandomAssignments(assignments, classBundle);
        var score = ScoreAssignment(assignments, bundleDb, transactionDb);
        return assignments;
    }

    private void MakeRandomAssignments(HashMap<String, String> assignments, ClassBundle classBundle) {
        var bundleIndices = new int[classBundle.StudentIds.length];
        for (var i=0; i < bundleIndices.length; i++)
            bundleIndices[i]=i;

        var random = new Random();
        for (var i= bundleIndices.length-1 ; i > 0; i--){
            var j = random.nextInt(i+1);
            //swap item i with item j
            swap(bundleIndices, i, j);
        }

        assignments.clear();
        for (var i=0; i< bundleIndices.length; i++) {
            var studentId = classBundle.StudentIds[i];
            var bundleId = classBundle.BundleIds[bundleIndices[i]];
            assignments.put(studentId, bundleId);

        }
    }

    private int ScoreAssignment(HashMap<String, String> assignments, BundleDb bundleDb, TransactionDb transactionDb) {
        return 0;
    }

    public static void swap (int[] a, int i, int j) {
        var t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
