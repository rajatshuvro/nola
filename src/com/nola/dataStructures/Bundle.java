package com.nola.dataStructures;

import com.nola.utilities.TimeUtilities;

import java.util.Arrays;
import java.util.Date;

public class Bundle {
    public final String Id;
    public final String Description;
    public final String[] BookIds;
    public final Date Date;

    public Bundle(String id, String description, String[] bookIds, Date date){
        Id = id;
        Description = description;
        BookIds = bookIds;
        Date = date;
    }

    @Override
    public String toString(){
        return  "Id:           "+Id+'\n'+
                "Description:  "+Description+'\n'+
                "BookIds:      "+String.join(",", BookIds)+'\n'+
                "Date:         "+ TimeUtilities.ToString(Date);
    }

    public boolean contains(String bookId) {
        for (var id: BookIds) {
            if (id.equals(bookId)) return true;
        }
        return false;
    }
}
