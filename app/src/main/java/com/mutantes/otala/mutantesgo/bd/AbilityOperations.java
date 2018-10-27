package com.mutantes.otala.mutantesgo.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.mutantes.otala.mutantesgo.bean.Ability;
import com.mutantes.otala.mutantesgo.bean.Mutant;

import java.util.ArrayList;
import java.util.List;

public class AbilityOperations {
    private SimpleBDWrapper simpleBDWrapper;
    private String[] ABILITY_TABLE_COLUMNS = { SimpleBDWrapper.ABILITY_ID,
            SimpleBDWrapper.ABILITY_NAME, SimpleBDWrapper.MUTANTS_ABILITIES};
    private SQLiteDatabase database;

    public AbilityOperations(Context context){
        simpleBDWrapper = new SimpleBDWrapper(context);
    }

    public void open() throws SQLException {
        database = simpleBDWrapper.getWritableDatabase();
    }

    public void close() {
        simpleBDWrapper.close();
    }

    public Ability addAbility(Ability ability) {
        ContentValues values = new ContentValues();
        values.put(SimpleBDWrapper.ABILITY_NAME, ability.getName());
        values.put(SimpleBDWrapper.MUTANTS_ABILITIES, ability.getIdMutant());
        long abilityId = database.insert(SimpleBDWrapper.ABILITY, null, values);

        Cursor cursor = database.query(SimpleBDWrapper.ABILITY, ABILITY_TABLE_COLUMNS,
                SimpleBDWrapper.ABILITY_ID + " = " + abilityId, null,
                null, null, null);

        cursor.moveToFirst();
        Ability newAbility = parseAbility(cursor);
        cursor.close();
        return newAbility;
    }

    public void deleteAbility(Ability ability) {
        long id = ability.getId();
        System.out.println("Removido id: " + id);
        database.delete(SimpleBDWrapper.ABILITY, SimpleBDWrapper.ABILITY_ID
                + " = " + id, null);
    }

    public List getAllAbilityOfMutant(Mutant mutant) {
        List abilityList = new ArrayList();
        Cursor cursor = database.rawQuery("SELECT * FROM " + SimpleBDWrapper.ABILITY + " WHERE "
                + SimpleBDWrapper.MUTANTS_ABILITIES + " = " + mutant.getId(),null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Ability ability = parseAbility(cursor);
            abilityList.add(ability);
            cursor.moveToNext();
        }
        cursor.close();
        return abilityList;
    }

    private Ability parseAbility(Cursor cursor) {
        Ability ability = new Ability();
        ability.setId(cursor.getInt(0));
        ability.setName(cursor.getString(1));
        ability.setIdMutant(cursor.getInt(2));
        return ability;
    }
}
