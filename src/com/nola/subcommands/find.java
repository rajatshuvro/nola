package com.nola.subcommands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class find {
    private static String commandSyntex = "nola find -b \"book search string\" -u \"user search string\"";

    public static void Run(String[] args){
        Options options = new Options();

        Option bookOption = new Option("b", "book", true, "");
        bookOption.setRequired(false);
        options.addOption(bookOption);

        Option userOption = new Option("u", "user", true, "user search string");
        userOption.setRequired(false);
        options.addOption(userOption);

    }
}
