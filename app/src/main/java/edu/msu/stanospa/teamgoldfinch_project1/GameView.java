package edu.msu.stanospa.teamgoldfinch_project1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


/**
 * Custom view class for our Game
 */
public class GameView extends View {

    //private Game game;

    public GameView(Context context) {
        super(context);
        init(null, 0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
    }

}
