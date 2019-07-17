package com.capstone.mattf.polostats;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class NewGameActivity extends AppCompatActivity {
    private Button newGame;
    private EditText opponentName;
    private DatabaseHelper db;
    private String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        db = new DatabaseHelper(this);
        newGame = (Button) findViewById(R.id.submitNewGame);
        opponentName = (EditText) findViewById(R.id.opponentName);

        opponentName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    opponentName.setText("");
                } else {
                    opponentName.setText("Opponent Name");
                }
            }
        });

        opponentName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (opponentName.getText().toString().equals("") || opponentName.getText().toString().equals("Name")) {
                        Toast.makeText(getApplicationContext(), "You must input a valid name", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            tableName = db.createScoreSheet(opponentName.getText().toString());
                            addRosterData(tableName);
                            openGame(tableName);
                        } catch (SQLiteException e) {
                            openEditRosterActivity();
                        }

                    }
                    return true;
                }
                return false;
            }
        });

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
                    tableName = db.createScoreSheet(opponentName.getText().toString());
                    addRosterData(tableName);
                    openGame(tableName);
//                } catch (SQLiteException e) {
//                    openEditRosterActivity();
//                }
            }
        });
    }


    private void addRosterData(String table) {
        Cursor cursor = db.viewData("Roster");
        ArrayList<String> athleteNames = new ArrayList<>();
        while (cursor.moveToNext()) {
            athleteNames.add(cursor.getString(1));
        }
        for (int i = 0; i < athleteNames.size(); i++) {
            db.insertRosterSCS(table, athleteNames.get(i));
        }
    }

    public void openGame(String tableName) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("TableName", tableName);
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

    public void openEditRosterActivity() {
        Intent intent = new Intent(this, EditRosterActivity.class);
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
