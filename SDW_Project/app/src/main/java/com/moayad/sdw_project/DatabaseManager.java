package com.moayad.sdw_project;

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
    static final String DatabaseName = "SMS";

    //Table 1 "Accounts"
    static final String AccountsTable = "Accounts";
    static final String AccountID_Col = "ID";
    static final String FirstName_Col = "FirstName";
    static final String LastName_Col = "LastName";
    static final String MobileNo_Col = "MobileNo";
    static final String UserName_Col = "UserName";
    static final String Password_Col = "Password";

    //Table 2 "Students List table"
    static final String StudentsListTable = "StudentsList";
    static final String ID_Col = "ID";
    static final String Name_Col = "StudentName";
    static final String Age_Col = "StudentAge";
    static final String Address_Col = "StudentAddress";

    //Table 3 "Students Information table"
    static final String StudentsInformationTable  = "StudentsInformation";
    static final String StudentID_Col = "StudentID";
    static final String StudentName_Col = "StudentName";
    static final String Period_Col = "Period";
    static final String CourseName_Col = "CourseName";
    static final String Price_Col = "Price";

    //Table 4 "Feedback Table"
    static final String FeedbackTable  = "Feedback";
    static final String FeedbackComments_Col = "FeedbackComments";

    private static class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context) {
            super(context, DatabaseName, null, DatabaseVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("Create Table "+AccountsTable+" ("+AccountID_Col+" Integer Primary Key Autoincrement, "+FirstName_Col+" Text," +
                    ""+LastName_Col+" Text, "+MobileNo_Col+" Text, "+UserName_Col+" Text, "+Password_Col+" Text);");

            db.execSQL("Create Table "+StudentsListTable+" ("+ID_Col+" Integer Primary Key Autoincrement," +
                    " "+Name_Col+" Text, "+Age_Col+" Integer, "+Address_Col+" Text);");

            db.execSQL("Create Table "+StudentsInformationTable+" ("+StudentID_Col+" Integer, "+StudentName_Col+" Text," +
                    " "+Period_Col+" Text, "+CourseName_Col+" Text, "+Price_Col+" Double);");

            db.execSQL("Create Table "+FeedbackTable+" ("+FeedbackComments_Col+" Text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop Table If Exists "+AccountsTable);
            db.execSQL("Drop Table If Exists "+StudentsListTable);
            db.execSQL("Drop Table If Exists "+StudentsInformationTable);
            db.execSQL("Drop Table If Exists "+FeedbackTable);
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
    public Cursor ViewData(String[] Projection,String Selection,String[] SelectionArgs,String SortOrder,String TableName){
        SQLiteQueryBuilder sqlQB = new SQLiteQueryBuilder();
        sqlQB.setTables(TableName);
        Cursor cursor = sqlQB.query(sqlDB,Projection,Selection,SelectionArgs,null,null,SortOrder);
        return cursor;
    }
    public Cursor CheckAccount(String userName, String password){
        String[] columns = {UserName_Col,Password_Col};
        String where = UserName_Col+" = ? and "+Password_Col+" = ?;";
        String[] selectionArgs = {userName,password};
        Cursor data = sqlDB.query(AccountsTable,columns,where,selectionArgs,null,null,null);
        return data;
    }
}
