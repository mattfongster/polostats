package com.capstone.mattf.polostats;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditPlayerActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Button saveChanges;
    private TextView playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player);
        db = new DatabaseHelper(this);
        db.getWritableDatabase();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        Intent i = getIntent();
        Player player = (Player) i.getSerializableExtra("Player");
        final String tableName = i.getStringExtra("TableName");
        playerName = (TextView) findViewById(R.id.epName);
        playerName.setText(player.getCapNumber() + ". " + player.getName());


        Button epShotAttemptsP = (Button) findViewById(R.id.epShotAttemptsP);
        final TextView epShotAttempts = (TextView) findViewById(R.id.epnumberShotAttempts);
        epShotAttempts.setText(player.getShotAttempts());
        updateTextView(epShotAttemptsP, epShotAttempts);
        Button epShotAttemptsM = (Button) findViewById(R.id.epShotAttemptsM);
        updateTextView(epShotAttemptsM, epShotAttempts);

        Button epGoalsP = (Button) findViewById(R.id.epGoalsP);
        final TextView epGoals = (TextView) findViewById(R.id.epnumberGoals);
        epGoals.setText(player.getGoals());
        updateGoalsSA(epGoalsP, epGoals, epShotAttempts);
        Button epGoalsM = (Button) findViewById(R.id.epGoalsM);
        updateGoalsSA(epGoalsM, epGoals, epShotAttempts);


        Button epAssistsP = (Button) findViewById(R.id.epAssistP);
        final TextView epAssists = (TextView) findViewById(R.id.epnumberAssists);
        epAssists.setText(player.getAssists());
        updateTextView(epAssistsP, epAssists);
        Button epAssistsM = (Button) findViewById(R.id.epAssistM);
        updateTextView(epAssistsM, epAssists);

        Button epDEXP = (Button) findViewById(R.id.epDrawnEjectP);
        final TextView epDEX = (TextView) findViewById(R.id.epnumberDrawnEj);
        epDEX.setText(player.getDrawnEject());
        updateTextView(epDEXP, epDEX);
        Button epDEXM = (Button) findViewById(R.id.epDrawnEjectM);
        updateTextView(epDEXM, epDEX);

        Button epEXP = (Button) findViewById(R.id.epEjectionsP);
        final TextView epEX = (TextView) findViewById(R.id.epnumberEjections);
        epEX.setText(player.getEject());
        updateTextView(epEXP, epEX);
        Button epEXM = (Button) findViewById(R.id.epEjectionsM);
        updateTextView(epEXM, epEX);

        Button epStealP = (Button) findViewById(R.id.epStealsP);
        final TextView epSteal = (TextView) findViewById(R.id.epnumberSteals);
        epSteal.setText(player.getSteals());
        updateTextView(epStealP, epSteal);
        Button epStealM = (Button) findViewById(R.id.epStealsM);
        updateTextView(epStealM, epSteal);

        Button epBlockP = (Button) findViewById(R.id.epBlocksP);
        final TextView epBlock = (TextView) findViewById(R.id.epnumberBlocks);
        epBlock.setText(player.getBlocks());
        updateTextView(epBlockP, epBlock);
        Button epBlockM = (Button) findViewById(R.id.epBLocksM);
        updateTextView(epBlockM, epBlock);

        final String capNumber = player.getCapNumber();
        saveChanges = (Button) findViewById(R.id.epSaveChanges);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String percent = Double.toString(Double.parseDouble(epGoals.getText().toString())/Double.parseDouble(epShotAttempts.getText().toString())) + "%";
                db.updateData(tableName, capNumber, epGoals.getText().toString(), epShotAttempts.getText().toString(), percent,  epAssists.getText().toString()
                , epDEX.getText().toString(), epEX.getText().toString(), epSteal.getText().toString(), epBlock.getText().toString());
                openGameActivity(tableName);
            }
        });
    }

    public void updateTextView(final Button button, final TextView textView){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(textView.getText().toString());
                if(button.getText().toString().equals("+")){
                    number++;
                } else {
                    if(number == 0){
                        Toast.makeText(getApplicationContext(), "Number cannot go below 0", Toast.LENGTH_SHORT).show();
                    } else {
                        number--;
                    }
                }
                textView.setText(Integer.toString(number));
            }
        });
    }

    public void updateGoalsSA(final Button button, final TextView goals, final TextView shotAttempts){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(goals.getText().toString());
                int shotAttempt = Integer.parseInt(shotAttempts.getText().toString());
                if(button.getText().toString().equals("+")){
                    number++;
                    shotAttempt++;
                } else {
                    if(number == 0){
                        Toast.makeText(getApplicationContext(), "Number cannot go below 0", Toast.LENGTH_SHORT).show();
                    } else {
                        number--;
                    }
                }
                goals.setText(Integer.toString(number));
                shotAttempts.setText(Integer.toString(shotAttempt));
            }
        });
    }

    public void openGameActivity(String tableName) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("TableName", tableName);
        startActivity(intent);
    }

}
