package edu.msu.stanospa.teamgoldfinch_project1;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Ethan on 2/12/15.
 */
public class SelectionView extends View {


    /**
     * The actual selection view
     */
    private Selection selection;

    public SelectionView(Context context) {
        super(context);
        init(null, 0);
    }

    public SelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SelectionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        selection = new Selection(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        selection.draw(canvas);
    }

}

