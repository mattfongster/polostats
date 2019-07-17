package com.capstone.mattf.polostats;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "Score_Sheets.db";
    public static final String tableName = "Score_Sheet";
    public static final String capNumber = "Number";
    public static final String athleteName = "Name";
    public static final String goals = "Goals";
    public static final String shotAttempts = "Shot_Attempts";
    public static final String shotPercent = "Percent";
    public static final String assists = "Assists";
    public static final String drawnEject = "Drawn_Exclusions";
    public static final String eject = "Exclusions";
    public static final String steal = "Steals";
    public static final String fBlock = "Field_Blocks";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    public void deleteRoster() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table roster");
    }

    public void createRoster() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("create table " + "Roster" + "(" + capNumber + " integer primary key autoincrement, "
                    + athleteName + " text)");
        } catch (SQLException e) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("drop table roster");
            db.execSQL("create table " + "Roster" + "(" + capNumber + " integer primary key autoincrement, "
                    + athleteName + " text)");
        }

    }

    public void dropTable(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table " + table);
    }

    public String createScoreSheet(String opponentName) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Date currentTime = Calendar.getInstance().getTime();
            String s = currentTime.toString();
            String[] t = s.split(" ");
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < t.length; i++) {
                if (i == 0 || i == 1 || i == 2) {
                    builder.append(t[i] + "_");
                } else if (i == 5) {
                    builder.append(t[i]);
                }
            }
            String date = builder.toString();
            StringBuilder builder1 = new StringBuilder();
            builder1.append(opponentName + "_" + date);
            String table = builder1.toString();
            dropTable(table);
            db.execSQL("create table " + table + "(" + capNumber + " integer primary key, " +
                    athleteName + " text default '0', " + goals + " text default '0', " + shotAttempts + " text default '0', " + shotPercent + " text default '0%', " +
                    assists + " text default '0', " + drawnEject + " text default '0', " + eject + " text default '0', " +
                    steal + " text default '0', " + fBlock + " text default '0')");
            return table;
        } catch (SQLException e) {
            SQLiteDatabase db = this.getWritableDatabase();
            Date currentTime = Calendar.getInstance().getTime();
            String s = currentTime.toString();
            String[] t = s.split(" ");
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < t.length; i++) {
                if (i == 0 || i == 1 || i == 2) {
                    builder.append(t[i] + "_");
                } else if (i == 5) {
                    builder.append(t[i]);
                }
            }
            String date = builder.toString();
            StringBuilder builder1 = new StringBuilder();
            builder1.append(opponentName + "_" + date);
            String table = builder1.toString();
            db.execSQL("create table " + table + "(" + capNumber + " integer primary key, " +
                    athleteName + " text default '0', " + goals + " text default '0', " + shotAttempts + " text default '0', " + shotPercent + " text default '0%', " +
                    assists + " text default '0', " + drawnEject + " text default '0', " + eject + " text default '0', " +
                    steal + " text default '0', " + fBlock + " text default '0')");
            return table;
        }
    }

    public void updateData(String table, String capNumbers, String goal, String attempts, String percent, String assist, String drawnEjects, String ejects, String steals, String blocks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(capNumber, capNumbers);
        contentValues.put(goals, goal);
        contentValues.put(shotAttempts, attempts);
        contentValues.put(shotPercent, percent);
        contentValues.put(assists, assist);
        contentValues.put(drawnEject, drawnEjects);
        contentValues.put(eject, ejects);
        contentValues.put(steal, steals);
        contentValues.put(fBlock, blocks);
        db.update(table, contentValues, "number = ?", new String[]{capNumbers});
    }

    public void insertRoster(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(athleteName, name);
        db.insert("Roster", null, contentValues);
    }

    public Cursor grabTableNames() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT name FROM sqlite_master WHERE type='table'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void insertRosterSCS(String tableName, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(athleteName, name);
        db.insert(tableName, null, contentValues);
    }

    public Cursor viewData(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + table;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + tableName);
        onCreate(db);
    }
}
