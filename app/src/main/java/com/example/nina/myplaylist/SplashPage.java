package com.example.nina.myplaylist;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.GridView;
import android.widget.Toast;

public class SplashPage extends Activity {


    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new FrontGridAdapter(this ,getLayoutInflater()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FrontGridAdapter.ViewHolder tag =(FrontGridAdapter.ViewHolder)view.getTag();

                Toast.makeText(getBaseContext(), "Loading " + (String)tag.imgView.getTag()+" Videos", Toast.LENGTH_LONG).show();

                Intent intent =new Intent(getApplicationContext(),JsonParser.class);
                // put the name a(to be sent to other activity) in intent
                intent.putExtra("ARTIST_NAME", (String)tag.imgView.getTag());

                // start the JsonParser activity
                startActivity(intent);


            }
        });
    }

}
