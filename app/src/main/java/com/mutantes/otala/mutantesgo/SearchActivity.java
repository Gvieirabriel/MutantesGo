package com.mutantes.otala.mutantesgo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mutantes.otala.mutantesgo.bd.AbilityOperations;
import com.mutantes.otala.mutantesgo.bd.MutantsOperations;
import com.mutantes.otala.mutantesgo.bean.Ability;
import com.mutantes.otala.mutantesgo.bean.Mutant;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private Button search;
    private EditText textToSearch;
    private TextView textResult;
    private RadioGroup radioGroup;
    private MutantsOperations mutantsOperations;
    private AbilityOperations abilityOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search);

        mutantsOperations = new MutantsOperations(this);
        abilityOperations = new AbilityOperations(this);

        search = (Button) findViewById(R.id.searchB);
        radioGroup = (RadioGroup) findViewById(R.id.radioG);
        textResult = (TextView) findViewById(R.id.listResult);

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.nom)
                    searchByName();
                else
                    searchByAbility();
                textToSearch.setText("");
            }
        });
    }

    private void searchByAbility() {
        mutantsOperations.open();
        abilityOperations.open();
        String resultToScreen = "";
        textToSearch = findViewById(R.id.searchText);
        List<Mutant> mutantsResult = mutantsOperations.searchMutantAbility(textToSearch.getText().toString());
        if (!mutantsResult.isEmpty()) {
            for (Mutant m : mutantsResult) {
                resultToScreen += m.getName() + " \n";
            }
            resultToScreen = resultToScreen.substring(0, resultToScreen.length() - 1);
            textResult.setText(resultToScreen);
        } else
        {
            textResult.setText("Nenhum registro encontrado");
        }
        mutantsOperations.close();
        abilityOperations.close();
    }

    private void searchByName() {
        mutantsOperations.open();
        abilityOperations.open();
        String resultToScreen = "";
        textToSearch = findViewById(R.id.searchText);
        List<Mutant> mutantsResult = mutantsOperations.searchMutantName(textToSearch.getText().toString());
        if (!mutantsResult.isEmpty()) {
            for (Mutant m : mutantsResult) {
                resultToScreen += m.getName() + " \n";
            }
            resultToScreen = resultToScreen.substring(0, resultToScreen.length() - 1);
            textResult.setText(resultToScreen);
        } else
        {
            textResult.setText("Nenhum registro encontrado");
        }
        mutantsOperations.close();
        abilityOperations.close();
    }
}
