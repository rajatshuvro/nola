package com.nola.databases.tests;

import com.nola.dataStructures.User;
import com.nola.databases.UserDb;
import com.nola.testUtilities.testData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDbTests {
    @Test
    public void AddUser(){
        var userDb = new UserDb(testData.GetUsers());
        var newUser = userDb.AddNewUser("Shawroth Shuvro", "Student", "shawroth.shuvro@onkur.com", "858-666-7242");
        assertNotNull(newUser);
        assertEquals(User.StudentRoleTag, newUser.Role);
    }
}
