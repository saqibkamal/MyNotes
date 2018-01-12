package kamal.saqib.mynotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> array=new ArrayList<>();
    static Set<String> s;
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView listview= (ListView) findViewById(R.id.listview);

        SharedPreferences shared=this.getSharedPreferences("kamal.saqib.mynotes", Context.MODE_PRIVATE);

       //shared.edit().clear().commit();

        s= shared.getStringSet("notes",null);
        array.clear();

        if(s!=null){
            array.addAll(s);

        }
        else{
            array.add("First Data");
            s=new HashSet<>();
            s.addAll(array);
            shared.edit().putStringSet("notes",s).apply();
        }



        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);

        listview.setAdapter(arrayAdapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getApplicationContext(),individual_notes.class);
                i.putExtra("noteID",position);
                startActivity(i);
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                new AlertDialog.Builder(MainActivity.this).
                        setIcon(android.R.drawable.alert_dark_frame).
                        setTitle("Delete").
                        setMessage("Want To delete").
                        setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                array.remove(position);
                                if (s == null) s = new HashSet<String>();
                                else s.clear();
                                s.addAll(array);
                                SharedPreferences shared = MainActivity.this.getSharedPreferences("kamal.saqib.mynotes", Context.MODE_PRIVATE);
                                shared.edit().remove("notes").apply();
                                shared.edit().putStringSet("notes", s).apply();
                                arrayAdapter.notifyDataSetChanged();

                            }


                        }).
                        setNegativeButton("No", null).show();


                return true;
            }
        });







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {

            array.add("");
            if(s==null)  s=new HashSet<String>();
            else s.clear();
            s.addAll(array);
            SharedPreferences shared=this.getSharedPreferences("kamal.saqib.mynotes", Context.MODE_PRIVATE);
            shared.edit().remove("notes").apply();
            shared.edit().putStringSet("notes",s).apply();
            arrayAdapter.notifyDataSetChanged();
            Intent i=new Intent(getApplicationContext(),individual_notes.class);
            i.putExtra("noteID",array.size()-1);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
