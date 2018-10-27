package com.mutantes.otala.mutantesgo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mutantes.otala.mutantesgo.bd.AbilityOperations;
import com.mutantes.otala.mutantesgo.bd.MutantsOperations;
import com.mutantes.otala.mutantesgo.bean.Ability;
import com.mutantes.otala.mutantesgo.bean.Mutant;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    MutantsOperations mutantsOperations;
    AbilityOperations abilityOperations;
    Button bUp;
    Button bDel;
    String mutantNameS;
    AlertDialog alert;

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
                updateMutant();
            }
        });

        bDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteMutant();
            }
        });
    }

    private void updateMutant() {

        mutantsOperations.open();
        abilityOperations.open();

        EditText name = (EditText) findViewById(R.id.textName);
        Mutant mutant = mutantsOperations.getMutant(mutantNameS);
        String lastName = mutant.getName();
        mutant.setName(name.getText().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado");
        if(mutantsOperations.hasMutant(mutant) != false)
        {
            mutantsOperations.updateMutant(mutant);
        }
        else
        {
            if(!lastName.equals(mutant.getName())) {
                builder.setMessage("Nome de mutante já está em uso");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

                alert = builder.create();
                alert.show();
                return;
            }
        }

        EditText abilitiesMultiLines  = (EditText) findViewById(R.id.abilities);
        String abilities = abilitiesMultiLines.getText().toString();
        abilities = abilities.replaceAll(" ","");
        String[] abilitiesList;
        String delimiter = "\n";

        abilitiesList = abilities.split(delimiter);

        for(String ability : abilitiesList)
        {
            Ability a = new Ability();
            a.setIdMutant(mutant.getId());
            a.setName(ability);

            List<Ability> abilitylistOfMutant = abilityOperations.getAllAbilityOfMutant(mutant);

            List<String> abilitiesToverify = new ArrayList<>();

            for(Ability ab : abilitylistOfMutant)
                abilitiesToverify.add(ab.getName());

            if(!abilitiesToverify.contains(a.getName()))
                abilityOperations.addAbility(a);
            //else
            //abilityOperations.updateAbility(a);
        }

        Toast.makeText(DetailActivity.this, "Mutante atualizado com sucesso", Toast.LENGTH_SHORT).show();

        mutantsOperations.close();
        abilityOperations.close();

        this.finish();
    }

    private void deleteMutant() {
        mutantsOperations.open();
        abilityOperations.open();

        Mutant mutant = mutantsOperations.getMutant(mutantNameS);
        List<Ability> abilities = abilityOperations.getAllAbilityOfMutant(mutant);

        for(Ability a : abilities)
        {
            abilityOperations.deleteAbility(a);
        }

        mutantsOperations.deleteMutant(mutant);

        mutantsOperations.close();
        abilityOperations.close();

        this.finish();
    }

    private void searchMutant() {
        mutantsOperations.open();
        abilityOperations.open();

        mutantNameS = "";
        Mutant mutant = null;
        List<Ability> abilities;
        String abilitiesToScreen = "";

        EditText mutantName = (EditText) findViewById(R.id.textName);
        EditText mutantAbilities = (EditText) findViewById(R.id.abilities);

        Intent it = getIntent();

        if(it != null)
            mutantNameS = it.getStringExtra("nameMutant");

        mutant = mutantsOperations.getMutant(mutantNameS);

        mutantName.setText(mutant.getName());

        abilities = abilityOperations.getAllAbilityOfMutant(mutant);

        for(Ability a : abilities)
        {
            abilitiesToScreen += a.getName() + " \n";
        }

        abilitiesToScreen = abilitiesToScreen.substring(0, abilitiesToScreen.length() - 1);

        mutantAbilities.setText(abilitiesToScreen);

        mutantsOperations.close();
        abilityOperations.close();
    }
}
