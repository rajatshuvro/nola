package com.nola.parsers;

public class ParserUtilities {
    // if failed to parse, return -1,
    // java doesn't allow passing by ref
    public static long ParseULong(String s){
        long x= -1;
        try{
            x = Long.parseLong(s);
        }
        catch (Exception e){
            return x;
        }
        return x;
    }
    public static int ParseUInt(String s){
        int x= -1;
        try{
            x = Integer.parseInt(s);
        }
        catch (Exception e){
            return x;
        }
        return x;
    }

    public static float ParseUFloat(String s) {
        float x= (float)-1.0;
        try{
            x = Float.parseFloat(s);
        }
        catch (Exception e){
            return x;
        }
        return x;
    }

    public static boolean IsNullOrEmpty(String genre) {
        return genre == null || genre.equals("");
    }

    public static String GetBookFileHeader(){
        var sb = new StringBuilder();
        sb.append("#Onkur library books\n");
        sb.append("#Schema number: 1\n");
        sb.append("#Title = Book title. Value = <String>\n");
        sb.append("#Author = Author name. Value = <String>\n");
        sb.append("#ISBN = ISBN number (-1 if unknown). Value = <Integer>\n");
        sb.append("#Publisher = Publisher name. Value = <String>\n");
        sb.append("#Year = Year of publication. Value = <Integer>");
        sb.append("#Genre = Book genre. Value = General/Fiction/Science/Social/Religion/History/Geography/Culture/Biography/Fairy tale/Factual\n");
        sb.append("#Page count = Page count. Value = <Integer>\n");
        sb.append("#Price = Book price (to be charged if lost). Value = <Decimal>\n");
        sb.append("#Reading level = Estimated reading level. Value = [1,2,3...10]\n");

        return sb.toString();
    }

    public static long ParseIsbn(String isbnString) {
        if(IsNullOrEmpty(isbnString) || isbnString.equals("-1")) return -1;
        if(isbnString.contains("-")) isbnString = isbnString.replace("-","");
        return ParseULong(isbnString);
    }

    public static String ToProperCase(String s) {
        var words = s.split("\\s+");
        var sb = new StringBuilder();
        for (int i=0; i < words.length; i++) {
            var word = words[i];
            words[i] = word.substring(0, 1).toUpperCase() +
                    word.substring(1).toLowerCase();

        }
        return String.join(" ", words);
    }
}
