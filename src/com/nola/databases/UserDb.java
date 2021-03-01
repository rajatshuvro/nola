package com.nola.databases;

import com.nola.dataStructures.User;
import com.nola.parsers.FlatObjectParser;
import com.nola.utilities.PrintUtilities;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

public class UserDb {
    private HashMap<String, User> _users;
    public static final String NewUserId="New.User";

    public UserDb(Iterable<User> users){
        _users = new HashMap<>();
        for (User user: users) {
            if(user== null) continue;

            if(_users.containsKey(user.Id)){
                PrintUtilities.PrintWarningLine("Duplicate user id:"+ user.Id);
            }
            else _users.put(user.Id, user);
        }
    }
    public int size(){
        return _users.size();
    }

    public User GetUser(String id){
        if(_users.containsKey(id)) return _users.get(id);
        return null;
    }

    public Collection<User> GetAllUsers(){
        return _users.values();
    }

    public HashSet<String> GetIds() {
        var ids = new HashSet<String>();
        ids.addAll(_users.keySet());
        return ids;
    }

    public String GetUserName(String userId) {
        if(_users.containsKey(userId)) return _users.get(userId).Name;
        return null;
    }

    private String GenerateUserId(String name){
        var names = name.split("\\s+");
        var firstName = names[0].toLowerCase();
        var lastName = names.length>1? names[names.length-1].toLowerCase(): null;

        var id = lastName != null ? firstName + '.' + lastName: firstName;

        var i = 1;
        while (_users.containsKey(id)){
            id = lastName != null ? firstName + '.' + lastName + '.' + i : firstName + '.'+ i;
        }
        return id;
    }
    public User AddNewUser(String name, String role, String email, String phone) {
        var id = GenerateUserId(name);

        var user = User.Create(id, name, role, email, phone);
        if(user == null) return null;//invalid user data

        _users.put(user.Id, user);
        return user;
    }

    public void AppendUsers(Iterable<User> users, OutputStream appendStream) throws IOException {
        if(users == null || appendStream == null ) return;
        var  appender = new BufferedWriter(new OutputStreamWriter(appendStream));

        for (var user: users) {
            appender.write(user.toString()+'\n');
            appender.write(FlatObjectParser.RecordSeparator +'\n');
        }
        appender.close();
    }


    public User ResolveUser(String userId, String email) {
        var user = GetUser(userId);
        var emailUsers = GetByEmail(email);

        if (user == null && emailUsers==null) return null;

        if(emailUsers == null) return user;
        if(user == null){
            return emailUsers.size()==1? emailUsers.get(0): null;
        }

        for (var emailUser: emailUsers) {
            if (user.Id == emailUser.Id)
                return user;
        }
        return null;
    }


    private List<User> GetByEmail(String email) {
        var users = new ArrayList<User>();
        for (var user: _users.values()) {
            if(user.Email.equals(email)) users.add(user);
        }
        return users.size()>0? users: null;
    }
}
