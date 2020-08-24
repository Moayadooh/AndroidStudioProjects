package com.example.watercontrolsystemcbr;

public class ServerData {
    private static final String ROOT_URL = "http://moayad.eu5.org/Mobile_App_Server.php";
    private static final String hostname = "localhost";
    private static final String username  = "148523";
    private static final String password = "Moayad258";
    private static final String dbname  = "148523";
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
