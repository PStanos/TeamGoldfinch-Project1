package edu.msu.stanospa.teamgoldfinch_project1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game(this);
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

    public void onStartGame(View view) {
        String name1 = ((EditText)findViewById(R.id.player1Name)).getText().toString();
        String name2 = ((EditText)findViewById(R.id.player2Name)).getText().toString();

        game.setPlayerNames(name1, name2);

        Bundle bundle = new Bundle();
        game.saveInstanceState(bundle, this);

        Intent intent = new Intent(this, SelectionActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
