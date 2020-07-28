package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class DatabaseManager {
    DatabaseManager(Context context){
        DatabaseHelper db = new DatabaseHelper(context);
        sqlDB = db.getWritableDatabase();
    }
    private SQLiteDatabase sqlDB;
    static final String DatabaseName = "Students";
    static final String TableName = "Accounts";
    static final String ID_Col = "ID";
    static final String Username_Col = "Username";
    static final String Password_Col = "Password";
    static final int DatabaseVersion = 3;

    private static class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context) {
            super(context, DatabaseName, null, DatabaseVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("Create Table "+TableName+" ("+ID_Col+" Integer Primary Key Autoincrement, "+Username_Col+" Text, "+Password_Col+" Text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop Table If Exists "+TableName);
            onCreate(db);
        }

    }
    public long InsertData(ContentValues values){
        long ID = sqlDB.insert(TableName,null,values);
        return ID;
    }
    public Cursor query(String[] Projection,String Selection,String[] SelectionArgs,String SortOrder){
        SQLiteQueryBuilder sqlQB = new SQLiteQueryBuilder();
        sqlQB.setTables(TableName);

        Cursor cursor = sqlQB.query(sqlDB,Projection,Selection,SelectionArgs,null,null,SortOrder);
        return cursor;
    }
    public int DeleteData(String Selection,String[] SelectionArgs){
        int count = sqlDB.delete(TableName,Selection,SelectionArgs);
        return count;
    }
    public int UpdateData(ContentValues values,String Selection,String[] SelectionArgs){
        int count = sqlDB.update(TableName,values,Selection,SelectionArgs);
        return count;
    }

}
