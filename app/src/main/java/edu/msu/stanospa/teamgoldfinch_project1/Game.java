package edu.msu.stanospa.teamgoldfinch_project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class Game {
    /**
     * Used to track what state the game is currently in
     */
    private enum GameState {
        birdSelection,
        birdPlacement
    }

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
     * The first player in the game
     */
    private Player player1;

    /**
     * The second player in the game
     */
    private Player player2;

    /**
     * The player that won the game
     */
    private Player winner = null;

    /**
     * The player turn: the first player to go for 0, or the second player to go for 1
     */
    private int playerTurn = 0;

    /**
     * The current round number (0 based)
     */
    private int roundNum = 0;

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
        outlinePaint.setStrokeWidth(3.0f);
        outlinePaint.setColor(Color.RED);

        // Birds will be scaled so that the game is "1.5 ostriches" wide
        Bitmap scaleBird = BitmapFactory.decodeResource(context.getResources(), R.drawable.ostrich);
        scalingWidth = scaleBird.getWidth()*1.5f;

        // load the temp bird image
        birds.add(new Bird(context, R.drawable.ostrich, 0.259f, 0.238f));
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
                winner = getNextPlayer();
                return;
            }
        }

        birds.add(getCurrentPlayer().getSelectedBird());

        advanceTurn();
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
        canvas.drawRect(marginX, marginY, marginX + gameSize, marginY + gameSize, outlinePaint);

        scaleFactor = gameSize/scalingWidth;

        for (Bird bird : birds) {
            bird.draw(canvas, marginX, marginY, gameSize, scaleFactor);
        }
    }
}
