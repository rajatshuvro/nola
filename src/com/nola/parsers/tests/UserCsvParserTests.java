package com.nola.parsers.tests;

import com.nola.parsers.UserCsvParser;
import com.nola.testUtilities.TestStreams;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserCsvParserTests {
    @Test
    public void GetNewUsers() throws IOException {
        var parser = new UserCsvParser(TestStreams.GetNewUserCsvStream());

        var users = parser.GetUsers();
        assertEquals(3, users.size());
        assertEquals("Tanni Chakraborty", users.get(0).Name);
    }
}
