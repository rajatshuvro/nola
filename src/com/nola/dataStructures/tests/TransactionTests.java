package com.nola.dataStructures.tests;

import com.nola.dataStructures.Transaction;
import com.nola.databases.AppendUtilities;
import com.nola.databases.TransactionDb;
import com.nola.parsers.TransactionParser;
import com.nola.testUtilities.TestStreams;
import com.nola.testUtilities.testData;
import com.nola.utilities.TimeUtilities;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.nola.testUtilities.testData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionTests {
    @Test
    public void SkipInvalidUserAndBook() throws IOException{
        var parser = new TransactionParser(TestStreams.GetTransactionStreamReduced());
        var transactions = parser.GetTransactions();

        var transactionDb = new TransactionDb(transactions, GetUserDb_reduced(), GetBookDb_reduced());

        assertEquals(Transaction.ReturnTag, transactionDb.GetBookStatus("7890788-(2)"));
    }
    private ArrayList<Transaction> GetTransactions(){
        var transactions = new ArrayList<Transaction>();
        transactions.add(Transaction.Create("7890788-(2)", "234", TimeUtilities.parseDateTime("2019-09-13 10:30:31"), Transaction.CheckoutTag));
        transactions.add(Transaction.Create("678564-(1)", "123", TimeUtilities.parseDateTime("2019-10-15 11:01:22"), Transaction.CheckoutTag));
        transactions.add(Transaction.Create("456098-(1)", "345", TimeUtilities.parseDateTime("2019-11-03 10:33:22"), Transaction.CheckoutTag));
        transactions.add(Transaction.Create("7890788-(2)", "234", TimeUtilities.parseDateTime("2019-11-13 10:30:25"), Transaction.ReturnTag));

        return transactions;
    }
    @Test
    public void AppendNewTransactions() throws IOException{
        //initializing with empty transaction list
        var transactionDb = new TransactionDb(GetTransactions(), testData.GetUserDb_transactionTest(), testData.GetBookDb_transactionTest());
        //adding new transactions
        var newTransactions = new ArrayList<Transaction>();
        newTransactions.add(Transaction.Create("7890788-(2)", "234", TimeUtilities.parseDateTime("2019-11-13 10:39:31"), Transaction.CheckoutTag));
        newTransactions.add(Transaction.Create("678564-(1)", "123", TimeUtilities.parseDateTime("2019-11-15 11:01:22"), Transaction.ReturnTag));
        var count = transactionDb.AddRange(newTransactions);
        assertEquals(2, count);

        var memStream = new ByteArrayOutputStream();
        AppendUtilities.AppendItems(newTransactions, memStream);
        var buffer = memStream.toByteArray();
        memStream.close();
        var inputStream =  new ByteArrayInputStream(buffer);
        var hasTimeStamp1= false;
        var hasTimeStamp2 = false;
        try (Scanner scanner =  new Scanner(inputStream)){
            while (scanner.hasNextLine()){
                var line = scanner.nextLine();
                if(line.contains("2019-11-13 10:39:31")){
                    hasTimeStamp1 = true;
                }
                if(line.contains("2019-11-15 11:01:22")){
                    hasTimeStamp2 = true;
                }
            }
        }

        assertTrue(hasTimeStamp1);
        assertTrue(hasTimeStamp2);
    }
}
