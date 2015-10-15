package database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

public class Provider extends ContentProvider {

    static final String PROVIDER_NAME           = "coach";

    //TABLES
    static final String TABLE_TEAM              = "team";
    static final String TABLE_PLAYER            = "player";
    static final String TABLE_MATCH             = "match";
    static final String TABLE_POSITION          = "position";
    static final String TABLE_LINEUP            = "lineup";
    static final String TABLE_TRAINING          = "training";
    static final String TABLE_PLAYER_TRAINING   = "player_training";
    static final String TABLE_PLAYER_POSITION   = "player_position";
    static final String TABLE_PLAYER_MATCH      = "player_match";

    static final String[] ALL_TABLES            = {
            TABLE_TEAM,TABLE_PLAYER,TABLE_MATCH,TABLE_POSITION,
            TABLE_LINEUP,TABLE_TRAINING,TABLE_PLAYER_TRAINING,
            TABLE_PLAYER_POSITION,TABLE_PLAYER_MATCH};

    //URI matcher
    static final int ALL_TEAMS                   = 10;
    static final int SINGLE_TEAM                 = 15;
    static final int ALL_PLAYERS                 = 20;
    static final int SINGLE_PLAYER               = 25;
    static final int ALL_MATCHES                 = 30;
    static final int SINGLE_MATCH                = 35;
    static final int ALL_POSITIONS               = 40;
    static final int SINGLE_POSITION             = 45;
    static final int ALL_LINEUPS                 = 50;
    static final int SINGLE_LINEUP               = 55;
    static final int ALL_TRAININGS               = 60;
    static final int SINGLE_TRAINING             = 65;
    static final int ALL_PLAYER_TRAININGS        = 70;
    static final int SINGLE_PLAYER_TRAINING      = 75;
    static final int ALL_PLAYER_POSITIONS        = 80;
    static final int SINGLE_PLAYER_POSITION      = 85;
    static final int ALL_PLAYER_MATCHES          = 90;
    static final int SINGLE_PLAYER_MATCH         = 55;

    private static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_TEAM, ALL_TEAMS);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_TEAM + "/#",SINGLE_TEAM);

        uriMatcher.addURI(PROVIDER_NAME, TABLE_PLAYER, ALL_PLAYERS);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_PLAYER + "/#",SINGLE_PLAYER);

        uriMatcher.addURI(PROVIDER_NAME, TABLE_MATCH, ALL_MATCHES);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_PLAYER + "/#",SINGLE_MATCH);

        uriMatcher.addURI(PROVIDER_NAME, TABLE_POSITION, ALL_POSITIONS);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_POSITION + "/#",SINGLE_POSITION);

        uriMatcher.addURI(PROVIDER_NAME, TABLE_LINEUP, ALL_LINEUPS);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_POSITION + "/#",SINGLE_LINEUP);

        uriMatcher.addURI(PROVIDER_NAME, TABLE_TRAINING, ALL_TRAININGS);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_TRAINING + "/#",SINGLE_TRAINING);

        uriMatcher.addURI(PROVIDER_NAME, TABLE_PLAYER_TRAINING, ALL_PLAYER_TRAININGS);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_PLAYER_TRAINING + "/#",SINGLE_PLAYER_TRAINING);

        uriMatcher.addURI(PROVIDER_NAME, TABLE_PLAYER_POSITION, ALL_PLAYER_POSITIONS);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_PLAYER_POSITION + "/#",SINGLE_PLAYER_POSITION);

        uriMatcher.addURI(PROVIDER_NAME, TABLE_PLAYER_MATCH, ALL_PLAYER_MATCHES);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_PLAYER_MATCH + "/#",SINGLE_PLAYER_MATCH);
    }

    //SQL CREATE TABLES

    static final String[] SQL_CREATE_ALL_TABLES = {null,null};

    //DATABASE VARS
    SQLiteDatabase database;
    static final String DATABASE_NAME           = "coach";
    static final int DATABASE_VERSION           = 1;


    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context, DATABASE_NAME,null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            for (String SQL_CREATE_ALL_TABLE : SQL_CREATE_ALL_TABLES) {
                db.execSQL(SQL_CREATE_ALL_TABLE);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            for (String ALL_TABLE : ALL_TABLES) {
                db.execSQL("DROP TABLE IF EXISTS " + ALL_TABLE);
            }
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return (database == null)? false:true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri){
        return "";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
