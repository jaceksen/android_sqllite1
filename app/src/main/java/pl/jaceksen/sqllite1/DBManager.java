package pl.jaceksen.sqllite1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by jsen on 22.03.17.
 */

public class DBManager {

    //konstruktor
    public DBManager(Context context){
        DatabaseHelperUser db = new DatabaseHelperUser(context);
        sqlDB=db.getWritableDatabase();
    }

    private SQLiteDatabase sqlDB;
    static final String DBName = "Students";
    static final String TableName = "Logins";
    static final String ColUserName = "UserName";
    static final String ColPassword = "Password";
    static final int DBVersion = 1;

    //create table
    static final String CreateTable = "Create table IF NOT EXISTS " + TableName +
            "(ID integer PRIMARY KEY AUTOINCREMENT," + ColUserName + " text," +
            ColPassword + " text)";

    static class DatabaseHelperUser extends SQLiteOpenHelper{

        Context context;

        //konstruktor
        DatabaseHelperUser(Context context){
            super(context,DBName,null,DBVersion);
            this.context=context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
           db.execSQL(CreateTable);
            Toast.makeText(context,"Table is created",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop table IF EXISTS " + TableName);
            onCreate(db);
        }
    }

}
