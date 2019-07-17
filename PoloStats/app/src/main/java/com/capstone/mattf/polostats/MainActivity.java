package com.capstone.mattf.polostats;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button newgame;
    private Button gamearchive;
    private Button roster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DatabaseHelper db = new DatabaseHelper(this);
//        db.dropTable("Roster");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newgame = (Button) findViewById(R.id.button5);
        newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewGame();
            }
        });

        gamearchive = (Button) findViewById(R.id.button7);
        gamearchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameArchive();
            }
        });

        roster = (Button) findViewById(R.id.button6);
        roster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRoster();

            }
        });
    }

    public void openNewGame(){
        Intent intent = new Intent(this, NewGameActivity.class);
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
            return true;
        } else if (id == R.id.gamearchivemenu){
            openGameArchive();
        } else if (id == R.id.rostermenu){
            openRoster();
        }

        return super.onOptionsItemSelected(item);
    }
}
