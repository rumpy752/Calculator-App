/*
        date: 2023-06-08
        file: ListFragment.java
        author: Jaime Rump
        desc: This fragment supports the settings list options
 */
package com.example.simplecalculator;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.jetbrains.annotations.Nullable;

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FragmentManager fragmentManager;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] cities = getResources().getStringArray(R.array.string_array_settings);
        ListView listView = (ListView) view.findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, cities);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentManager = requireActivity().getSupportFragmentManager();

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Fragment fragment = null;

        switch (i) {
            case 0:
                    fragment = new ViewFragment();
                break;
            case 1:
                    fragment = new View2Fragment();
                    break;
            case 2:
                    fragment = new View3Fragment();
                    break;
            default:
                    break;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, fragment)
                .commit();
    }
}