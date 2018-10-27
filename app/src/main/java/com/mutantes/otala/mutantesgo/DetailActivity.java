package com.mutantes.otala.mutantesgo;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mutantes.otala.mutantesgo.bd.AbilityOperations;
import com.mutantes.otala.mutantesgo.bd.MutantsOperations;
import com.mutantes.otala.mutantesgo.bean.Ability;
import com.mutantes.otala.mutantesgo.bean.Mutant;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    MutantsOperations mutantsOperations;
    AbilityOperations abilityOperations;
    Button bUp;
    Button bDel;
    int mutantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail);

        mutantsOperations = new MutantsOperations(this);
        abilityOperations = new AbilityOperations(this);

        searchMutant();

        bUp = (Button) findViewById(R.id.upd);
        bDel = (Button) findViewById(R.id.delete);

        bUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //To-Do
            }
        });

        bDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteMutant();
            }
        });
    }

    private void deleteMutant() {
        mutantsOperations.open();
        abilityOperations.open();

        Mutant mutant = mutantsOperations.getMutant(mutantId + 1);
        List<Ability> abilities = abilityOperations.getAllAbilityOfMutant(mutant);

        for(Ability a : abilities)
        {
            abilityOperations.deleteAbility(a);
        }

        mutantsOperations.deleteMutant(mutant);
        Intent register = new Intent(this, DashboardActivity.class);
        startActivity(register);

        mutantsOperations.close();
        abilityOperations.close();
    }

    private void searchMutant() {
        mutantsOperations.open();
        abilityOperations.open();

        mutantId = 0;
        Mutant mutant = null;
        List<Ability> abilities;
        String abilitiesToScreen = "";

        TextView mutantName = (TextView) findViewById(R.id.textName);
        TextView mutantAbilities = (TextView) findViewById(R.id.abilities);

        Intent it = getIntent();

        if(it != null)
            mutantId = it.getIntExtra("idMutant",-1);

        mutant = mutantsOperations.getMutant(mutantId + 1);

        mutantName.setText(mutant.getName());

        abilities = abilityOperations.getAllAbilityOfMutant(mutant);

        for(Ability a : abilities)
        {
            abilitiesToScreen += a.getName() + " \n";
        }

        mutantAbilities.setText(abilitiesToScreen);

        mutantsOperations.close();
        abilityOperations.close();
    }
}
