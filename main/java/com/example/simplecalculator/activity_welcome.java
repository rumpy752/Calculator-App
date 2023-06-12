/*
        date: 2023-06-08
        file: activity_welcome.java
        author: Jaime Rump
        desc: Handles the welcome activity in the app
 */
package com.example.simplecalculator;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class activity_welcome extends AppCompatActivity {
    private TextView dateTextView, textView_username;
    private static final long DELAY_MS = 2000; // 2 second delay
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        textView_username = findViewById(R.id.textView_welcome);
        dateTextView = findViewById(R.id.dateTextView);
        updateCurrentDate();

        // Create a handler to delay the activity transition
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activity_welcome.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, DELAY_MS);
    }

    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        data = intent.getStringExtra("key2");

        if (data == null) {
            Log.d("TAG", "The passed variable 'data' is null");
        } else if (data != null) {
            textView_username.setText("Welcome, " + data + "!");
        }
    }

    private void updateCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        String formattedDate = sdf.format(currentDate);
        dateTextView.setText(formattedDate);
    }
}