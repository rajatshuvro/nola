package com.nola.dataStructures;

import java.util.Arrays;

public class BookBundle {
    public final String Id;
    public final String Description;
    public final String[] BookIds;

    public BookBundle(String id, String description, String[] bookIds){
        Id = id;
        Description = description;
        BookIds = bookIds;
    }

    @Override
    public String toString(){
        return  "Id:           "+Id+'\n'+
                "Description:  "+Description+'\n'+
                "BookIds:      "+String.join(",", BookIds);
    }

    public boolean contains(String bookId) {
        for (var id: BookIds) {
            if (id.equals(bookId)) return true;
        }
        return false;
    }
}
