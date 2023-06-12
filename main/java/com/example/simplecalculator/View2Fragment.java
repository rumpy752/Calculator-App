/*
        date: 2023-06-08
        file: View2Fragment.java
        author: Jaime Rump
        desc: This fragment supports the 'set decimals' options for users
 */
package com.example.simplecalculator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class View2Fragment extends Fragment {
    public interface OnDataPassListener {
        void onDataPass(String data);
    }

    private OnDataPassListener dataPassListener;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    Button two_decimal, three_decimal, four_decimal, update;
    String decimal_place;
    EditText editText;
    int number;
    String text;

    public View2Fragment() {
        // Required empty public constructor
    }

    public static View2Fragment newInstance(String param1, String param2) {
        View2Fragment fragment = new View2Fragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rootView = inflater.inflate(R.layout.fragment_view2_land, container, false);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            rootView = inflater.inflate(R.layout.fragment_view2, container, false);
        }

        editText = rootView.findViewById(R.id.editText_custom);
        update = rootView.findViewById(R.id.button_update_decimal);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = editText.getText().toString();
                if (text != null && !text.equals("")) {
                    number = Integer.parseInt(text);
                    if (number < 0) {
                        Toast.makeText(getActivity(), "Number must be greater than 0", Toast.LENGTH_SHORT).show();
                    } else if (number > 10) {
                        Toast.makeText(getActivity(), "Number cannot be greater than 10", Toast.LENGTH_SHORT).show();
                    } else {
                        decimal_place = text;
                        dataPassListener.onDataPass(decimal_place);
                    }
                } else {
                    Log.d("TAG", "Text is null");
                }
            }
        });

        two_decimal = rootView.findViewById(R.id.button_two_decimal);
        three_decimal = rootView.findViewById(R.id.button_three_decimal);
        four_decimal = rootView.findViewById(R.id.button_four_decimal);

        two_decimal.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decimal_place = "2";
                dataPassListener.onDataPass(decimal_place);

            }
        });

        three_decimal.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decimal_place = "3";
                dataPassListener.onDataPass(decimal_place);
            }
        });

        four_decimal.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decimal_place = "4";
                dataPassListener.onDataPass(decimal_place);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dataPassListener = (OnDataPassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDataPassListener");
        }
    }
}