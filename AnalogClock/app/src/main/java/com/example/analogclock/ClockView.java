package com.example.analogclock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class ClockView extends View {
    Paint blackPaint; // color for the clock
    Context c;
    float seconds; // number of seconds elapsed


    // Constructors
    public ClockView(Context context) {
        super(context);
        c = context;
        init();

    }

    // alternate constructor
    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        c = context;
        init();
    }

    // initialize
    protected void init() {
        // start at a random number of seconds
        Random rand = new Random();
        seconds = rand.nextInt(5000) + 1000;

        // set the paint to black and stroke style
        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle( Paint.Style.STROKE );

        // start our timer in one second
        View clockView = findViewById(R.id.clock_view);
        clockView.postDelayed( timer, 1000 );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // get hight and width
        int height = canvas.getHeight();
        int width = canvas.getWidth();

        // find the center point
        int centerX = width / 2;
        int centerY = height / 2;

        // radius of clock is the minimum of height and width
        int radius = Math.min(height, width) / 2 - 50;


        // length of hour and second/minute marks
        float hourLength = radius/8;
        float minuteLength = radius/7;

        // draw the outer circle on the clock
        blackPaint.setStrokeWidth(radius/20);
        canvas.drawCircle(centerX, centerY, radius, blackPaint);

        // draw all the second/minute and hour markings on the clock
        blackPaint.setStrokeWidth(radius/20);
        drawCircularLines(centerX, centerY, radius, hourLength, 12, canvas, 0);
        blackPaint.setStrokeWidth(radius/40);
        drawCircularLines(centerX, centerY, radius, minuteLength, 60, canvas, 5);

        // draw the second, minute, and hour hands
        drawHands(centerX, centerY, radius, canvas);

    }

    /*
        draw the second, minute, and hour hands
        starting at the center point
    */

    private void drawHands(int centerX, int centerY, int radius, Canvas canvas) {
        // drawing the second hand
        // multiply the number of seconds by 6 and mod by 360
        float angle = (6 * seconds)%360 - 90;

        // calculate the point on the circle to draw to
        float x = centerX + (float)(Math.cos(Math.toRadians(angle)) * (radius * .75)); // length of second hand is 3/4 radius
        float y = centerY + (float)(Math.sin(Math.toRadians(angle)) * (radius * .75));
        blackPaint.setStrokeWidth(2);
        canvas.drawLine(x, y, centerX, centerY, blackPaint);

        // draw the hour hand
        // number of seconds/3600 * (360/12) because there are twelve hour markings
        angle = (30 * seconds/3600)%360 - 90;

        // calculating the x and y for the point
        x = centerX + (float)(Math.cos(Math.toRadians(angle)) * (radius * .5)); // length of hour hand is 1/2 radius
        y = centerY + (float)(Math.sin(Math.toRadians(angle)) * (radius * .5));
        blackPaint.setStrokeWidth(15);
        canvas.drawLine(x, y, centerX, centerY, blackPaint);

        // drawing the second hand
        // (360/60) * (seconds/60)
        angle = (6 * seconds/60)%360 - 90;

        x = centerX + (float)(Math.cos(Math.toRadians(angle)) * (radius * .6));
        y = centerY + (float)(Math.sin(Math.toRadians(angle)) * (radius * .6));
        blackPaint.setStrokeWidth(10);
        canvas.drawLine(x, y, centerX, centerY, blackPaint);
    }

    private void drawCircularLines(float centerX, float centerY, float radius, float length, float divisions,  Canvas canvas, int skip) {
        int increment = (int)(360/divisions);
        for (float i = 0; i <= 360; i = i + increment) {
            if (i % skip == 0) {
                continue;
            }
            float angle = i - 90;
            float x1 = centerX + ((float)Math.cos(Math.toRadians(angle)) * (radius - 30));
            float y1 = centerY + ((float)Math.sin(Math.toRadians(angle)) * (radius - 30));
            float x2 = 0;
            float y2 = 0;
            if (centerX == x1) {
                x2 = x1;
                if (y1 > centerY) {
                    y2 = y1 - length;
                }
                else {
                    y2 = y1 + length;
                }
            }
            else {
                float m = (y1 - centerY) / (x1 - centerX);
                // y = mx + b
                //b = y - mx
                float b = y1 - m * x1;

                x2 = x1 - (length * (x1 - centerX)) / radius;
                y2 = m * x2 + b;
            }
            canvas.drawLine(x1, y1, x2, y2, blackPaint); //draw a line from center point back to the point
        }
    }
    Runnable timer = new Runnable() {
        @Override
        public void run() {
            View clockView = findViewById(R.id.clock_view);
            invalidate();
            seconds ++;
            clockView.postDelayed( this, 1000 );
        }
    };

}
