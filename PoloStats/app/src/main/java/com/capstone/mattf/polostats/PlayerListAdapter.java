package com.capstone.mattf.polostats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PlayerListAdapter extends ArrayAdapter<Player> {
    private LayoutInflater mInflater;
    private ArrayList<Player> players;
    private int mViewResourceId;

    public PlayerListAdapter(Context context, int textViewResourceId, ArrayList<Player> players) {
        super(context, textViewResourceId, players);
        this.players = players;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents) {
        convertView = mInflater.inflate(mViewResourceId, null);
        Player player = players.get(position);

        if (player != null) {
            TextView number = (TextView) convertView.findViewById(R.id.gcapNumber);
            TextView name = (TextView) convertView.findViewById(R.id.gplayerName);
            TextView goals = (TextView) convertView.findViewById(R.id.ggoals);
            TextView attempts = (TextView) convertView.findViewById(R.id.gattempts);
            TextView shotPercent = (TextView) convertView.findViewById(R.id.gshotPercent);
            TextView assists = (TextView) convertView.findViewById(R.id.gassists);
            TextView drawnEject = (TextView) convertView.findViewById(R.id.gdrawnEjections);
            TextView eject = (TextView) convertView.findViewById(R.id.gejections);
            TextView steals = (TextView) convertView.findViewById(R.id.gsteals);
            TextView fblocks = (TextView) convertView.findViewById(R.id.gfBlocks);

            if (number != null) {
                number.setText(player.getCapNumber());
            }
            if (name != null) {
                name.setText(player.getName());
            }
            if (goals != null) {
                goals.setText(player.getGoals());
            }
            if(attempts != null){
                attempts.setText(player.getShotAttempts());
            }
            if (shotPercent != null) {
                shotPercent.setText(player.getShotPercent());
            }
            if (assists != null) {
                assists.setText(player.getAssists());
            }
            if (drawnEject != null) {
                drawnEject.setText(player.getDrawnEject());
            }
            if (eject != null) {
                eject.setText(player.getEject());
            }
            if (steals != null) {
                steals.setText(player.getSteals());
            }
            if (fblocks != null) {
                fblocks.setText(player.getBlocks());
            }
        }
        return convertView;
    }
}
