/*
        date: 2023-06-08
        file: View3Fragment.java
        author: Jaime Rump
        desc: This fragment supports the 'color' options for users
 */
package com.example.simplecalculator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class ViewFragment extends Fragment {
    public interface OnDataPassListener {
        void onDataPass3(String data);
        void onDataPass4(String data);
    }

    private ViewFragment.OnDataPassListener dataPassListener;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private TextView frag2_text;
    private Button button_colour, background_colour, button_restore;
    private int backgroundColor = Color.WHITE;
    SeekBar redSeekBar;
    SeekBar greenSeekBar;
    SeekBar blueSeekBar;
    View colorView;
    View view;
    int color;
    int minValue = 0;
    TextView valueTextView;
    int maxValue = 255;

    public ViewFragment() {
        // Required empty public constructor
    }

    public static ViewFragment newInstance(String param1, String param2) {
        ViewFragment fragment = new ViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("How to ")
                .setMessage("1. Use seek bars to select colour. \n2. Apply it to the button or background.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view = inflater.inflate(R.layout.fragment_view_land, container, false);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            view = inflater.inflate(R.layout.fragment_view, container, false);
        }

        frag2_text = (TextView) view.findViewById(R.id.frag_textView);
        valueTextView = view.findViewById(R.id.valueTextView);
        valueTextView.setText("Current Value: " + minValue);
        button_colour = view.findViewById(R.id.button);
        background_colour = view.findViewById(R.id.button2);
        button_restore = view.findViewById(R.id.buttonRestore);
        button_colour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (color != 0) {
                    button_colour.setBackgroundColor(color);
                    background_colour.setBackgroundColor(color);
                    dataPassListener.onDataPass3(String.valueOf(color));
                } else {
                    Log.d("TAG", "Color is equal to 0");
                }
            }
        });

        background_colour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (color != 0) {
                    dataPassListener.onDataPass4(String.valueOf(color));
                } else {
                    Log.d("TAG", "Color is equal to 0");
                }
            }
        });

        button_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restoreColors(view, "button");
            }
        });

        colorView = view.findViewById(R.id.colorView);
        redSeekBar = view.findViewById(R.id.redSeekBar);
        redSeekBar.setMax(maxValue - minValue);
        redSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        greenSeekBar = view.findViewById(R.id.greenSeekBar);
        greenSeekBar.setMax(maxValue - minValue);
        greenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        blueSeekBar = view.findViewById(R.id.blueSeekBar);
        blueSeekBar.setMax(maxValue - minValue);
        blueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dataPassListener = (ViewFragment.OnDataPassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDataPassListener");
        }
    }

    private void updateColor() {
        int redValue = redSeekBar.getProgress();
        int greenValue = greenSeekBar.getProgress();
        int blueValue = blueSeekBar.getProgress();
        color = Color.argb(255, redValue, greenValue, blueValue);
        String rgbString = String.format("%d, %d, %d", redValue, greenValue, blueValue);
        colorView.setBackgroundColor(color);
        valueTextView.setText("RGB: " + rgbString);
    }

    public void restoreColors(View view, String source) {
             if (getActivity() != null) {
                    getActivity().getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.white));
             }

            for (int i = 0; i <= 9; i++) {
                int buttonId = getResources().getIdentifier("bu" + i, "id", requireContext().getPackageName());
                Button button = view.findViewById(buttonId);
                button.setBackgroundColor(getResources().getColor(R.color.lightgreen));
            }

            List<Button> buttons = new ArrayList<>();
            for (Button button : buttons) {
                if (button != null) {
                    button.setBackgroundColor(getResources().getColor(R.color.darkgreen));
                }
            }
    }
}