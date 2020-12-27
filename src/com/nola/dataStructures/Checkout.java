package com.nola.dataStructures;

import com.nola.utilities.TimeUtilities;

import java.util.Date;

public class Checkout implements Comparable<Checkout>{
    public final String BookId;
    public final String UserId;
    public final String Email;
    public final Date CheckoutDate;
    public final Date DueDate;

    public Checkout(String bookId, String userId, String email, Date checkoutDate, Date dueDate ){
        BookId = bookId;
        UserId = userId;
        Email = email;
        CheckoutDate = checkoutDate;
        DueDate = dueDate;
    }

    @Override
    public String toString(){
        return
                "Book Id:        "+ BookId+'\n'+
                "User Id:        "+ UserId+'\n'+
                "Checkout Date:  "+ TimeUtilities.ToString(CheckoutDate)+'\n'+
                "Due Date:       "+TimeUtilities.ToString(DueDate);

    }

    @Override
    public int compareTo(Checkout other) {
        return CheckoutDate.compareTo(other.CheckoutDate);
    }
}
