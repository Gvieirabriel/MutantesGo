package com.mutantes.otala.mutantesgo;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mutantes.otala.mutantesgo.bd.MutantsOperations;
import com.mutantes.otala.mutantesgo.bean.Mutant;

import java.util.ArrayList;
import java.util.List;

public class MutantListActivity extends ListActivity {

    private MutantsOperations mutantsOperations;
    private List<Mutant> mutantList;
    private List<String> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mutantsOperations = new MutantsOperations(this);
        mutantsOperations.open();

        mutantList = mutantsOperations.getAllMutants();
        nameList = new ArrayList<>();

        for(Mutant m : mutantList)
        {
            nameList.add(m.getName());
        }

        if(!nameList.isEmpty())
            displayResultList();
        else
            Toast.makeText(MutantListActivity.this, "Não tem registro de mutantes", Toast.LENGTH_SHORT).show();
    }

    private void displayResultList() {
        ArrayAdapter<String> array = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList);
        setListAdapter(array);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l,v,position,id);
        nextActivity(l.getAdapter().getItem(position).toString());
    }

    public void nextActivity(String name) {
        Intent register = new Intent(this, DetailActivity.class);
        register.putExtra("nameMutant", name);
        startActivity(register);
    }

}
