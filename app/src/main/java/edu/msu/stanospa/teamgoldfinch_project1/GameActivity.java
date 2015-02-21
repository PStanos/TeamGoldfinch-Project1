package edu.msu.stanospa.teamgoldfinch_project1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class GameActivity extends ActionBarActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_game);
        Game game = (Game)getIntent().getExtras().getSerializable(getString(R.string.game_state));
        gameView = (GameView)findViewById(R.id.gameView);
        gameView.setGame(game);

        gameView.reloadBirds();

        if (bundle != null) {
            gameView.loadInstanceState(bundle);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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
    public void onPlaceBird(View view) {
        GameView gView = (GameView) findViewById(R.id.gameView);
        gView.onPlaceBird();

        if (gameView.inSelectionState()) {
            Intent intent = new Intent(this, FinalScoreActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        gameView.saveInstanceState(bundle);
    }
}
