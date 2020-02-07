package com.omi.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omi.app.R;
import com.omi.app.adapter.LaundryAdapter;
import com.omi.app.models.Clothes;

public class TabLaundry extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=  inflater.inflate(R.layout.tab_laundry, container, false);

        Clothes[] myListData = new Clothes[] {
                new Clothes(R.drawable.hoodie,"Hodiies",150),
                new Clothes(R.drawable.shirt,"Shirt/Top",60),
                new Clothes(R.drawable.skirtlong, "Skirt Long",80),
                new Clothes(R.drawable.skirtshort, "Shirt Small",10),

        };

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.tablaundryrv);
        LaundryAdapter adapter = new LaundryAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return  view;

    }
}