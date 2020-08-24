package com.example.watercontrolsystemcbr;

public class ServerData {
    private static final String ROOT_URL = "http://moayad.eu5.org/Application_Server.php";
    private static final String ROOT_URL1 = "http://moayad.eu5.org/Store_Plant_Age.php";
    private static final String ROOT_URL2 = "http://moayad.eu5.org/Retrieve_Plant_Age.php";
    private static final String ROOT_URL3 = "http://moayad.eu5.org/Retrieve_Plants_Names.php";
    private static final String hostname = "localhost";
    private static final String username  = "148523";
    private static final String password = "Moayad258";
    private static final String dbname  = "148523";
    public String getRootUrl(){
        return ROOT_URL;
    }
    public String getRootUrl1(){
        return ROOT_URL1;
    }
    public String getRootUrl2(){
        return ROOT_URL2;
    }
    public String getRootUrl3(){
        return ROOT_URL3;
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
