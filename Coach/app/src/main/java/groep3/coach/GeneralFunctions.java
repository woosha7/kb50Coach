package groep3.coach;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

/**
 * Algemene functies die gebruikt kunnen worden in verschillende onderdelen
 */
public class GeneralFunctions extends AppCompatActivity {

    void launchActivity(Intent a){
        startActivity(a);
    }

    public Cursor cpRetrieveItem(String link, int i) {
        Uri item = Uri.parse(link + i);
        return cpRetrieveItems(item,null,null);
    }

    public Cursor cpRetrieveItems(Uri u, String where, String order) {
        Uri items = u;
        Cursor c;
        if (android.os.Build.VERSION.SDK_INT < 11) {
            return c = managedQuery(items, null, null, null, null);
        } else {
            CursorLoader cursorLoader = new CursorLoader(this, items, null, where, null, order);
            return c = cursorLoader.loadInBackground();
        }
    }
}
