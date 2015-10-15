package groep3.coach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void playersClicked(View b) {
        Intent intent = new Intent(MainActivity.this, PlayersActivity.class);
        startActivity(intent);
    }
    public void draftClicked(View b) {
        Intent intent = new Intent(MainActivity.this, DraftActivity.class);
        startActivity(intent);
    }
    public void administrationClicked(View b) {
        Intent intent = new Intent(MainActivity.this, AdministrationActivity.class);
        startActivity(intent);
    }
    public void matchClicked(View b) {
        Intent intent = new Intent(MainActivity.this, MatchActivity.class);
        startActivity(intent);
    }
    public void whistletoolClicked(View b) {
        Intent intent = new Intent(MainActivity.this, WhistletoolActivity.class);
        startActivity(intent);
    }
    public void newteamClicked(View b) {
        Intent intent = new Intent(MainActivity.this, NewteamActivity.class);
        startActivity(intent);
    }
}
