package com.nola.utilities;

public class StringUtilities {
    public static int GetWordCount(String s) {
        var words = s.split("\\s+");
        return words.length;
    }
}
