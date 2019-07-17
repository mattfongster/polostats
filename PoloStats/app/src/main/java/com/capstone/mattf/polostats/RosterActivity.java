package com.capstone.mattf.polostats;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RosterActivity extends AppCompatActivity {
    private Button editRoster;
    private ArrayList<String> playerNames;
    private ArrayAdapter<String> arrayAdapter;
    private DatabaseHelper db;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        db = new DatabaseHelper(this);
        playerNames = new ArrayList<>();
        listView = (ListView) findViewById(R.id.rosterLVID);
        viewData();
        editRoster = (Button) findViewById(R.id.button3);
        editRoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditRoster();
            }
        });
    }

    private void viewData() {
        try {
            Cursor cursor = db.viewData("Roster");
            int count = 1;
            while (cursor.moveToNext()) {
                playerNames.add(count + ". " + cursor.getString(1));
                count++;
            }
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playerNames);
            listView.setAdapter(arrayAdapter);
            count = 0;
        } catch (SQLException e) {
            openEditRoster();
        }
    }

    public void openEditRoster() {
        Intent intent = new Intent(this, EditRosterActivity.class);
        startActivity(intent);
    }

    public void openMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openGameArchive() {
        Intent intent = new Intent(this, GameArchiveActivity.class);
        startActivity(intent);
    }

    public void openRoster() {
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
        } else if (id == R.id.gamearchivemenu) {
            openGameArchive();
        } else if (id == R.id.rostermenu) {
            openRoster();
        }

        return super.onOptionsItemSelected(item);
    }
}
