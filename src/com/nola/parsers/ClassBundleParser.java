package com.nola.parsers;

import com.nola.dataStructures.ClassBundle;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ClassBundleParser {
    private InputStream _inputStream;

    private final String ClassTag = "Class Id";
    private final String BundleIdsTag = "Bundle Ids";
    private final String UserIdsTag = "User Ids";

    public ClassBundleParser(InputStream inputStream){
        _inputStream = inputStream;
    }

    public ArrayList<ClassBundle> GetClassBundles() throws IOException {
        ArrayList<ClassBundle> classBundles = new ArrayList<>();
        var fobParser = new FlatObjectParser(_inputStream, new String[]{
                ClassTag, BundleIdsTag, UserIdsTag
        });

        var nextSetOfValues =fobParser.GetNextRecord();
        while ( nextSetOfValues != null){
            var classBundle = GetClassBundle(nextSetOfValues);
            if (classBundle != null)  classBundles.add(classBundle);

            nextSetOfValues = fobParser.GetNextRecord();
        }
        fobParser.close();
        return classBundles;
    }

    private ClassBundle GetClassBundle(HashMap<String, String> keyValues) {
        String classId = null;
        String bundleIds = null;
        String userIds = null;

        for (var entry: keyValues.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();

            switch (key){
                case ClassTag:
                    classId = value;
                    break;
                case BundleIdsTag:
                    bundleIds = value;
                    break;
                case UserIdsTag:
                    userIds = value;
                    break;
            }

        }

        return new ClassBundle(classId, bundleIds.split(","), userIds.split(","));
    }
}
