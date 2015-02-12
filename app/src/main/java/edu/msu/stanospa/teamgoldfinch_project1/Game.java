package edu.msu.stanospa.teamgoldfinch_project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class Game {

    private Player player1;
    private Player player2;

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

    /**
     * Collection of the birds that have been placed
     */
    private ArrayList<Bird> birds = new ArrayList<>();

    /**
     * @param context the current context
     */
    public Game(Context context) {

        // Create the paint for outlining the play area
        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(3.0f);
        outlinePaint.setColor(Color.RED);

        // Birds will be scaled so that the game is "2.5 ostriches" wide
        Bitmap scaleBird = BitmapFactory.decodeResource(context.getResources(), R.drawable.ostrich);
        scalingWidth = scaleBird.getWidth()*2.5f;

        // load the temp bird image
        birds.add(new Bird(context, R.drawable.ostrich));
    }

    public void setPlayerSelection(Bird selection) {
        // TODO: fill this in
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

        scaleFactor = gameSize/scalingWidth;

        for (Bird bird : birds) {
            bird.draw(canvas, marginX, marginY, gameSize, scaleFactor);
        }

    }
}
