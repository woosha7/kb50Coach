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

        Log.d("START:","....");

    }




    public void testLoadItems(){

        String id,name,city,club;

        ContentValues cv = new ContentValues();

        cv.put("club","FC AGGAfff");
        cv.put("city","DEN HAAGfff");
        cv.put("name" ,"E2s");

        resolve.insert(Uri.parse("content://coach/team"),cv);

        ContentValues cv1 = new ContentValues();
        cv1.put("club","xxx" );
        cv1.put("city","xxx" );
        cv1.put("name" ,"xxx");

        resolve.insert(Uri.parse("content://coach/team"),cv1);

        Cursor c = resolve.query(Uri.parse("content://coach/team"), null, null, null, null);

        Log.d("STEP2","CHECK LIST");
        if (c!= null) {
            if (c.moveToFirst()) {
                do {
                    id      = c.getString(c.getColumnIndex("id"));
                    city    = c.getString(c.getColumnIndex("city"));

                    Log.d("LIST:",":::::" + id + ":" + city);
                } while (c.moveToNext());
            }
            else{
                Log.d("LIST:","EMPTY");
            }
        }
        else{
            Log.d("LIST:","EMPTY");
        }
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
