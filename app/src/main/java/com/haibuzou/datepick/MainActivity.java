package com.haibuzou.datepick;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.haibuzou.datepick.cons.DPMode;
import com.haibuzou.datepick.views.MonthView;

public class MainActivity extends AppCompatActivity {

    private MonthView dp;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        dp = (MonthView)findViewById(R.id.main_dp);
        dp.setDPMode(DPMode.SINGLE);
        dp.setDate(2015, 11);
//        list = (ListView)findViewById(R.id.list);
//        List<String> data = new ArrayList<>();
//        for(int i = 0 ; i<10; i++){
//            data.add(""+i);
//        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
//        list.setAdapter(adapter);
    }

}
