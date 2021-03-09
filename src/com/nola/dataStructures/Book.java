package com.nola.dataStructures;

import com.nola.NativeSearch.ISearchDocument;
import com.nola.databases.IdDb;
import com.nola.parsers.ParserUtilities;
import com.nola.utilities.PrintUtilities;
import com.nola.utilities.StringUtilities;
import com.nola.utilities.TimeUtilities;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Book implements Comparable<Book>, ISearchDocument {
    public final long Isbn;
    public final String Author;
    public final String Title;
    public final int Year;
    public final String Publisher;
    public final int CopyNum;
    public final int PageCount;
    public final float Price;
    public final String Genre;
    public String ShortId;
    public final int ReadingLevel;
    public final Date EntryDate;
    public final Date ExpiryDate;

    private Book(long isbn, String author, String title, String publisher, int year, int pageCount,
                float price, String genre, int readingLevel, int copyNum,
                Date entryDate, Date expiryDate, String shortId){
        Isbn = isbn;
        Author = author;
        Title = title;
        Publisher = publisher;
        Year = year;
        CopyNum = copyNum;
        PageCount = pageCount;
        Price = price;
        Genre = genre;
        ReadingLevel = readingLevel;
        EntryDate = entryDate;
        ExpiryDate = expiryDate;
        ShortId = shortId == null ? "" : shortId;
    }
    //if a user friendly bookId is provided, it needs to be reduced to isbn-(copy num) format
    public static String GetReducedId(String bookId) {
        if(IdDb.IsValidShortId(bookId)) return bookId;
        var splits = bookId.split("-");
        if (splits.length == 2) return bookId;
        else return splits[0]+'-'+splits[splits.length-1];
    }

    public String GetId(){
        return GenerateId(Isbn, CopyNum);
    }

    @Override
    public int compareTo(Book other) {
        return Title.compareTo(other.Title);
    }
    public static long GetIsbn(String bookId) {
        if(ParserUtilities.IsNullOrEmpty(bookId)) return -1;

        var isbnString = bookId.split("-")[0];
        return ParserUtilities.ParseULong(isbnString);
    }

    public static Book Create(long isbn, String author, String title, String publisher, int year, int pageCount,
                              float price, String genre, int readingLevel, int copyNum,
                              Date entryDate, Date expiryDate, String shortId)
    {
        if(!IsValid(isbn, author,title, publisher, year, pageCount, price, genre, readingLevel, copyNum)) return null;

        return new Book(isbn, author, title, publisher, year, pageCount,
                        price, genre, readingLevel, copyNum,
                        entryDate, expiryDate, shortId);
    }
    private static boolean IsValid(long isbn, String author, String title, String publisher, int year,
                                  int pageCount, float price, String genre, int readingLevel, int copyNumber) {
        return isbn != 0 &&
                !(ParserUtilities.IsNullOrEmpty(author)) &&
                !(ParserUtilities.IsNullOrEmpty(title)) &&
                !(ParserUtilities.IsNullOrEmpty(publisher)) &&
                price > 0 &&
                //todo: many legacy books don't have year specified. need to manually fix this.
                //year > 0 &&
                pageCount > 0 &&
                IsValidGenreTag(genre) &&
                IsValidReadingLevel(readingLevel);
    }

    public static boolean IsLegacyValid(long isbn, String author, String title, String publisher, int year,
                                  int pageCount, float price, String genre, int readingLevel, int copyNumber) {
        return isbn != 0 &&
                !(ParserUtilities.IsNullOrEmpty(author)) &&
                !(ParserUtilities.IsNullOrEmpty(title)) &&
                !(ParserUtilities.IsNullOrEmpty(publisher)) &&
                price > 0 &&
                pageCount > 0 &&
                IsValidGenreTag(genre) &&
                IsValidReadingLevel(readingLevel);
    }

    public static long GenerateIsbn(String title, String author, String publisher, int year, int pageCount) {
        // since the spelling of the title, author and publisher is subjective,
        // we use a more reliable and stable parameter - word counts of these strings
        if(pageCount == -1) pageCount = 12;//some books don't have page count
        if(year == -1) year = 1789;
        var titleWordCount = StringUtilities.GetWordCount(title);
        var authorWordCount = StringUtilities.GetWordCount(author);
        var publisherWordCount = StringUtilities.GetWordCount(publisher);
        var titleNumeric = GetNumericChars(title);
        var isbnString = Integer.toString(titleWordCount+titleNumeric) + authorWordCount + publisherWordCount
                        + year + pageCount;
        if(isbnString.length()>9) {
            System.out.println("Failed to generate isbn smaller than 10 characters");
            return 999999999L;
        }
        return Long.parseLong(isbnString);
    }

    private static int GetNumericChars(String title) {
        var s = "";
        for(var c: title.toCharArray()){
            if(Character.isDigit(c))  s+=c;
        }
        return s.equals("")? 0: ParserUtilities.ParseUInt(s);
    }

//    public String GetUserFriendlyId(){
//        return String.join("-", Long.toString(Isbn), GetAbbreviation(Genre), Integer.toString(ReadingLevel), '('+Integer.toString(CopyNum)+')');
//    }

    public static String GenerateId(long isbn, int copyNum){
        return isbn +"-("+ copyNum +')';
    }

    private static final int MinReadingLevel =1;
    private static final int MaxReadingLevel = 10;
    public static boolean IsValidReadingLevel(int level){
        return level >= MinReadingLevel && level <= MaxReadingLevel;
    }

    @Override
    public String toString(){
        return  "Title:           "+Title+'\n'+
                "Author:          "+Author+'\n'+
                "ISBN:            "+Isbn+'\n'+
                "ID:              "+GetId()+'\n'+
                "Publisher:       "+Publisher+'\n'+
                "Year:            "+Year+'\n'+
                "Genre:           "+Genre+'\n'+
                "Copy number:     "+CopyNum+'\n'+
                "Page count:      "+ PageCount+'\n'+
                "Price:           "+Price+'\n'+
                "Reading level:   "+ReadingLevel+'\n'+
                "Entry date:      "+ TimeUtilities.ToString(EntryDate)+'\n'+
                "Expiry date:     "+ TimeUtilities.ToString(ExpiryDate)+'\n'+
                "ShortId:         "+ ShortId.toLowerCase();

    }

    public String GetContent(){
        return  Title+'\n'+
                Author+'\n'+
                Isbn+'\n'+
                Publisher+'\n'+
                Year+'\n'+
                Genre+'\n'+
                CopyNum+'\n'+
                PageCount+'\n'+
                Price+'\n'+
                ReadingLevel+'\n'+
                TimeUtilities.ToString(EntryDate)+'\n'+
                TimeUtilities.ToString(ExpiryDate)+'\n'+
                ShortId;
    }

    public String ToCsvString(){
        return "\""+TimeUtilities.ToString(EntryDate)+"\"," +
                "\""+Isbn+"\"," +
                "\""+Title+"\"," +
                "\""+Author+"\"," +
                "\""+Publisher+"\"," +
                "\""+Year+"\"," +
                "\""+ ShortId +"\"," +
                "\""+Genre+"\"," +
                "\""+ReadingLevel+"\"," +
                "\""+PageCount+"\"," +
                "\""+Price;
    }

    //static fields
    // Tags in the files should be user friendly. they may be abbreviated in IDs.
    public static final String FictionTag = "Fiction";
    public static final String ScienceTag = "Science";
    public static final String SociologyTag = "Social";
    public static final String GeneralTag = "General";
    public static final String ReligiousTag = "Religion";
    public static final String HistoryTag = "History";
    public static final String GeographyTag = "Geography";
    public static final String CultureTag = "Culture";
    public static final String BiographyTag = "Biography";
    public static final String FairyTaleTag = "Fairy tale";
    public static final String FactualTag = "Factual";

    private static final HashSet<String> GenreTags = new HashSet<>(){{
        add(FictionTag);
        add(ScienceTag);
        add(SociologyTag);
        add(GeneralTag);
        add(ReligiousTag);
        add(HistoryTag);
        add(GeographyTag);
        add(CultureTag);
        add(BiographyTag);
        add(FairyTaleTag);
        add(FactualTag);

    }};

    private static final HashMap<String, String> GenreAbbreviations = new HashMap<>(){{
        put(FictionTag, "FIC");
        put(ScienceTag, "SCI");
        put(SociologyTag, "SOC");
        put(GeneralTag, "GEN");
        put(ReligiousTag, "REL");
        put(HistoryTag, "HIS");
        put(GeographyTag, "GEO");
        put(CultureTag, "CUL");
        put(BiographyTag, "BIO");
        put(FairyTaleTag, "FAI");
        put(FactualTag, "FAC");

    }};

    public static boolean IsValidGenre(String genre){
        return GenreTags.contains(genre) || GenreAbbreviations.containsValue(genre);
    }
    private static String GetAbbreviation(String genres){
        var genreArray = genres.split(";");
        var abbreviation = new StringBuilder();
        boolean isFirst=true;
        for (String genre: genreArray){
            if(!isFirst) abbreviation.append("-");
            abbreviation.append(GenreAbbreviations.get(genre));
            isFirst = false;
            }
        return abbreviation.toString();
        }

    private static boolean IsValidGenreTag(String genres) {
        var genreArray = genres.split(";");
        for (String genre: genreArray) {
            if (!GenreTags.contains(genre)) {
                PrintUtilities.PrintWarningLine("WARNING!! Invalid genre:" + genre);
                return false;
            }
        }
        return true;
    }
}
