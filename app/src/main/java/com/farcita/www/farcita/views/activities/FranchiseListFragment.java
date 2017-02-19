package com.farcita.www.farcita.views.activities;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.farcita.www.farcita.R;

import java.util.ArrayList;


public class FranchiseListFragment extends DialogFragment {

    private ArrayList<String> franchiseArray = new ArrayList<String>();
    private View v;
    private ListView list;
    private ActivityCommunicator activityCommunicator;
    private EditText search;
    private ArrayAdapter<String> adapter;

    public FranchiseListFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityCommunicator = (ActivityCommunicator) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        v = inflater.inflate(R.layout.fragment_franchise_list, container, false);
        list = (ListView) v.findViewById(R.id.listView);
        Bundle bundle = getArguments();

        if (bundle != null) { franchiseArray = bundle.getStringArrayList("franchise_array"); }

        adapter = new ArrayAdapter<String>(getActivity(),R.layout.franchise_row,R.id.text_row,franchiseArray);
        list.setAdapter(adapter);

        search =(EditText) v.findViewById(R.id.search_view);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String returnFranchise = adapter.getItem(position).toString();
                Log.d("Farcita",returnFranchise);
                activityCommunicator.passDataToActivity(returnFranchise);
                dismiss();
            }
        });

        Button button = (Button) v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameFranchise = search.getText().toString();
                Log.d("Farcita",nameFranchise);
                activityCommunicator.passDataToActivity(nameFranchise);
                dismiss();
            }
        });

        return v;
    }

}