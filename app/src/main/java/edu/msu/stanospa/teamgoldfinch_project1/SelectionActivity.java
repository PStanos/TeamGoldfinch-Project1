package edu.msu.stanospa.teamgoldfinch_project1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class SelectionActivity extends ActionBarActivity {

    private Game game;

    private SelectionView selectionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        game = (Game)getIntent().getExtras().getSerializable(getString(R.string.game_state));
        selectionView = (SelectionView)findViewById(R.id.selectionView);

        //selectionView.setGame(game);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selection, menu);
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

    public void onConfirmSelection(View view) {
        Bundle bundle = new Bundle();
        game.saveInstanceState(bundle, this);

        if (selectionView.isSelected()) {
            selectionView.setPlayerSelection(game);

            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Log.i("onConfirmSelection", "bird not selected");
        }
    }

}
