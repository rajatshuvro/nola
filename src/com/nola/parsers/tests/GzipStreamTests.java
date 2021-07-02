package com.nola.parsers.tests;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GzipStreamTests {
    @Test
    public void WriteAndReadBack() throws IOException {
        var text = "the quick brown fox jumps over the lazy dog";
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(new GZIPOutputStream(output), "UTF-8");
        writer.write(text);
        writer.close();

        var buffer = output.toByteArray();
        output.close();

        var inputStream = new GZIPInputStream(new ByteArrayInputStream(buffer));
        var scanner = new Scanner(inputStream);

        var readText = scanner.nextLine();

        assertEquals(text, readText);

    }
}
