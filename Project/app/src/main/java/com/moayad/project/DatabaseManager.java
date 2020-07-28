package com.moayad.project;

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
    public SQLiteDatabase sqlDB;
    static final int DatabaseVersion = 3;
    static final String DatabaseName = "MyDB";

    static final String TableName1 = "Accounts";
    static final String ID_Col = "ID";
    static final String FirstName_Col = "FirstName";
    static final String LastName_Col = "LastName";
    static final String VisaAccount_Col = "VisaAccount";
    static final String UserName_Col = "UserName";
    static final String Password_Col = "Password";

    static final String TableName2 = "MobileNoLists";
    static final String UserID_Col = "UserID";
    static final String MobileNo_Col = "MobileNo";

    static final String TableName3 = "RechargeData";
    static final String MobileNoID_Col = "MobileNoID";
    static final String Balance_Col = "Balance";
    static final String Date_Col = "Date";
    static final String Time_Col = "Time";

    static final String TableName4 = "Plans";
    static final String Plan1_Col = "Plan1";
    static final String Plan2_Col = "Plan2";
    static final String Plan3_Col = "Plan3";

    private static class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context) {
            super(context, DatabaseName, null, DatabaseVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("Create Table "+TableName1+" ("+ID_Col+" Integer Primary Key Autoincrement, "+FirstName_Col+" Text, "+LastName_Col+" Text, "
                    +VisaAccount_Col+" Text, "+UserName_Col+" Text, "+Password_Col+" Text);");
            db.execSQL("Create Table "+TableName2+" ("+UserID_Col+" Text, "+MobileNo_Col+" Text);");
            db.execSQL("Create Table "+TableName3+" ("+MobileNoID_Col+" Text, "+Balance_Col+" Text,"+Date_Col+" Text, " +Time_Col+" Text);");
            db.execSQL("Create Table "+TableName4+" ("+Plan1_Col+" Text, "+Plan2_Col+" Text,"+Plan3_Col+" Text);");
            ContentValues contentValues = new ContentValues();
            contentValues.put(Plan1_Col,"1 OMR");
            contentValues.put(Plan2_Col,"3 OMR");
            contentValues.put(Plan3_Col,"5 OMR");
            db.insert(TableName4,null,contentValues);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop Table If Exists "+TableName1);
            db.execSQL("Drop Table If Exists "+TableName2);
            db.execSQL("Drop Table If Exists "+TableName3);
            onCreate(db);
        }

    }
    public long InsertData(ContentValues values,String TableName){
        long ID = sqlDB.insert(TableName,null,values);
        return ID;
    }
    public int DeleteData(String Selection,String[] SelectionArgs,String TableName){
        int count = sqlDB.delete(TableName,Selection,SelectionArgs);
        return count;
    }
    public int UpdateData(ContentValues values,String Selection,String[] SelectionArgs,String TableName){
        int count = sqlDB.update(TableName,values,Selection,SelectionArgs);
        return count;
    }
    public Cursor CheckAccount(String userName, String password){
        String[] columns = {UserName_Col,Password_Col};
        String where = UserName_Col+" = ? and "+Password_Col+" = ?;";
        String[] selectionArgs = {userName,password};
        Cursor data = sqlDB.query(TableName1,columns,where,selectionArgs,null,null,null);
        return data;
    }
    public Cursor GetData(String[] Projection,String Selection,String[] SelectionArgs,String SortOrder,String TableName){
        SQLiteQueryBuilder sqlQB = new SQLiteQueryBuilder();
        sqlQB.setTables(TableName);
        Cursor cursor = sqlQB.query(sqlDB,Projection,Selection,SelectionArgs,null,null,SortOrder);
        return cursor;
    }
}
