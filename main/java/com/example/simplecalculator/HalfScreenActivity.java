/*
        date: 2023-06-08
        file: HalfScreenActivity.java
        author: Jaime Rump
        desc: This activity is the settings menu and controls the fragments within
 */
package com.example.simplecalculator;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HalfScreenActivity extends AppCompatActivity implements ViewFragment.OnDataPassListener, View2Fragment.OnDataPassListener, View3Fragment.OnDataPassListener {
    protected FragmentManager fragmentManager;
    String decimal;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_half_screen_land);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_half_screen);
        }

        setTitle("Settings");
        if (savedInstanceState==null)
        {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ListFragment fragment = new ListFragment();
            fragmentTransaction.add(R.id.fragmentContainerView, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onDataPass(String data) {
        if (data == null) {
            Log.d("TAG", "The passed variable 'data' is null");
        }

        if (data != null && !data.equals("")) {
            if (data.equals("2")) {
                decimal = "2";
            } else if (data.equals("3")) {
                decimal = "3";
            } else if (data.equals("4")) {
                decimal = "4";
            } else {
                decimal = data;
            }
            Intent intent = new Intent(HalfScreenActivity.this, MainActivity.class);
            intent.putExtra("key", decimal);
            startActivity(intent);
        }
    }

    @Override
    public void onDataPass2(String data) {
        if (data == null) {
            Log.d("TAG", "The passed variable 'data' is null");
        }

        if (data != null && !data.equals("")) {
            Intent intent2 = new Intent(HalfScreenActivity.this, activity_welcome.class);
            intent2.putExtra("key2", data);
            startActivity(intent2);
        }
    }

    @Override
    public void onDataPass3(String data) {
        if (data == null) {
            Log.d("TAG", "The passed variable 'data' is null");
        }

        if (data != null && !data.equals("")) {
            Intent intent3 = new Intent(HalfScreenActivity.this, MainActivity.class);
            intent3.putExtra("key3", data);
            value = data;
            startActivity(intent3);
        }
    }

    @Override
    public void onDataPass4(String data) {
        if (data == null) {
            Log.d("TAG", "The passed variable 'data' is null");
        }

        if (data != null && !data.equals("")) {
            Intent intent3 = new Intent(HalfScreenActivity.this, MainActivity.class);
            intent3.putExtra("key4", data);
            value = data;
            startActivity(intent3);
        }
    }
}
