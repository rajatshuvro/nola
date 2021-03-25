package com.nola.analytics;

import com.nola.dataStructures.ClassBundle;
import com.nola.databases.BundleDb;
import com.nola.databases.TransactionDb;

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

        var studentBundleScores = GetStudentBundleScores(classBundle, bundleDb, transactionDb);

        MakeRandomAssignments(assignments, classBundle);
        var score = ScoreAssignment(assignments, studentBundleScores);
        return assignments;
    }

    private int ScoreAssignment(HashMap<String, String> assignments, HashMap<String, Integer> studentBundleScores) {
        var score = 0;
        for (var userId: assignments.keySet()) {
            var bundleId = assignments.get(userId);
            score += studentBundleScores.get(userId+':'+bundleId);
        }
        return score;
    }

    public HashMap<String, Integer> GetStudentBundleScores(ClassBundle classBundle, BundleDb bundleDb, TransactionDb transactionDb) {
        var bundleScores = new HashMap<String, Integer>();
        for (var userId: classBundle.UserIds) {
            // generate reading list for a user
            var transactions = transactionDb.GetUserActivity(userId);
            var readingSet = new HashSet<String>();
            for (var transaction: transactions) {
                readingSet.add(transaction.BookId);
            }
            //generate bundle scores
            for (var bundleId: classBundle.BundleIds) {
                var bookIds = bundleDb.GetBookBundle(bundleId).BookIds;

                var score =0;
                for (var bookId: bookIds) {
                    if (readingSet.contains(bookId)) score++;
                }
                bundleScores.put(userId+':'+bundleId, score);
            }
        }
        return bundleScores;
    }

    public void MakeRandomAssignments(HashMap<String, String> assignments, ClassBundle classBundle) {
        var bundleIndices = new int[classBundle.UserIds.length];
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
            var studentId = classBundle.UserIds[i];
            var bundleId = classBundle.BundleIds[bundleIndices[i]];
            assignments.put(studentId, bundleId);

        }
    }

    public static void swap (int[] a, int i, int j) {
        var t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
