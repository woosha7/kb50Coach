package test;

import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;
import android.util.Log;

import database.Provider;
import groep3.coach.GeneralFunctions;

/**
 * Created by virtue on 16/10/15.
 */
public class TestContentProviderCoach extends ProviderTestCase2<Provider> {

    private static MockContentResolver resolve;

    public TestContentProviderCoach(){
        super(Provider.class, "coach");
    }

    public void setUp() {
        try {
            super.setUp();
            resolve = this.getMockContentResolver();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("START:", "....");

    }



    public void testCrudTeam(){
        Uri uri  = Uri.parse("content://coach/team");
        ContentValues team = new ContentValues();

        String expextedCity = "MUNICH";
        String expextedName = "TEAM 1";
        String expextedClub = "BAYERN MUNICH";
        String changedClub  = "BAYERN MUNICH AD";

        String retrievedCity = "";
        String retrievedName = "";
        String retrievedClub = "";

        team.put("club",expextedClub);
        team.put("city",expextedCity);
        team.put("name" ,expextedName);

        //create
        resolve.insert(uri, team);

        //Now check if items exists
        Cursor c = resolve.query(Uri.parse("content://coach/team/1"), null, null, null, null);
        String id;

        if (c!= null) {
            if (c.moveToFirst()) {
                do {
                    id                  = c.getString(c.getColumnIndex("id"));
                    retrievedCity       = c.getString(c.getColumnIndex("city"));
                    retrievedName       = c.getString(c.getColumnIndex("name"));
                    retrievedClub       = c.getString(c.getColumnIndex("club"));

                } while (c.moveToNext());
            }
        }

        //test insert
        assertEquals(expextedCity, retrievedCity);
        assertEquals(expextedName, retrievedName);
        assertEquals(expextedClub,retrievedClub);


        team.put("club", changedClub);
        resolve.update(Uri.parse("content://coach/team/1"), team, null, null);

        //Now check if items exists
        Cursor cd = resolve.query(Uri.parse("content://coach/team/1"), null, null, null, null);
        String idd;

        if (cd!= null) {
            if (cd.moveToFirst()) {
                do {
                    retrievedClub       = cd.getString(c.getColumnIndex("club"));

                } while (cd.moveToNext());
            }
        }

        //test update
        assertEquals(changedClub,retrievedClub);

        //delete
        resolve.delete(Uri.parse("content://coach/team/1"), null, null);

        //Now check if items exists
        Cursor de = resolve.query(Uri.parse("content://coach/team/1"), null, null, null, null);
        String idde;

        retrievedClub = "";
        if (de!= null) {
            if (de.moveToFirst()) {
                do {
                    retrievedClub           = de.getString(c.getColumnIndex("club"));

                } while (cd.moveToNext());
            }
        }

        //test delete
        assertEquals("",retrievedClub);
    }

    @Override
    public void tearDown() {
        try{
            super.tearDown();

        }
        catch(Exception e) {


        }
    }

}
