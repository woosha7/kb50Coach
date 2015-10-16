package database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class Provider extends ContentProvider {

    static final String PROVIDER_NAME           = "coach";

    //SQL LITE GENERAL
    static final String SQL_TXT_NN              = " text not null";
    static final String SQL_INT_NN              = " integer not null";
    static final String NEXT                    = ",";
    static final String END                     = ");";
    static final String REF_INT                 = " INTEGER REFERENCES ";


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

    //TABLE VALUES
    //TEAM
    static final String TABLE_TEAM_ID           = "id";
    static final String TABLE_TEAM_CLUB         = "club";
    static final String TABLE_TEAM_NAME         = "name";
    static final String TABLE_TEAM_CITY         = "city";

    //PLAYER
    static final String TABLE_PLAYER_ID         = "id";
    static final String TABLE_PLAYER_NAME       = "name";
    static final String TABLE_PLAYER_EMAIL      = "email";
    static final String TABLE_PLAYER_PHONE      = "phone";

    //MATCH
    static final String TABLE_MATCH_ID          = "id";
    static final String TABLE_MATCH_OPPONTENT   = "opponent";
    static final String TABLE_MATCH_DATE        = "date";
    static final String TABLE_MATCH_LOCATION    = "location";

    //POSITION
    static final String TABLE_POSITION_ID           = "id";
    static final String TABLE_POSITION_NAME         = "name";
    static final String TABLE_POSITION_DESCRIPTION  = "description";

    //LINEUP
    static final String TABLE_LINEUP_ID             = "id";
    static final String TABLE_LINEUP_NAME           = "name";

    //TRAINING
    static final String TABLE_TRANING_ID            = "id";
    static final String TABLE_TRANING_DATE          = "date";

    //PLAYER TRAINING
    static final String TABLE_PLAYER_TRAINING_ID        = "id";
    static final String TABLE_PLAYER_TRAINING_PLAYER    = "playerid";
    static final String TABLE_PLAYER_TRAINING_TRAINING  = "trainingid";
    static final String TABLE_PLAYER_TRAINING_RATING    = "rating";

    //PLAYER POSITION
    static final String TABLE_PLAYER_POSITION_ID        = "id";
    static final String TABLE_PLAYER_POSITION_PLAYER    = "playerid";
    static final String TABLE_PLAYER_POSITION_POSITION  = "positionid";

    //PLAYER MATCH
    static final String TABLE_PLAYER_MATCH_ID           = "id";
    static final String TABLE_PLAYER_MATCH_PLAYER       = "playerid";
    static final String TABLE_PLAYER_MATCH_MATCH        = "matchid";


    static final String[] ALL_TABLES            = {
            TABLE_TEAM,TABLE_PLAYER,TABLE_MATCH,TABLE_POSITION,
            TABLE_LINEUP,TABLE_TRAINING,TABLE_PLAYER_TRAINING,
            TABLE_PLAYER_POSITION,TABLE_PLAYER_MATCH};

    //URI index voor alle items 'ALL_' en specifieke 'SINGLE_'
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
    static final int SINGLE_PLAYER_MATCH         = 95;

    /*
    URI index koppelen aan tabel namen. Hierdoor kan met de methode getSelector
    herhalingen in de code worden verkomen.
    */
    static final Selector[] tableMatchers        = {
            //ALL ITEMS
            new Selector(   ALL_TEAMS,              TABLE_TEAM),
            new Selector(   ALL_PLAYERS,            TABLE_PLAYER),
            new Selector(   ALL_MATCHES,            TABLE_MATCH),
            new Selector(   ALL_POSITIONS,          TABLE_POSITION),
            new Selector(   ALL_LINEUPS,            TABLE_LINEUP),
            new Selector(   ALL_TRAININGS,          TABLE_TRAINING),
            new Selector(   ALL_PLAYER_TRAININGS,   TABLE_PLAYER_TRAINING),
            new Selector(   ALL_PLAYER_POSITIONS,   TABLE_PLAYER_POSITION),
            new Selector(   ALL_PLAYER_MATCHES,     TABLE_PLAYER_MATCH),

            //SINGLE ITEMS
            new Selector(   SINGLE_TEAM,            TABLE_TEAM),
            new Selector(   SINGLE_PLAYER,          TABLE_PLAYER),
            new Selector(   SINGLE_MATCH,           TABLE_MATCH),
            new Selector(   SINGLE_POSITION,        TABLE_POSITION),
            new Selector(   SINGLE_LINEUP,          TABLE_LINEUP),
            new Selector(   SINGLE_TRAINING,        TABLE_TRAINING),
            new Selector(   SINGLE_PLAYER_TRAINING, TABLE_PLAYER_TRAINING),
            new Selector(   SINGLE_PLAYER_POSITION, TABLE_PLAYER_POSITION),
            new Selector(   SINGLE_PLAYER_MATCH,    TABLE_PLAYER_MATCH),
    };

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
    static final String SQL_CREATE_TABLE_TEAM               = "create table " + TABLE_TEAM +
            "( id integer primary key autoincrement," +
            TABLE_TEAM_CLUB + SQL_TXT_NN + NEXT +
            TABLE_TEAM_NAME + SQL_TXT_NN + NEXT +
            TABLE_TEAM_CITY + SQL_TXT_NN + END;

    static final String SQL_CREATE_TABLE_PLAYER             = "create table " + TABLE_PLAYER +
            "( id integer primary key autoincrement," +
            TABLE_PLAYER_NAME   + SQL_TXT_NN + NEXT +
            TABLE_PLAYER_EMAIL  + SQL_TXT_NN + NEXT +
            TABLE_PLAYER_PHONE  + SQL_INT_NN + END;

    static final String SQL_CREATE_TABLE_MATCH              = "create table " + TABLE_MATCH +
            "( id integer primary key autoincrement," +
            TABLE_MATCH_OPPONTENT   + SQL_TXT_NN + NEXT +
            TABLE_MATCH_DATE        + SQL_TXT_NN + NEXT +
            TABLE_MATCH_LOCATION    + SQL_TXT_NN + END;

    static final String SQL_CREATE_TABLE_POSITION           = "create table " + TABLE_POSITION +
            "( id integer primary key autoincrement," +
            TABLE_POSITION_NAME + SQL_TXT_NN + NEXT +
            TABLE_POSITION_DESCRIPTION + SQL_TXT_NN + END;

    static final String SQL_CREATE_TABLE_LINEUP             = "create table " + TABLE_LINEUP +
            "( id integer primary key autoincrement," +
            TABLE_LINEUP_NAME + SQL_TXT_NN + END;

    static final String SQL_CREATE_TABLE_TRAINING           = "create table " + TABLE_TRAINING +
            "( id integer primary key autoincrement," +
            TABLE_TRANING_DATE + SQL_TXT_NN + END;

    static final String SQL_CREATE_TABLE_PLAYER_TRAINING    = "create table " + TABLE_PLAYER_TRAINING +
            "( id integer primary key autoincrement," +
            TABLE_PLAYER_TRAINING_PLAYER    + REF_INT + TABLE_PLAYER    +"(id)"+ NEXT +
            TABLE_PLAYER_TRAINING_TRAINING  + REF_INT + TABLE_TRAINING  +"(id)"+ NEXT +
            TABLE_PLAYER_TRAINING_RATING + SQL_TXT_NN + END;

    static final String SQL_CREATE_TABLE_PLAYER_POSITION    = "create table " + TABLE_PLAYER_POSITION +
            "( id integer primary key autoincrement," +
            TABLE_PLAYER_POSITION_PLAYER    + REF_INT + TABLE_PLAYER    +"(id)"+ NEXT +
            TABLE_PLAYER_POSITION_POSITION  + REF_INT + TABLE_POSITION    +"(id)"+ END;

    static final String SQL_CREATE_TABLE_PLAYER_MATCH       = "create table " + TABLE_PLAYER_MATCH +
            "( id integer primary key autoincrement," +
            TABLE_PLAYER_MATCH_PLAYER       + REF_INT + TABLE_PLAYER    +"(id)"+ NEXT +
            TABLE_PLAYER_MATCH_MATCH        + REF_INT + TABLE_MATCH +"(id)"+ END;

    static final String[] SQL_CREATE_ALL_TABLES = {
            SQL_CREATE_TABLE_TEAM,SQL_CREATE_TABLE_PLAYER,
            SQL_CREATE_TABLE_MATCH,SQL_CREATE_TABLE_POSITION,SQL_CREATE_TABLE_LINEUP,
            SQL_CREATE_TABLE_TRAINING,SQL_CREATE_TABLE_PLAYER_TRAINING,
            SQL_CREATE_TABLE_PLAYER_POSITION,SQL_CREATE_TABLE_PLAYER_MATCH};

    //DATABASE VARS
    SQLiteDatabase database;
    static final String DATABASE_NAME           = "coachDB";
    static final int DATABASE_VERSION           = 1;

    public static class Selector {
        public int      index;
        public String   table;
        public Uri      uri;

        Selector(int i, String t){
            index   = i;
            table   = t;
            uri     = Uri.parse("content://"+ PROVIDER_NAME +"/"+table);
        }
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context, DATABASE_NAME,null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            for (String SQL_CREATE_SPECIFIC_TABLE : SQL_CREATE_ALL_TABLES) {
                db.execSQL(SQL_CREATE_SPECIFIC_TABLE);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            for (String TABLE : ALL_TABLES) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE);
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

    Selector getSelector(Integer selector){
        Selector result = null;

        for(Selector s : tableMatchers) {
            if(s.index == selector){
               result =  s;
                }

        }
        return result;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int match = uriMatcher.match(uri);
        switch(match){

            default:
                throw new IllegalArgumentException("Uknown URI:" + uri);

            //ALL ITEMS
            case ALL_TEAMS:
            case ALL_PLAYERS:
            case ALL_MATCHES:
            case ALL_POSITIONS:
            case ALL_LINEUPS:
            case ALL_TRAININGS:
            case ALL_PLAYER_TRAININGS:
            case ALL_PLAYER_POSITIONS:
            case ALL_PLAYER_MATCHES:
                queryBuilder.setTables(getSelector(match).table);
                break;

            //SPECIFIC ITEM
            case SINGLE_TEAM:
            case SINGLE_PLAYER:
            case SINGLE_MATCH:
            case SINGLE_POSITION:
            case SINGLE_LINEUP:
            case SINGLE_TRAINING:
            case SINGLE_PLAYER_TRAINING:
            case SINGLE_PLAYER_POSITION:
            case SINGLE_PLAYER_MATCH:
                queryBuilder.setTables(getSelector(match).table);
                queryBuilder.appendWhere("id=" + uri.getLastPathSegment());
                break;
        }

        Cursor c = queryBuilder.query(
                database,projection,selection,selectionArgs,null,null,sortOrder);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri){
        String type;
        int match = uriMatcher.match(uri);
        switch(match){

            default:
                throw new IllegalArgumentException("Uknown URI:" + uri);

            //ALL ITEMS
            case ALL_TEAMS:
            case ALL_PLAYERS:
            case ALL_MATCHES:
            case ALL_POSITIONS:
            case ALL_LINEUPS:
            case ALL_TRAININGS:
            case ALL_PLAYER_TRAININGS:
            case ALL_PLAYER_POSITIONS:
            case ALL_PLAYER_MATCHES:
                type = "vnd.adroid.cursor.dir/vnd." + getSelector(match).table;
                break;

            //SPECIFIC ITEM
            case SINGLE_TEAM:
            case SINGLE_PLAYER:
            case SINGLE_MATCH:
            case SINGLE_POSITION:
            case SINGLE_LINEUP:
            case SINGLE_TRAINING:
            case SINGLE_PLAYER_TRAINING:
            case SINGLE_PLAYER_POSITION:
            case SINGLE_PLAYER_MATCH:
                type = "vnd.adroid.cursor.dir/item." + getSelector(match).table;
                break;
        }
        return type;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match   = uriMatcher.match(uri);
        Uri _uri    = null;
        switch(uriMatcher.match(uri)) {
            default:
                throw new IllegalArgumentException("Uknown URI:" + uri);

            case ALL_TEAMS:
            case ALL_PLAYERS:
            case ALL_MATCHES:
            case ALL_POSITIONS:
            case ALL_LINEUPS:
            case ALL_TRAININGS:
            case ALL_PLAYER_TRAININGS:
            case ALL_PLAYER_POSITIONS:
            case ALL_PLAYER_MATCHES:
                Selector temp = getSelector(match);
                Long _CP = database.insert(temp.table, "" , values);
                if(_CP > 0 ){
                    _uri = ContentUris.withAppendedId(temp.uri, _CP);
                    getContext().getContentResolver().notifyChange(_uri,null);
                }
                break;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match   = uriMatcher.match(uri);
        int count   = 0 ;
        String id   = null;
        switch(uriMatcher.match(uri)) {

            default:
                throw new IllegalArgumentException("Uknown URI:" + uri);

            //ALL ITEMS
            case ALL_TEAMS:
            case ALL_PLAYERS:
            case ALL_MATCHES:
            case ALL_POSITIONS:
            case ALL_LINEUPS:
            case ALL_TRAININGS:
            case ALL_PLAYER_TRAININGS:
            case ALL_PLAYER_POSITIONS:
            case ALL_PLAYER_MATCHES:
                count = database.delete(getSelector(match).table,selection,selectionArgs);
                break;

            //SPECIFIC ITEM
            case SINGLE_TEAM:
            case SINGLE_PLAYER:
            case SINGLE_MATCH:
            case SINGLE_POSITION:
            case SINGLE_LINEUP:
            case SINGLE_TRAINING:
            case SINGLE_PLAYER_TRAINING:
            case SINGLE_PLAYER_POSITION:
            case SINGLE_PLAYER_MATCH:
                id      = uri.getPathSegments().get(1);
                count   = database.delete(getSelector(match).table, "id=" + id + (!TextUtils.isEmpty(selection) ? " AND ("+ selection + ')' : ""),selectionArgs);
                break;
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match   = uriMatcher.match(uri);
        int count   = 0 ;
        String id   = null;
        switch(uriMatcher.match(uri)) {

            default:
                throw new IllegalArgumentException("Uknown URI:" + uri);

            //ALL ITEMS
            case ALL_TEAMS:
            case ALL_PLAYERS:
            case ALL_MATCHES:
            case ALL_POSITIONS:
            case ALL_LINEUPS:
            case ALL_TRAININGS:
            case ALL_PLAYER_TRAININGS:
            case ALL_PLAYER_POSITIONS:
            case ALL_PLAYER_MATCHES:
                count = database.update(getSelector(match).table, values, selection, selectionArgs);
                break;

            //SPECIFIC ITEM
            case SINGLE_TEAM:
            case SINGLE_PLAYER:
            case SINGLE_MATCH:
            case SINGLE_POSITION:
            case SINGLE_LINEUP:
            case SINGLE_TRAINING:
            case SINGLE_PLAYER_TRAINING:
            case SINGLE_PLAYER_POSITION:
            case SINGLE_PLAYER_MATCH:
                id      = uri.getPathSegments().get(1);
                count   = database.update(getSelector(match).table, values, "id=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
        }
        return count;
    }
}
