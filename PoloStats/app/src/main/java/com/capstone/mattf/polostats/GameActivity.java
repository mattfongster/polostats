package com.capstone.mattf.polostats;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private ArrayList<Player> players;
    private ListView listView;
    private Player player;
    private String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        db = new DatabaseHelper(this);
        players = new ArrayList<>();
        players.add(new Player("#", "Name", "G", "S/A", "%", "A",
                "DEX", "EX", "S", "B"));
        Intent i = getIntent();
        tableName = i.getStringExtra("TableName");
        Cursor cursor = db.viewData(tableName);
        int rows = cursor.getCount();
        if(rows == 0){
            openRoster();
        } else {
            while(cursor.moveToNext()){
                player = new Player(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7),
                        cursor.getString(8), cursor.getString(9));
                players.add(player);
            }
            PlayerListAdapter adapter = new PlayerListAdapter(this, R.layout.activity_game, players);
            listView = (ListView) findViewById(R.id.gameListView);
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    openEditPlayerActivity(players.get(position));
                }
            }
        });
    }


    public void openEditPlayerActivity(Player name){
        Intent intent = new Intent(this, EditPlayerActivity.class);
        intent.putExtra("Player", name);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        intent.putExtra("TableName", tableName);
        startActivity(intent);
    }

    public void openMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openRoster() {
        Intent intent = new Intent(this, RosterActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.endGame) {
            openMainMenu();
        }
        return super.onOptionsItemSelected(item);
    }
}
