package com.nola.databases;

import com.nola.utilities.PathUtilities;

public class DbCommons {
    public static final String DataDirectory = "data";
    public static final String UsersFileName = "Users.fob";
    public static final String BooksFileName = "Books.fob";
    public static final String TransactionsFileName = "Transactions.fob";
    public static final String CheckoutsFileName = "Checkouts.fob";
    public static final String BundleFileName = "Bundles.fob";
    public static final String ClassBundleFileName = "ClassBundles.fob";

    public static String getClassBundleFilePath(){
        return PathUtilities.combine(DbCommons.DataDirectory, DbCommons.ClassBundleFileName);
    }
    public static String getBooksFilePath(){
        return PathUtilities.combine(DbCommons.DataDirectory, DbCommons.BooksFileName);
    }
    public static String getUsersFilePath(){
        return PathUtilities.combine(DbCommons.DataDirectory, DbCommons.UsersFileName);
    }
    public static String getTransactionsFilePath(){
        return PathUtilities.combine(DbCommons.DataDirectory, DbCommons.TransactionsFileName);
    }
    public static String getCheckoutsFilePath(){
        return PathUtilities.combine(DbCommons.DataDirectory, DbCommons.CheckoutsFileName);
    }

    public static String getBundleFilePath() {
        return PathUtilities.combine(DbCommons.DataDirectory, DbCommons.BundleFileName);
    }
}
