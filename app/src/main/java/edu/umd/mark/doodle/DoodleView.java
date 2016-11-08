package edu.umd.mark.doodle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Mark on 10/31/2016.
 */

public class DoodleView extends View {

    private Paint _paintDoodle = new Paint();
    private ArrayList<Path> _paths;

    public DoodleView(Context context) {
        super(context);
        init(null, 0);
    }

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DoodleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        _paintDoodle.setColor(Color.RED);
        _paintDoodle.setAntiAlias(true);
        _paintDoodle.setStyle(Paint.Style.STROKE);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path p = new Path();
        _paths.add(p);
        canvas.drawPath(p, _paintDoodle);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float touchX = motionEvent.getX();
        float touchY = motionEvent.getY();
        Path newestPath = _paths.get(_paths.size()-1);

        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                newestPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                newestPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        invalidate();
        return true;
    }

    public void clearDoodle() {
        _paths.clear();
    }

    public void setBrushSize(CharSequence brushSize) {
        if(brushSize.equals("Small")) {
            _paintDoodle.setStrokeWidth(30);
        } else if(brushSize.equals("Medium")) {
            _paintDoodle.setStrokeWidth(60);
        } else {
            _paintDoodle.setStrokeWidth(90);
        }
    }
}