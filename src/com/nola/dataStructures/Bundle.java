package com.nola.dataStructures;

import com.nola.utilities.TimeUtilities;

import java.util.Arrays;
import java.util.Date;

public class Bundle {
    public final String Id;
    public final String Description;
    public final String[] BookIds;
    public final Date Date;
    public final int ReadingLevel;

    public Bundle(String id, String description, String[] bookIds, int readingLevel, Date date){
        Id = id;
        Description = description;
        BookIds = bookIds;
        Date = date;
        ReadingLevel = readingLevel;
    }

    @Override
    public String toString(){
        return  "Id:           "+Id+'\n'+
                "Description:  "+Description+'\n'+
                "BookIds:      "+String.join(",", BookIds)+'\n'+
                "ReadingLevel: "+ReadingLevel+'\n'+
                "Date:         "+ TimeUtilities.ToString(Date);
    }

    public boolean contains(String bookId) {
        for (var id: BookIds) {
            if (id.equals(bookId)) return true;
        }
        return false;
    }
}
