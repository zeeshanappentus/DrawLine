package com.example.drawzgesture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

public class MyDrawView extends View implements View.OnTouchListener {

    Paint paint = new Paint();
    ArrayList<Line> list = new ArrayList<>();
    GestureDetector gestureDetector;
    boolean actionStarted = false;
    Point startPoint = new Point();
    Point endPoint = new Point();
    boolean actionCompleted = false;

    Context context;
    Canvas canvas;

    public MyDrawView(Context context) {
        super(context);
        init(context);
    }

    public MyDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        this.context = context;
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(6f);

        gestureDetector = new GestureDetector(context, new MyGestureDetector());
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(actionCompleted){
            this.canvas = canvas;
            Log.d("TAG1", "onDraw: 1");
            for (Line l : list)
               canvas.drawLine((float) l.startX, (float) l.startY, (float) l.endX, (float) l.endY, paint);
//               canvas.drawLine((float) startPoint.x, (float) startPoint.y, (float) endPoint.x, (float) endPoint.y, paint);

        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        Log.d("TAG2", "onMeasure: "+size);
        display.getSize(size);
        Log.d("TAG3", "onMeasure: "+size.x +","+ size.y );
        setMeasuredDimension( size.x, size.y);
    }

    public class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("learning_gesture", "onDown: done");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("learning_gesture", "onLongPress: done");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if(actionStarted){

                actionStarted = false;
//                endPoint.x = (int) e.getX();
//                endPoint.y = (int) e.getY();
                Line currentLine = list.get(list.size() - 1);
                currentLine.endX = e.getX();
                currentLine.endY = e.getY();

                actionCompleted = true;
                invalidate();
            }else{
                actionStarted = true;
//                startPoint.x = (int) e.getX();
//                startPoint.y = (int) e.getY();
                list.add(new Line((int) e.getX(), (int) e.getY()));

            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("learning_gesture", "onDoubleTap: done");
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("learning_gesture", "onScroll: done");
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            (x1,y1)
//            float x1 = e1.getX();
//            float y1 = e1.getY();
//
//            (x2,y2)
//            float x2 = e2.getY();
//            float y2 = e2.getY();
//
//            calculating distance between two point...
//            double distance = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
//            Log.d("learning_gesture_Fling", "onFling: done "+ distance);
            return true;
        }
    }

    class Line {
        float startX, startY, endX, endY;
        public Line(float startX, float startY, float endX, float endY) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }
        public Line(float startX, float startY) {
            this.startX = startX;
            this.startY = startY;
//            this(startX, startY, startX, startY);
        }

    }
}
