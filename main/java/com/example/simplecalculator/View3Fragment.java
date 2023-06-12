/*
        date: 2023-06-08
        file: View3Fragment.java
        author: Jaime Rump
        desc: This fragment supports the 'set username' option for users
 */
package com.example.simplecalculator;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class View3Fragment extends Fragment {
    public interface OnDataPassListener {
        void onDataPass2(String data);
    }

    private View3Fragment.OnDataPassListener dataPassListener;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    Button button_update_username;
    EditText editText_username;
    String username;

    public View3Fragment() {
        // Required empty public constructor
    }

    public static View3Fragment newInstance(String param1, String param2) {
        View3Fragment fragment = new View3Fragment();
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
        View rootView = inflater.inflate(R.layout.fragment_view3, container, false);
        editText_username = rootView.findViewById(R.id.editText_username);
        button_update_username = rootView.findViewById(R.id.button_update_username);
        button_update_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = editText_username.getText().toString();
                Toast.makeText(getActivity(), username, Toast.LENGTH_SHORT).show();
                dataPassListener.onDataPass2(username);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dataPassListener = (View3Fragment.OnDataPassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDataPassListener");
        }
    }
}