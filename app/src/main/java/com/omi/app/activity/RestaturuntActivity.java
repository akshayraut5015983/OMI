package com.omi.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.omi.app.R;
import com.omi.app.adapter.MyHotelAdapter;
import com.omi.app.models.Hotel;

public class RestaturuntActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaturunt);
        getSupportActionBar().hide();


        Hotel[] myListData = new Hotel[]{
                new Hotel(R.drawable.veg1, "The Briyani Wall"),
                new Hotel(R.drawable.veg2, "Wai Wai City"),
                new Hotel(R.drawable.img3, "Happy Dosa"),
                new Hotel(R.drawable.veg1, "The Briyani Wall"),
                new Hotel(R.drawable.veg2, "Wai Wai City"),
                new Hotel(R.drawable.img3, "Happy Dosa"),
                new Hotel(R.drawable.veg1, "The Briyani Wall"),
                new Hotel(R.drawable.veg2, "Wai Wai City"),
                new Hotel(R.drawable.img3, "Happy Dosa"),

        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcylersubservice);
        MyHotelAdapter adapter = new MyHotelAdapter(myListData, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);

    }

    public void backimage(View view) {
        finish();
    }


}
