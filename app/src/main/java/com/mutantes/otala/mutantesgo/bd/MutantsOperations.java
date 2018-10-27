package com.mutantes.otala.mutantesgo.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.mutantes.otala.mutantesgo.bean.Mutant;

import java.util.ArrayList;
import java.util.List;

public class MutantsOperations {

    private SimpleBDWrapper simpleBDWrapper;
    private String[] MUTANTS_TABLE_COLUMNS = { SimpleBDWrapper.MUTANTS_ID,
            SimpleBDWrapper.MUTANTS_NAME};
    private SQLiteDatabase database;

    public MutantsOperations(Context context){
        simpleBDWrapper = new SimpleBDWrapper(context);
    }

    public void open() throws SQLException {
        database = simpleBDWrapper.getWritableDatabase();
    }

    public void close() {
        simpleBDWrapper.close();
    }

    public Mutant addMutant(Mutant mutant) {
        ContentValues values = new ContentValues();
        values.put(SimpleBDWrapper.MUTANTS_NAME, mutant.getName());
        long mutantId = database.insert(SimpleBDWrapper.MUTANTS, null, values);

        Cursor cursor = database.query(SimpleBDWrapper.MUTANTS, MUTANTS_TABLE_COLUMNS,
                SimpleBDWrapper.MUTANTS_ID + " = " + mutantId, null,
                null, null, null);

        cursor.moveToFirst();
        Mutant newMutant = parseMutant(cursor);
        cursor.close();
        return newMutant;
    }

    public Mutant getMutant(String name) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + SimpleBDWrapper.MUTANTS + " WHERE " +
                SimpleBDWrapper.MUTANTS_NAME + " = '" + name + "'", null);
        cursor.moveToFirst();
        Mutant newMutant = parseMutant(cursor);
        cursor.close();
        return newMutant;
    }

    public boolean hasMutant(Mutant mutant) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + SimpleBDWrapper.MUTANTS + " WHERE " +
                SimpleBDWrapper.MUTANTS_NAME + " = '" + mutant.getName() + "'", null);

        if(!cursor.moveToFirst())
            return true;
        cursor.close();
        return false;
    }

    public void deleteMutant(Mutant mutant) {
        long id = mutant.getId();
        System.out.println("Removido id: " + id);
        database.delete(SimpleBDWrapper.MUTANTS, SimpleBDWrapper.MUTANTS_ID
                + " = " + id, null);
    }

    public List getAllMutants() {
        List mutants = new ArrayList();
        Cursor cursor = database.query(SimpleBDWrapper.MUTANTS, MUTANTS_TABLE_COLUMNS,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Mutant mutant = parseMutant(cursor);
            mutants.add(mutant);
            cursor.moveToNext();
        }

        cursor.close();
        return mutants;
    }

    private Mutant parseMutant(Cursor cursor) {
        Mutant mutant = new Mutant();
        mutant.setId(cursor.getInt(0));
        mutant.setName(cursor.getString(1));
        return mutant;
    }
}
