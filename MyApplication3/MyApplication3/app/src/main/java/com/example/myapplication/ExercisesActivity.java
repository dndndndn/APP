package com.example.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.Adapter.ChoiceListAdapter;
import com.example.utils.GetData;

import java.util.List;

public class ExercisesActivity extends AppCompatActivity {

    private ChoiceListAdapter choiceListAdapter;
    private List<Bitmap> ebl;
    private ListView choicelist;
    private int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExercisesActivity.this.finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");
        int subjectId = bundle.getInt("subjectId");
        final int ChoiceLength = bundle.getInt("ChoiceLength");
        int answer = bundle.getInt("answer");
        /*GetData getData = new GetData(ExercisesActivity.this);
        getData.setOnListener(new GetData.Listener() {
            @Override
            public void result(String result) {
            }

            @Override
            public void imageresult(Bitmap bitmap) {
                count++;
                ebl.add(bitmap);
                if(count==ChoiceLength)
                    initAdapter();
            }
        });
        for (int i = 0; i < ChoiceLength; i++) {
            String url = bundle.getString("Choice" + i);
            getData.getImage(url);
        }*/


    }
    private void initAdapter()
    {
        choiceListAdapter = new ChoiceListAdapter(ExercisesActivity.this);
        choiceListAdapter.setData(ebl);
        choicelist = findViewById(R.id.choicelist);
        choicelist.setAdapter(choiceListAdapter);
    }
}
