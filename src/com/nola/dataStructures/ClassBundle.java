package com.nola.dataStructures;

public class ClassBundle {
    public final String ClassId;
    public final String[] BundleIds;
    public final String[] UserIds;

    public ClassBundle(String classId, String[] bundleIds, String[] userIds){
        ClassId = classId;
        BundleIds = bundleIds;
        UserIds = userIds;
    }

    @Override
    public String toString(){
        return  "ClassId:      "+ ClassId+'\n'+
                "BundleIds:    "+ String.join(",", BundleIds)+'\n'+
                "UserIds:      "+ String.join(",", UserIds);
    }
}
