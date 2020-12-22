package com.nola.utilities;

public class PrintUtilities {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    public static void PrintLine(String s){
        System.out.println(s);
    }
    public static void Print(String s){
        System.out.print(s);
    }
    public static void PrintErrorLine(String s){
        System.out.println(RED + s + RESET);
    }
    public static void PrintWarningLine(String s){
        System.out.println(YELLOW + s + RESET);
    }
    public static void PrintSuccessLine(String s){ System.out.println(GREEN + s + RESET);}
    public static void PrintDelimiterLine(String s){ System.out.println(PURPLE + s + RESET);}
    public static void PrintInfoLine(String s){
        System.out.println(CYAN + s + RESET);
    }
    public static void PrintPrompt(){
        System.out.print("OLA>");
    }

    public static void PrintBanner() {
        PrintDelimiterLine("**************************************");
        PrintDelimiterLine("      ___           ___       ___     ");
        PrintDelimiterLine("     /\\  \\         /\\__\\     /\\  \\    ");
        PrintDelimiterLine("    /::\\  \\       /:/  /    /::\\  \\   ");
        PrintDelimiterLine("   /:/\\:\\  \\     /:/  /    /:/\\:\\  \\  ");
        PrintDelimiterLine("  /:/  \\:\\  \\   /:/  /    /::\\~\\:\\  \\ ");
        PrintDelimiterLine(" /:/__/ \\:\\__\\ /:/__/    /:/\\:\\ \\:\\__\\");
        PrintDelimiterLine(" \\:\\  \\ /:/  / \\:\\  \\    \\/__\\:\\/:/  /");
        PrintDelimiterLine("  \\:\\  /:/  /   \\:\\  \\        \\::/  / ");
        PrintDelimiterLine("   \\:\\/:/  /     \\:\\  \\       /:/  /  ");
        PrintDelimiterLine("    \\::/  /       \\:\\__\\     /:/  /   ");
        PrintDelimiterLine("     \\/__/         \\/__/     \\/__/    ");
        PrintDelimiterLine("**************************************");

    }
}
