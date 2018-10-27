package com.mutantes.otala.mutantesgo;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.mutantes.otala.mutantesgo.bd.AbilityOperations;
import com.mutantes.otala.mutantesgo.bd.MutantsOperations;
import com.mutantes.otala.mutantesgo.bean.Ability;
import com.mutantes.otala.mutantesgo.bean.Mutant;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private MutantsOperations mutantsOperations;
    private AbilityOperations abilityOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail);

        int mutantId = 0;
        Mutant mutant = null;
        List<Ability> abilities;
        String abilitiesToScreen = "";

        mutantsOperations = new MutantsOperations(this);
        mutantsOperations.open();

        abilityOperations = new AbilityOperations(this);
        abilityOperations.open();

        TextView mutantName = (TextView) findViewById(R.id.textName);
        TextView mutantAbilities = (TextView) findViewById(R.id.abilityText);

        Intent it = getIntent();

        if(it != null)
            mutantId = it.getIntExtra("idMutant",-1);

        mutant = mutantsOperations.getMutant(mutantId+1);

        mutantName.setText(mutant.getName());

        abilities = abilityOperations.getAllAbilityOfMutant(mutant);

        for(Ability a : abilities)
        {
            abilitiesToScreen += a.getName() + "\n";
        }
    }
}
