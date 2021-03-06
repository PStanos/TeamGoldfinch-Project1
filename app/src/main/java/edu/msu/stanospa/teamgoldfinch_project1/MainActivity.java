package edu.msu.stanospa.teamgoldfinch_project1;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

    public void onViewInstructions(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        builder.setTitle(R.string.instructions_title);
        builder.setMessage(R.string.instructions_text);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
