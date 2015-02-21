package edu.msu.stanospa.teamgoldfinch_project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Ethan on 2/12/15.
 */
public class Selection {
    /**
     * Percentage of the view width/height that is occupied by the game
     */
    private final static float SCALE_IN_VIEW = 0.9f;

    /**
     * The size of the game field
     */
    private int gameSize;


    /**
     * The width screen margin for the game
     */
    private int marginX;

    /**
     * The height screen buffer for the game
     */
    private int marginY;

    /**
     * The 1:1 scaling width of the game
     */
    private float scalingWidth;

    /**
     * the scaling factor for drawing birds
     */
    private float scaleFactor;

    /**
     * Paint for outlining the area the puzzle is in
     */
    private Paint outlinePaint;

    public Bird getTouchedBird() {
        return touchedBird;
    }

    /**
     * Currently touched bird
     */
    private Bird touchedBird = null;

    /**
     * Collection of the birds that have been placed
     */
    private ArrayList<Bird> birds = new ArrayList<>();


    /**
     * @param context the current context
     */
    public Selection(Context context) {

        // Create the paint for outlining the play area
        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(3.0f);
        outlinePaint.setColor(Color.RED);

        // Birds will be scaled so that the game is "1.5 ostriches" wide
        Bitmap scaleBird = BitmapFactory.decodeResource(context.getResources(), R.drawable.ostrich);
        scalingWidth = scaleBird.getWidth()*1.5f;


        // load the bird images
        birds.add(new Bird(context, R.drawable.ostrich));
        birds.get(0).setX(0.359f);
        birds.get(0).setY(0.480f);
        birds.add(new Bird(context, R.drawable.swallow));
        birds.get(1).setX(0.806f);
        birds.get(1).setY(0.158f);
        birds.add(new Bird(context, R.drawable.robin));
        birds.get(2).setX(0.841f);
        birds.get(2).setY(0.451f);
        birds.add(new Bird(context, R.drawable.hummingbird));
        birds.get(3).setX(0.158f);
        birds.get(3).setY(0.119f);
        birds.add(new Bird(context, R.drawable.seagull));
        birds.get(4).setX(0.710f);
        birds.get(4).setY(0.701f);

    }

    public void draw(Canvas canvas) {

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // The puzzle size is the view scale ratio of the minimum dimension, to make it square
        gameSize = (int)((width < height ? width : height) * SCALE_IN_VIEW);

        // Margins for centering the puzzle
        marginX = (width - gameSize) / 2;
        marginY = (height - gameSize) / 2;

        // Draw the outline of the gameplay area
        canvas.drawRect(marginX, marginY, marginX + gameSize, marginY + gameSize, outlinePaint);

        scaleFactor = (float)gameSize/scalingWidth;

        canvas.save();
        canvas.translate(marginX, marginY);
        canvas.scale(scaleFactor, scaleFactor);
        canvas.restore();

        for (Bird bird : birds) {
            bird.draw(canvas, marginX, marginY, gameSize, scaleFactor);
        }


    }

    /**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {
        float relX = (event.getX() - marginX) / gameSize;
        float relY = (event.getY() - marginY) / gameSize;

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:

                return onTouched(relX, relY);

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_MOVE:
                break;
        }

        return false;
    }

    private boolean onTouched(float x, float y) {
        Log.i("onTouched", "checking...");

        // Check each piece to see if it has been hit
        // We do this in reverse order so we find the pieces in front
        for(int b=birds.size()-1; b>=0;  b--) {
            if (birds.get(b).hit(x, y, gameSize, scaleFactor)) {
                // We hit a piece!
                Log.i("onTouched", "PIECE HIT!!" + birds.get(b));
                touchedBird = birds.get(b);
                return true;
            }
        }

        return false;
    }

    /**
     * save the selection in to a bundle
     * @param bundle the bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {
        if (touchedBird != null) {
            bundle.putInt("touchedBirdIndex", birds.indexOf(touchedBird));
        }
    }

    /**
     * Read the selection from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
        int touchedBirdIndex = bundle.getInt("touchedBirdIndex");
        touchedBird = birds.get(touchedBirdIndex);
    }
}
