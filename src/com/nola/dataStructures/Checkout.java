package com.nola.dataStructures;

import com.nola.utilities.TimeUtilities;

import java.util.Date;

public class Checkout implements Comparable<Checkout>{
    public final String Id;
    public final String UserId;
    public final String Email;
    public final Date CheckoutDate;
    public final Date DueDate;

    public Checkout(String id, String userId, String email, Date checkoutDate, Date dueDate ){
        Id = id;
        UserId = userId;
        Email = email;
        CheckoutDate = checkoutDate;
        DueDate = dueDate;
    }

    @Override
    public String toString(){
        return
                "Resource Id:    "+ Id +'\n'+
                "User Id:        "+ UserId+'\n'+
                "Checkout Date:  "+ TimeUtilities.ToString(CheckoutDate)+'\n'+
                "Due Date:       "+TimeUtilities.ToString(DueDate);

    }

    @Override
    public int compareTo(Checkout other) {
        return CheckoutDate.compareTo(other.CheckoutDate);
    }
}
