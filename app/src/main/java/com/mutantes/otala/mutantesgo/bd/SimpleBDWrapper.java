package com.mutantes.otala.mutantesgo.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class SimpleBDWrapper extends SQLiteOpenHelper {

    public static final String MUTANTS = "Mutants" ;
    public static final String MUTANTS_ID = "_id" ;
    public static final String MUTANTS_NAME = "_name" ;
    public static final String MUTANTS_ABILITIES = "_ability_id" ;

    public static final String ABILITY = "Ability" ;
    public static final String ABILITY_ID = "_id" ;
    public static final String ABILITY_NAME = "_name" ;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Mutants.db" ;

    private static final String DATABASE_CREATE = "CREATE TABLE " + MUTANTS + "(" + MUTANTS_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MUTANTS_NAME + " TEXT NOT NULL);";

    private static final String DATABASE_CREATE_ABILITY = "CREATE TABLE " + ABILITY + "(" +
            ABILITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ABILITY_NAME  +
            " TEXT NOT NULL,  " + MUTANTS_ABILITIES + "  INTEGER, FOREIGN KEY(" + MUTANTS_ABILITIES + ") REFERENCES " +
            MUTANTS + "(" + MUTANTS_ID + ") );";

    public SimpleBDWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE_ABILITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MUTANTS);
        db.execSQL("DROP TABLE IF EXISTS " + ABILITY);
        onCreate(db);
    }
}
