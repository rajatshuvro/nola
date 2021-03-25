package com.nola.dataStructures;

public class ClassBundle {
    public final String ClassId;
    public final String[] BundleIds;
    public final String[] StudentIds;

    public ClassBundle(String classId, String[] bundleIds, String[] studentIds){
        ClassId = classId;
        BundleIds = bundleIds;
        StudentIds = studentIds;
    }

    @Override
    public String toString(){
        return  "ClassId:      "+ ClassId+'\n'+
                "BundleIds:    "+ String.join(",", BundleIds)+'\n'+
                "StudentIds:   "+ String.join(",", StudentIds);
    }
}
