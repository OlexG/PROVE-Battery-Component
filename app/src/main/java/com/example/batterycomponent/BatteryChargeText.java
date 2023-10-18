package com.example.batterycomponent;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;

public class BatteryChargeText extends AppCompatTextView {

    public BatteryChargeText(Context context) {
        super(context);
        initialize();
    }

    public BatteryChargeText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public BatteryChargeText(Context context, int percentage, int bgColor) {
        super(context);
        initialize();
        setBatteryPercentage(percentage);
        setWidth(280);
        setHeight(280);
    }

    private void initialize() {
        // Set text size and color
        setTextSize(30);
        setTextColor(Color.BLACK);
        setTypeface(Typeface.DEFAULT_BOLD);
        setClickable(true);
        setFocusable(true);
        setOnClickListener((view -> {
            // Display a dialog with an input field
            displayInputDialog();
        }));
        // Set background to the circular drawable
        setBackgroundResource(R.drawable.circle_background);

        // Center text within the circle
        setGravity(Gravity.CENTER);
    }

    private void displayInputDialog() {
        // Obtain the context
        Context context = getContext();

        // Inflate the closable input layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View inputLayout = inflater.inflate(R.layout.closable_input_box, null);

        // Create a new AlertDialog to show the input layout
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialogStyle);
        builder.setView(inputLayout);

        Button closeButton = inputLayout.findViewById(R.id.close_button);

        TextView textView1 = inputLayout.findViewById(R.id.text_view1);
        TextView textView2 = inputLayout.findViewById(R.id.text_view2);
        TextView textView3 = inputLayout.findViewById(R.id.text_view3);

        textView1.setText("Percentage: " + this.getText());
        textView2.setText("Some other statistic");
        textView3.setText("Some 2nd statistic");

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }



    public void setBatteryPercentage(int percentage) {
        setText(percentage + "%");
    }
}
