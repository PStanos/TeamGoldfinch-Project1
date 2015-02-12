package edu.msu.stanospa.teamgoldfinch_project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ethan on 2/12/15.
 */
public class Selection {
    /**
     * Percentage of the view width/height that is occupied by the game
     */
    private final static float SCALE_IN_VIEW = 0.9f;

    /**
     * Random number generator
     */
    private static Random random = new Random();

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
    public Selection(Context context) {

        // Create the paint for outlining the play area
        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(3.0f);
        outlinePaint.setColor(Color.RED);

        // Birds will be scaled so that the game is "1.5 ostriches" wide
        Bitmap scaleBird = BitmapFactory.decodeResource(context.getResources(), R.drawable.ostrich);
        scalingWidth = scaleBird.getWidth()*1.5f;


        // load the temp bird image
        birds.add(new Bird(context, R.drawable.ostrich, 0.359f, 0.238f));
        birds.add(new Bird(context, R.drawable.swallow, 0.766f, 0.158f));
        birds.add(new Bird(context, R.drawable.robin, 0.841f, 0.501f));
        birds.add(new Bird(context, R.drawable.hummingbird, 0.541f, 0.519f));
        birds.add(new Bird(context, R.drawable.seagull, 0.610f, 0.761f));

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
        canvas.drawText("Select a bird", 0.1f, 0.1f, outlinePaint);

        scaleFactor = gameSize/scalingWidth;

        for (Bird bird : birds) {
            bird.place(canvas, marginX, marginY, gameSize, scaleFactor);
        }

    }

}
