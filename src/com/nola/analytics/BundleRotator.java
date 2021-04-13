package com.nola.analytics;

import com.nola.dataStructures.ClassBundle;
import com.nola.databases.BundleDb;
import com.nola.databases.TransactionDb;
import com.nola.utilities.PrintUtilities;

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

        var bundleScores = GetBundleScores(classBundle, bundleDb, transactionDb);

        //we will try out 100 random assignments and pick the minimum one
        var minAssignmentScore = Integer.MAX_VALUE;
        HashMap<String, String> minAssignment = null;
        var iterationCount = Integer.min(100, classBundle.BundleIds.length* classBundle.UserIds.length);
        for (int i=0; i < iterationCount; i++){
            var assignments = CreateRandomAssignments(classBundle);
            var assignmentScore = ScoreAssignment(assignments, bundleScores);
            if(assignmentScore == 0) {
                PrintUtilities.PrintInfoLine("Min assignment score: 0");
                return assignments;
            }

            //FindLocalMinima(assignments, bundleScores);

            if (minAssignmentScore > assignmentScore){
                minAssignmentScore = assignmentScore;
                minAssignment = assignments;
            }
        }
        PrintUtilities.PrintInfoLine("Min assignment score:"+minAssignmentScore);
        return minAssignment;
    }

    private void FindLocalMinima(HashMap<String, String> assignments, HashMap<String, Integer> bundleScores) {
        //find the max and min costing assignments and swap them. Keep doing this as long as the results can be improved

    }

    public static int ScoreAssignment(HashMap<String, String> assignments, HashMap<String, Integer> studentBundleScores) {
        var score = 0;
        for (var userId: assignments.keySet()) {
            var bundleId = assignments.get(userId);
            score += studentBundleScores.get(userId+':'+bundleId);
        }
        return score;
    }

    public static HashMap<String, Integer> GetBundleScores(ClassBundle classBundle, BundleDb bundleDb, TransactionDb transactionDb) {
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
                var bundle = bundleDb.GetBookBundle(bundleId);
                if (bundle==null) {
                    PrintUtilities.PrintErrorLine("Failed to find bundle:"+bundleId);
                }
                var bookIds = bundle.BookIds;

                var score =0;
                for (var bookId: bookIds) {
                    if (readingSet.contains(bookId)) score++;
                }
                bundleScores.put(userId+':'+bundleId, score);
            }
        }
        return bundleScores;
    }

    public static HashMap<String, String> CreateRandomAssignments(ClassBundle classBundle) {
        var bundleIndices = new int[classBundle.UserIds.length];
        for (var i=0; i < bundleIndices.length; i++)
            bundleIndices[i]=i;

        var random = new Random();
        for (var i= bundleIndices.length-1 ; i > 0; i--){
            var j = random.nextInt(i+1);
            //swap item i with item j
            swap(bundleIndices, i, j);
        }

        HashMap<String, String> assignments = new HashMap<>();

        for (var i=0; i< bundleIndices.length; i++) {
            var studentId = classBundle.UserIds[i];
            var bundleId = classBundle.BundleIds[bundleIndices[i]];
            assignments.put(studentId, bundleId);

        }
        return assignments;
    }

    public static void swap (int[] a, int i, int j) {
        var t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
