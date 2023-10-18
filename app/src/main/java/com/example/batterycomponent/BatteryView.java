package com.example.batterycomponent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

class BatteryView extends View {
    private int bgColor;
    private Paint paint;
    private int precision;
    private int batteryPercentage = 0;

    public void setBatteryPercentage(int batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
        invalidate(); // This will trigger the onDraw method to re-draw the view
    }

    public BatteryView(Context context, int bgColor, int precision) {
        super(context);
        this.bgColor = bgColor;
        paint = new Paint();
        paint.setColor(bgColor);
        paint.setStyle(Paint.Style.FILL);
        this.precision = precision;
    }

    private void drawBatteryChargeRectangles(Canvas canvas, int precision, int batteryPercentage) {
        int spacing = 20;
        int availableWidth = (int)(getWidth() * 0.9) - (spacing * (precision)) - (int)(20);  // account for border radius on both sides
        int rectangleWidth = availableWidth / precision;

        int rectangleHeight = getHeight() - 2;
        int amountOfRectangles = (int) Math.ceil(batteryPercentage / (100.0 / precision));
        int rectangleLeft = spacing;
        int rectangleTop = spacing;
        int rectangleRight = rectangleLeft + rectangleWidth;
        int rectangleBottom = rectangleHeight - spacing;
        float cornerRadius = 10;

        for (int i = 0; i < amountOfRectangles; i++) {
            // Rectangeles progressively go from green to yellow to red
            int red = 0;
            int green = 0;
            int blue = 0;
            if (i < (precision / 2)) {
                red = 255;
                green = (int) (255 * (i / (precision / 2.0)));
            } else {
                green = 255;
                red = (int) (255 * ((precision - i) / (precision / 2.0)));
            }
            paint.setColor(Color.rgb(red, green, blue));
            canvas.drawRoundRect(rectangleLeft, rectangleTop, rectangleRight, rectangleBottom, cornerRadius, cornerRadius, paint);
            rectangleLeft += rectangleWidth + spacing;
            rectangleRight = rectangleLeft + rectangleWidth;
        }

        // Draw gray hollowed out rectangles for the remaining battery percentage
        paint.setColor(new Color().rgb(220, 220, 220));
        for (int i = amountOfRectangles; i < precision; i++) {
            canvas.drawRoundRect(rectangleLeft, rectangleTop, rectangleRight, rectangleBottom, cornerRadius, cornerRadius, paint);
            rectangleLeft += rectangleWidth + spacing;
            rectangleRight = rectangleLeft + rectangleWidth;
        }
    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(bgColor);

        // Main battery body
        float bodyLeft = 0;
        float bodyTop = 0;
        float bodyRight = getWidth() * 0.9f; // 90% of the width for main body
        float bodyBottom = getHeight();
        canvas.drawRoundRect(bodyLeft, bodyTop, bodyRight, bodyBottom, 20, 20, paint);

        // Battery charge level
        drawBatteryChargeRectangles(canvas, this.precision, batteryPercentage);
        
        paint.setColor(bgColor);
        // Battery terminal rounded
        float terminalLeft = bodyRight;
        float terminalTop = getHeight() * 0.35f;
        float terminalRight = getWidth();
        float terminalBottom = getHeight() * 0.65f;
        canvas.drawRoundRect(terminalLeft, terminalTop, terminalRight, terminalBottom, 0, 10, paint);
    }
}

