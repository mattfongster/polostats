package com.capstone.mattf.polostats;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class GameArchiveActivity extends AppCompatActivity {

    private ArrayList<String> tableNames;
    private ArrayList<String> sqlTableNames;
    private ArrayAdapter<String> arrayAdapter;
    private DatabaseHelper db;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_archive);
        db = new DatabaseHelper(this);
        tableNames = new ArrayList<>();
        sqlTableNames = new ArrayList<>();
        listView = (ListView) findViewById(R.id.gameArchiveLV);
        viewData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openScoreSheet(sqlTableNames.get(position));
            }
        });
    }

    private void viewData() {
        try {
            Cursor cursor = db.grabTableNames();
            while (cursor.moveToNext()) {
                if(cursor.getString(0).equalsIgnoreCase("Roster") || cursor.getString(0).equalsIgnoreCase("sqlite_sequence")
                || cursor.getString(0).equalsIgnoreCase("android_metadata")){
                    String doNothing = "do nothing";
                } else {
                    sqlTableNames.add(cursor.getString(0));
                    String s = cursor.getString(0);
                    String[] t = s.split("_");
                    StringBuilder builder = new StringBuilder();
                    for(int i = 0; i < t.length; i++){
                        if(i == 0){
                            builder.append(t[i] + " - ");
                        } else if (i == 2 || i == 3){
                            builder.append(t[i] + " ");
                        } else if (i == 4){
                            builder.append(t[i]);
                        }
                    }
                    tableNames.add(builder.toString());
                }
            }
            arrayAdapter = new ArrayAdapter<>(this, R.layout.scoresheet_textview, tableNames);
            listView.setAdapter(arrayAdapter);
        } catch (SQLException e) {
            openMainMenu();
        }
    }

    public void openScoreSheet(String tableName){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("TableName", tableName);
        startActivity(intent);
    }

    public void openMainMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openGameArchive(){
        Intent intent = new Intent(this, GameArchiveActivity.class);
        startActivity(intent);
    }

    public void openRoster(){
        Intent intent = new Intent(this, RosterActivity.class);
        startActivity(intent);
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
        if (id == R.id.homemenu) {
            openMainMenu();
        } else if (id == R.id.gamearchivemenu){
            openGameArchive();
        } else if (id == R.id.rostermenu){
            openRoster();
        }

        return super.onOptionsItemSelected(item);
    }
}
