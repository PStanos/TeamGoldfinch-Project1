package edu.msu.stanospa.teamgoldfinch_project1;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Custom view class for our Game
 */
public class GameView extends View {

    /**
     * The actual game
     */
    private Game game;

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean inSelectionState() {
        return game.inSelectionState();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return game.onTouchEvent(this, event);
    }

    public void onPlaceBird() {
        game.confirmBirdPlacement();
    }

    public void reloadBirds() {
        game.reloadBirds(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        game.draw(canvas);
    }

    public void saveInstanceState(Bundle bundle) { game.saveInstanceState(bundle); }

    public void loadInstanceState(Bundle bundle) { game.loadInstanceState(bundle); }

}
