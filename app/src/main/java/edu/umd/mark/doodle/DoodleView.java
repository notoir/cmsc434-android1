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

    private Paint _currentPaint = new Paint();
    private Path _currentPath = new Path();

    public static Paint getCopy(Paint original) {
        Paint newPaint = new Paint();
        newPaint.setColor(original.getColor());
        newPaint.setAntiAlias(original.isAntiAlias());
        newPaint.setStyle(original.getStyle());
        newPaint.setStrokeWidth(original.getStrokeWidth());
        newPaint.setAlpha(original.getAlpha());

        return newPaint;
    }

    public class PaintPath {
        public Path _path;
        public Paint _paint;

        public PaintPath(Path path, Paint paint) {
            this._path = path;
            this._paint = paint;
        }
    }

    private ArrayList<PaintPath> brushStrokes;

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
        _currentPaint.setColor(Color.RED);
        _currentPaint.setAntiAlias(true);
        _currentPaint.setStyle(Paint.Style.STROKE);
        _currentPaint.setStrokeWidth(30);
        brushStrokes = new ArrayList<PaintPath>();

    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(PaintPath strokes : this.brushStrokes) {
            canvas.drawPath(strokes._path, strokes._paint);
        }
        canvas.drawPath(this._currentPath, this._currentPaint);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float touchX = motionEvent.getX();
        float touchY = motionEvent.getY();

        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                _currentPath = new Path();
                _currentPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                _currentPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                PaintPath p = new PaintPath(_currentPath, _currentPaint);
                brushStrokes.add(p);
                break;
        }

        invalidate();
        return true;
    }

    public void clearDoodle() {
        for(PaintPath p : brushStrokes) {
            p._path.reset();
        }
        brushStrokes.clear();
        invalidate();
    }

    public void setBrushSize(CharSequence brushSize) {
        Paint newPaint = getCopy(this._currentPaint);
        if(brushSize.equals("Small")) {
            newPaint.setStrokeWidth(30);
        } else if(brushSize.equals("Medium")) {
            newPaint.setStrokeWidth(60);
        } else {
            newPaint.setStrokeWidth(90);
        }
        this._currentPaint = newPaint;
    }

    public void setColor(String color) {
        Paint newPaint = getCopy(this._currentPaint);
        if(color.equals("Red")) {
            newPaint.setColor(Color.RED);
        } else if(color.equals("Blue")) {
            newPaint.setColor(Color.BLUE);
        } else if(color.equals("Yellow")) {
            newPaint.setColor(Color.YELLOW);
        } else if(color.equals("Green")) {
            newPaint.setColor(Color.GREEN);
        } else if(color.equals("Magenta")) {
            newPaint.setColor(Color.MAGENTA);
        } else {
            newPaint.setColor(Color.BLACK);
        }
        this._currentPaint = newPaint;
    }

    public void setOpacity(String opacity) {
        Paint newPaint = getCopy(this._currentPaint);
        int opacityVal = 0;
        if(opacity.equals("20%")) {
            opacityVal = 51;
        } else if(opacity.equals("40%")) {
            opacityVal = 102;
        } else if(opacity.equals("60%")) {
            opacityVal = 153;
        } else if(opacity.equals("80%")) {
            opacityVal = 204;
        } else {
            opacityVal = 255;
        }
        newPaint.setAlpha(opacityVal);
        this._currentPaint = newPaint;
        //invalidate();
    }

    public void undo() {
        this.brushStrokes.get(this.brushStrokes.size()-1)._path.reset();
        invalidate();
        this.brushStrokes.remove(this.brushStrokes.size()-1);
        invalidate();
    }
}