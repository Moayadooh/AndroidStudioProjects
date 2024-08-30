package com.example.mobileapp;

public class ServerData {
    private static final String ROOT_URL = "";
    private static final String hostname = "localhost";
    private static final String username  = "root";
    private static final String password = "";
    private static final String dbname  = "palm";
    public String getRootUrl(){
        return ROOT_URL;
    }
    public String getHostname(){
        return hostname;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getDbname(){
        return dbname;
    }
}
