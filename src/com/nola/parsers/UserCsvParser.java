package com.nola.parsers;

import com.nola.dataStructures.User;
import com.nola.databases.UserDb;
import com.nola.utilities.PrintUtilities;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class UserCsvParser {
    private InputStreamReader _reader;
    public final String TimeTag = "Timestamp";
    public final String NameTag = "Name";
    public final String RoleTag = "Role";
    public final String EmailTag = "Email";
    public final String PhoneTag = "Phone";

    public UserCsvParser(InputStream stream){
        _reader = new InputStreamReader(stream);
    }

    public void Close() throws IOException {
        _reader.close();
    }


    public ArrayList<User> GetUsers()  {
        var users = new ArrayList<User>();
        Iterable<CSVRecord> records = null;
        try {
            records = CSVFormat.RFC4180.withHeader(TimeTag, NameTag, RoleTag, EmailTag, PhoneTag).parse(_reader);
        } catch (IOException e) {
            PrintUtilities.PrintErrorLine("Error parsing user csv file");
            return null;
        }
        var isHeaderRecord = true;
        for (CSVRecord record : records) {
            //the first line is also reported as entry. We need to skip it
            if(isHeaderRecord) {
                isHeaderRecord = false;
                continue;
            }
            var name = record.get(NameTag).trim();
            var role = record.get(RoleTag);
            var email = record.get(EmailTag).trim();
            var phone = record.get(PhoneTag).trim();

            users.add(User.Create(UserDb.NewUserId, name, role, email, phone));
        }
        return users;
    }
}
