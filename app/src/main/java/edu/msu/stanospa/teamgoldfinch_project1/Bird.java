package edu.msu.stanospa.teamgoldfinch_project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.Serializable;

/**
 * This is a starting point for a class for a bird. It includes functions to
 * load the bird image and to do collision detection against another bird.
 */
public class Bird implements Serializable {
    /**
     * The image for the actual bird.
     */
    private transient Bitmap bird;

    /**
     * Rectangle that is where our bird is.
     */
    private transient Rect rect;

    /**
     * Rectangle we will use for intersection testing
     */
    private transient Rect overlap = new Rect();

    /**
     * The resource id for this bird's image
     */
    private int id;

    /**
     * x location
     */
    private float x = 0;

    /**
     * y location
     */
    private float y = 0;


    public Bird(Context context, int id) {
        this.id = id;
        bird = BitmapFactory.decodeResource(context.getResources(), id);
        this.x = bird.getWidth()/2;
        this.y = bird.getWidth()/2;
        //bird.move()
        rect = new Rect();
        setRect();
    }

    public void reloadBitmap(Context context) {
        bird = BitmapFactory.decodeResource(context.getResources(), id);
        rect = new Rect();
        setRect();
        overlap = new Rect();
    }

    public void move(float dx, float dy, float  gameSize) {

        float halfBirdX = bird.getWidth()/2;
        float halfBirdY = bird.getHeight()/2;

        x += dx;
        y += dy;

        if (x - halfBirdX < 0)
            x = 0;
        else if (x + halfBirdX > gameSize)
            x = gameSize - bird.getWidth();

        if (y - halfBirdY < 0)
            y = 0;
        else if (y + halfBirdY > gameSize)
            y = gameSize - bird.getHeight();

        setRect();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getPX(float gameSize, float scaleFactor) {
        return (int)(x * gameSize / scaleFactor) + bird.getWidth() / 2;
    }

    public int getPY(float gameSize, float scaleFactor) {
        return (int)(y * gameSize / scaleFactor) + bird.getHeight() / 2;
    }

    private void setRect() {
        rect.set((int)x, (int)y, (int)x+bird.getWidth(), (int)y+bird.getHeight());
    }

    public boolean hit(float testX, float testY, int gameSize, float scaleFactor) {
        //int pX = (int)((testX - x));
        //int pY = (int)((testY - y));
        int pX = (int)((testX - x) * gameSize / scaleFactor) + bird.getWidth() / 2;
        int pY = (int)((testY - y) * gameSize / scaleFactor) + bird.getHeight() / 2;

        if(pX < 0 || pX >= bird.getWidth() ||
                pY < 0 || pY >= bird.getHeight()) {
            return false;
        }
        // We are within the rectangle of the piece.
        // Are we touching actual picture?
        return (bird.getPixel(pX, pY) & 0xff000000) != 0;
    }

    /**
     * Collision detection between two birds. This object is
     * compared to the one referenced by other
     * @param other Bird to compare to.
     * @return True if there is any overlap between the two birds.
     */
    public boolean collisionTest(Bird other) {
        // Do the rectangles overlap?
        if(!Rect.intersects(rect, other.rect)) {
            return false;
        }

        // Determine where the two images overlap
        overlap.set(rect);
        overlap.intersect(other.rect);

        // We have overlap. Now see if we have any pixels in common
        for(int r=overlap.top; r<overlap.bottom;  r++) {
            int aY = (int)((r - y));
            int bY = (int)((r - other.y));

            for(int c=overlap.left;  c<overlap.right;  c++) {

                int aX = (int)((c - x));
                int bX = (int)((c - other.x));

                if( (bird.getPixel(aX, aY) & 0x80000000) != 0 &&
                        (other.bird.getPixel(bX, bY) & 0x80000000) != 0) {
                    //Log.i("collision", "Overlap " + r + "," + c);
                    return true;
                }
            }
        }

        return false;
    }

    public void draw(Canvas canvas, int marginX, int marginY) {

        canvas.save();
        canvas.translate(marginX + x, marginY + y);
        canvas.translate(-bird.getWidth() / 2, -bird.getHeight() / 2);
        canvas.drawBitmap(bird, 0, 0, null);
        canvas.restore();
    }


}