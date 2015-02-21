package edu.msu.stanospa.teamgoldfinch_project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String LOCATIONS = "Game.locations";

    /**
     * Used to track what state the game is currently in
     */
    private enum GameState {
        nameEntry,
        birdSelection,
        birdPlacement
    }

    /**
     * Percentage of the view width/height that is occupied by the game
     */
    private final static float SCALE_IN_VIEW = 0.9f;

    /**
     * Width of the border around the game
     */
    private final static float BORDER_WIDTH = 3;

    /**
     * Paint for outlining the area the game is in
     */
    private static Paint outlinePaint;

    /**
     * The size of the game field
     */
    private transient int gameSize;


    /**
     * The width screen margin for the game
     */
    private transient int marginX;

    /**
     * The height screen buffer for the game
     */
    private transient int marginY;

    /**
     * The 1:1 scaling width of the game
     */
    private transient float scalingWidth;

    /**
     * the scaling factor for drawing birds
     */
    private transient float scaleFactor;

    /**
     * Collection of the birds that have been placed
     */
    private ArrayList<Bird> birds = new ArrayList<>();

    /**
     * The first player in the game
     */
    private Player player1;

    /**
     * The second player in the game
     */
    private Player player2;

    /**
     * The player turn: the first player to go for 0, or the second player to go for 1
     */
    private int playerTurn = 0;

    /**
     * The current round number (0 based)
     */
    private int roundNum = 0;

    /**
     * Is there a bird currently being dragged
     */
    private Bird dragging = null;

    /**
     * Most recent relative X touch when dragging
     */
    private float lastRelX;

    /**
     * Most recent relative Y touch when dragging
     */
    private float lastRelY;

    /**
     * The current stage of the game
     */
    private GameState state = GameState.birdSelection;

    /**
     * @param context the current context
     */
    public Game(Context context) {

        // Create the paint for outlining the play area
        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(BORDER_WIDTH);
        outlinePaint.setColor(Color.RED);

        // Birds will be scaled so that the game is "1.5 ostriches" wide
        Bitmap scaleBird = BitmapFactory.decodeResource(context.getResources(), R.drawable.ostrich);
        scalingWidth = scaleBird.getWidth()*1.5f;

        // load the temp bird image
        birds.add(new Bird(context, R.drawable.hummingbird));
        birds.add(new Bird(context, R.drawable.parrot));
        birds.add(new Bird(context, R.drawable.seagull));
        dragging = birds.get(0);
    }

    public boolean inSelectionState() {
        return state == GameState.birdSelection;
    }

    /**
     * Get the current player who's turn it is
     * @return the player who's turn it is
     */
    private Player getCurrentPlayer() {
        if(playerTurn == 0) {
            if(roundNum % 2 == 0) return player1;
            else return player2;
        }
        else {
            if(roundNum % 2 == 1) return player2;
            else return player1;
        }
    }

    /**
     * Get the player who's turn is next
     * @return the player who's turn is next
     */
    private Player getNextPlayer() {
        if(getCurrentPlayer() == player1) return player2;
        else return player1;
    }

    /**
     * Advance the game by one turn
     */
    private void advanceTurn() {
        if(isSecondTurn()) {
            if(state == GameState.birdSelection) {
                state = GameState.birdPlacement;
            }
            else {
                state = GameState.birdSelection;
                roundNum++;
            }

            playerTurn = 0;
        }
        else {
            playerTurn = 1;
        }
    }

    /**
     * Get whether the second player in the current state has their turn now
     * @return true if the second player in the current state is playing; false otherwise
     */
    private boolean isSecondTurn() {
        return playerTurn == 1;
    }

    public void setPlayerNames(String name1, String name2) {
        player1 = new Player(name1);
        player2 = new Player(name2);

        state = GameState.birdSelection;
    }

    /**
     * Set the current player's bird selection
     * @param selection the bird selected to place this round
     */
    public void setPlayerSelection(Bird selection) {
        getCurrentPlayer().setSelectedBird(selection);

        advanceTurn();
    }

    /**
     * Confirms the player has chosen where their bird goes
     */
    public void confirmBirdPlacement() {
        // Check to see if the player's bird collides with any other bird
        for(int itr = 0; itr < birds.size(); itr++) {
            if(getCurrentPlayer().getSelectedBird().collisionTest(birds.get(itr))) {
                declareWinner(getNextPlayer());
                return;
            }
        }

        birds.add(getCurrentPlayer().getSelectedBird());

        advanceTurn();
    }

    private void declareWinner(Player winner) {

    }

    /**
     * Gets text to display to the player when they need to select a bird
     * @return the text to tell the player to select their bird
     */
    public String getBirdSelectionText() {
        return "Select your bird " + getCurrentPlayer().getName();
    }

    /**
     * Gets text to display to the player when they need to place a bird
     * @return the text to tell the player to place their bird
     */
    public String getBirdPlacementText() {
        return "Place your bird " + getCurrentPlayer().getName();
    }

    /**
     * Draw the game
     * @param canvas the canvas to draw on
     */
    public void draw(Canvas canvas) {

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // The puzzle size is the view scale ratio of the minimum dimension, to make it square
        gameSize = (int)((width < height ? width : height) * SCALE_IN_VIEW);

        // Margins for centering the puzzle
        marginX = (width - gameSize) / 2;
        marginY = (height - gameSize) / 2;

        // Draw the outline of the gameplay area
        canvas.drawRect(marginX - BORDER_WIDTH, marginY - BORDER_WIDTH,
                marginX + gameSize + BORDER_WIDTH, marginY + gameSize + BORDER_WIDTH, outlinePaint);

        scaleFactor = gameSize/scalingWidth;

        for (Bird bird : birds) {
            bird.draw(canvas, marginX, marginY, gameSize, scaleFactor);
        }
    }

    public void reloadBirds(Context context) {
        for (Bird bird : birds) {
            bird.reloadBitmap(context);
        }

        // Birds will be scaled so that the game is "1.5 ostriches" wide
        Bitmap scaleBird = BitmapFactory.decodeResource(context.getResources(), R.drawable.ostrich);
        scalingWidth = scaleBird.getWidth()*1.5f;
    }

    /**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {

        // Convert an x,y location to a relative location in the puzzle
        float relX = (event.getX() - marginX) / gameSize;
        float relY = (event.getY() - marginY) / gameSize;



        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                lastRelX = relX;
                lastRelY = relY;
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_MOVE:
                if (dragging != null) {
                    dragging.move(relX - lastRelX, relY - lastRelY, gameSize, scaleFactor);
                    lastRelX = relX;
                    lastRelY = relY;
                    view.invalidate();
                    return true;
                }
                break;
        }

        return false;
    }
    public void saveInstanceState(Bundle bundle) {
        float [] locations = new float[birds.size() * 2];

        for (int i = 0; i < birds.size(); i++) {
            Bird bird = birds.get(i);
            locations[i*2] = bird.getX();
            locations[i*2+1] = bird.getY();
        }

        bundle.putFloatArray(LOCATIONS, locations);
    }

    public void loadInstanceState(Bundle bundle) {
        float [] locations = bundle.getFloatArray(LOCATIONS);

        for (int i = 0; i < birds.size(); i++) {
            Bird bird = birds.get(i);
            bird.setX(locations[i*2]);
            bird.setY(locations[i*2+1]);
        }
    }
}
