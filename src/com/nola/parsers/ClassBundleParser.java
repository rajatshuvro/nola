package com.nola.parsers;

import com.nola.dataStructures.ClassBundle;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ClassBundleParser {
    private InputStream _inputStream;

    private final String ClassTag = "ClassId";
    private final String BundleIdsTag = "BundleIds";
    private final String StudentIdsTag = "StudentIds";

    public ClassBundleParser(InputStream inputStream){
        _inputStream = inputStream;
    }

    public ArrayList<ClassBundle> GetClassBundles() throws IOException {
        ArrayList<ClassBundle> classBundles = new ArrayList<>();
        var fobParser = new FlatObjectParser(_inputStream, new String[]{
                ClassTag, BundleIdsTag, StudentIdsTag
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
        String studentIds = null;

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
                case StudentIdsTag:
                    studentIds = value;
                    break;
            }

        }

        return new ClassBundle(classId, bundleIds.split(","), studentIds.split(","));
    }
}
