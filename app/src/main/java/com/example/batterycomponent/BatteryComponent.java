package com.example.batterycomponent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.view.ViewTreeObserver;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BatteryComponent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BatteryComponent extends Fragment {

    private static final String ARG_PERCENTAGE = "100";
    private static final String ARG_PRECISION = "10";
    private static final String ARG_BG_COLOR = Integer.toString(Color.GRAY);

    private int percentage;
    private int precision;
    private int bgColor;
    private BatteryView batteryView;
    private BatteryChargeText batteryChargeText;
    public BatteryComponent() {

    }

    /**
     * Create a new battery
     *
     * @param percentage Battery percentage
     * @return A new instance of fragment BatteryComponent.
     */
    public static BatteryComponent newInstance(int percentage, int precision, int bgColor) {
        BatteryComponent fragment = new BatteryComponent();
        Bundle args = new Bundle();
        args.putInt(ARG_PERCENTAGE, percentage);
        args.putInt(ARG_PRECISION, precision);
        args.putInt(ARG_BG_COLOR, bgColor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            percentage = getArguments().getInt(ARG_PERCENTAGE);
            precision = getArguments().getInt(ARG_PRECISION);
            bgColor = getArguments().getInt(ARG_BG_COLOR);
        }
    }

    private BatteryView drawBatteryOutline(int width, int height) {
        BatteryView batteryView = new BatteryView(getActivity(), this.bgColor, this.precision);
        batteryView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        return batteryView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Use a FrameLayout to allow views to overlap
        FrameLayout batteryLayout = new FrameLayout(getActivity());

        // Add in the battery outline, use parent width and wrap content for height
        BatteryView batteryView = drawBatteryOutline(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        this.batteryView = batteryView;
        batteryLayout.addView(batteryView);

        // Add battery charge text over the battery
        BatteryChargeText batteryChargeText = new BatteryChargeText(getActivity(), this.percentage, this.bgColor);
        FrameLayout.LayoutParams textLayoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textLayoutParams.gravity = Gravity.CENTER;

        // Calculate left margin as -10% of parent width
        batteryChargeText.setLayoutParams(textLayoutParams);
        this.batteryChargeText = batteryChargeText;
        batteryLayout.addView(batteryChargeText);

        batteryView.setBatteryPercentage(this.percentage);
        batteryChargeText.setBatteryPercentage(this.percentage);

        batteryLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int parentWidth = batteryLayout.getWidth(); // Get the parent's width
                int leftMargin = -parentWidth / 20; // -5% of parent width
                textLayoutParams.leftMargin = leftMargin;
                batteryChargeText.setLayoutParams(textLayoutParams);

                // Remove the listener to prevent multiple calls
                batteryLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        return batteryLayout;
    }





    public void setPercentage(int percentage) {
        this.percentage = percentage;
        this.batteryView.setBatteryPercentage(percentage);
        this.batteryChargeText.setBatteryPercentage(percentage);
    }


}