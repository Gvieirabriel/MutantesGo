package com.mutantes.otala.mutantesgo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mutantes.otala.mutantesgo.bd.AbilityOperations;
import com.mutantes.otala.mutantesgo.bd.MutantsOperations;
import com.mutantes.otala.mutantesgo.bean.Ability;
import com.mutantes.otala.mutantesgo.bean.Mutant;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private Button button;
    private MutantsOperations mutantsOperations;
    private AbilityOperations abilityOperations;
    private AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button = (Button) findViewById(R.id.button);
        mutantsOperations = new MutantsOperations(this);
        abilityOperations = new AbilityOperations(this);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveMutant();
            }
        });
    }

    private void saveMutant() {
        mutantsOperations.open();
        abilityOperations.open();

        EditText name = (EditText) findViewById(R.id.nameText);
        Mutant mutant = new Mutant();
        mutant.setName(name.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado");
        if(mutantsOperations.hasMutant(mutant) != false)
        {
            mutant = mutantsOperations.addMutant(mutant);
        }
        else
        {
            builder.setMessage("Mutante já cadastrado");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) { }
            });

            alert = builder.create();
            alert.show();
            return;
        }

        EditText abilitiesMultiLines  = (EditText) findViewById(R.id.abilityText);
        String abilities = abilitiesMultiLines.getText().toString();
        String[] abilitiesList;
        String delimiter = "\n";

        abilitiesList = abilities.split(delimiter);

        for(String ability : abilitiesList)
        {
            Ability a = new Ability();
            a.setIdMutant(mutant.getId());
            a.setName(ability);

            List<Ability> abilitylistOfMutant = abilityOperations.getAllAbilityOfMutant(mutant);

            for (Ability b : abilitylistOfMutant)
            {
                if(!b.getName().equals(a.getName()))
                {
                    abilityOperations.addAbility(a);
                }
            }

            if(abilitylistOfMutant.isEmpty())
                abilityOperations.addAbility(a);
        }

        builder.setMessage("Mutante cadastrado com sucesso");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) { }
        });

        alert = builder.create();
        alert.show();

        mutantsOperations.close();
        abilityOperations.close();
    }

}
