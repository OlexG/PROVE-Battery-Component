package com.example.batterycomponent;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNumber;
    private String userInput = "";
    private BatteryComponent batteryComponent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            // Create a new instance of your fragment
            this.batteryComponent = BatteryComponent.newInstance(
                    100, 10, Color.WHITE);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.battery_fragment_container, batteryComponent)
                    .commit();
        }

        this.editTextNumber = findViewById(R.id.editTextNumber);
        editTextNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                userInput = editable.toString();
                if (userInput.length() > 0) {
                    int percentage = Integer.parseInt(userInput);
                    batteryComponent.setPercentage(percentage);
                }
            }
        });
    }

    // Other lifecycle methods and event-handling code can go here
}