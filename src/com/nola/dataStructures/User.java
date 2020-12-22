package com.nola.dataStructures;

import com.nola.parsers.ParserUtilities;
import com.nola.utilities.FormatUtilities;

import java.util.HashSet;

public class User{
    public final String Id;
    public final String Name;
    public final String Role;
    public final String Email;
    public final String Phone;

    private User(String id, String name, String role, String email, String phn){
        Id = id;
        Name = name;
        Role = role;
        Email = email;
        Phone = phn;
    }

    public String GetId(){
        return Id;
    }
    public static User Create(String id, String name, String role, String email, String phn){
        if(!IsValid(id, name, role, email, phn)) return null;
        return new User(id, name, role, email, phn);
    }

    private static boolean IsValid(String id, String name, String role, String email, String phn) {
        if(ParserUtilities.IsNullOrEmpty(name)){
            System.out.println("User name cannot be empty");
            return false;
        }
        if(!IsValidRole(role)){
            System.out.println("Role has to be one of:"+ String.join("/",RoleTags));
            return false;
        }
        if(!FormatUtilities.IsValidEmail(email)){
            System.out.println("Please provide a valid email address. e.g. user@onkur.com");
            return false;
        }
        if(!FormatUtilities.IsValidPhoneNumber(phn)){
            System.out.println("Please provide a valid phone number. e.g. XXX-XXX-XXXX");
            return false;
        }
        return !ParserUtilities.IsNullOrEmpty(id);
    }

    @Override
    public String toString(){
        return    "Id:       "+ Id +
                "\nName:     "+ Name +
                "\nRole:     "+ Role +
                "\nEmail:    "+ Email+
                "\nPhone:    "+ Phone;
    }

    public String GetContent(){
        return   Id +"\n"+ Name +"\n"+ Role +"\n"+ Email+"\n"+ Phone;
    }

    //static fields
    public static final String StudentRoleTag = "Student";
    public static final String CitizenRoleTag = "Citizen";
    public static final String VolunteerRoleTag = "Volunteer";
    public static final String TeacherRoleTag = "Teacher";
    public static final String AdminRoleTag = "Administrator";

    public static final HashSet<String> RoleTags = new HashSet<>(){{
        add(StudentRoleTag);
        add(CitizenRoleTag);
        add(VolunteerRoleTag);
        add(TeacherRoleTag);
        add(AdminRoleTag);
    }};
    private static boolean IsValidRole(String role) {
        return RoleTags.contains(role);
    }
}
