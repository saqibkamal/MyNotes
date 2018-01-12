package kamal.saqib.mynotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class individual_notes extends AppCompatActivity implements TextWatcher {
   EditText text1;
    int noteid;
    Set<String> s1;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        text1=(EditText) findViewById(R.id.text1);

        Intent i=getIntent();

        noteid=i.getIntExtra("noteID",-1);
        if(noteid!=-1)
            text1.setText(MainActivity.array.get(noteid));
        text1.addTextChangedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        MainActivity.array.set(noteid,String.valueOf(s));
        MainActivity.arrayAdapter.notifyDataSetChanged();

        if(MainActivity.s==null) MainActivity.s=new HashSet<String>();
        else MainActivity.s.clear();
        MainActivity.s.addAll(MainActivity.array);
        SharedPreferences shared=this.getSharedPreferences("kamal.saqib.mynotes", Context.MODE_PRIVATE);
        shared.edit().remove("notes").apply();
        shared.edit().putStringSet("notes",MainActivity.s).apply();

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
