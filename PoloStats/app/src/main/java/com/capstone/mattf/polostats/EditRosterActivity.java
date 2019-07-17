package com.capstone.mattf.polostats;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditRosterActivity extends AppCompatActivity {

    private Button saveChanges;
    private Button addPlayers;
    private int count = 1;
    private ListView mLayout;
    private EditText mEditText;
    private Button mButton;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> playerNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_roster);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final DatabaseHelper db = new DatabaseHelper(this);
        mLayout = (ListView) findViewById(R.id.rosterLV);
        addPlayers = (Button) findViewById(R.id.addPlayer);
        mEditText = (EditText) findViewById(R.id.editText);
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mEditText.setText("");
                } else {
                    mEditText.setText("Name");
                }
            }
        });

        playerNameList = new ArrayList<>();
        playerNameList.add("Roster");
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, playerNameList);
        mLayout.setAdapter(arrayAdapter);

        mEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (mEditText.getText().toString().equals("") || mEditText.getText().toString().equals("Name")) {
                        Toast.makeText(getApplicationContext(), "You must input a valid name", Toast.LENGTH_SHORT).show();
                    } else {
                        playerNameList.add(mEditText.getText().toString());
                        arrayAdapter.notifyDataSetChanged();
                        mEditText.setText("");

                    }
                    return true;
                }
                return false;
            }
        });

        addPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditText.getText().toString().equals("") || mEditText.getText().toString().equals("Name")) {
                    Toast.makeText(getApplicationContext(), "You must input a valid name", Toast.LENGTH_SHORT).show();
                } else {
                    playerNameList.add(mEditText.getText().toString());
                    arrayAdapter.notifyDataSetChanged();
                    mEditText.setText("");
                    mEditText.requestFocus();
                }
            }
        });

        saveChanges = (Button) findViewById(R.id.button8);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerNameList.size() > 7) {
                    db.createRoster();
                    for (int i = 1; i < playerNameList.size(); i++) {
                        db.insertRoster(playerNameList.get(i));
                    }
                    openRoster();
                } else {
                    Toast.makeText(getApplicationContext(), "You must have at least 7 players on a roster", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
