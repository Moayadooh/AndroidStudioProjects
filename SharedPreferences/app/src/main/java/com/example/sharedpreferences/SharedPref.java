package com.example.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    SharedPreferences sharedPreferences;
    public SharedPref(Context context){
        sharedPreferences = context.getSharedPreferences("myRef",Context.MODE_PRIVATE);
    }
    public void SaveData(String UserName,String Password){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserName",UserName);
        editor.putString("Password",Password);
        editor.commit();
    }
    public String LoadData(){
        String fileContent = "Username: "+sharedPreferences.getString("UserName","No name");
        fileContent += ",Password: "+sharedPreferences.getString("Password","No password");
        return fileContent;
    }
}
