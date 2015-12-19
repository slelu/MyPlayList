package com.example.nina.myplaylist;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class JsonParser extends Activity {

    String video_id ;

    static  ListViewJsonAdapter listViewJsonAdapter;
    ListView mainListView;
    ArrayAdapter mArrayAdapter;
    ArrayList mNameList = new ArrayList();

    private static int listPosition ;
    private  String QUERY_URL ;
    private GoogleApiClient client2;
    private static String video_Id;
    static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        video_id = getIntent().getStringExtra("ARTIST_NAME");
        context = this ;

         try {

            URLEncoder.encode(video_id, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        QUERY_URL= "https://www.googleapis.com/youtube/v3/search?part=snippet&q="+video_id+"&maxResults=50&type=video&key=AIzaSyDaFQCrGFP04d_Z-QTtALQV6JbRwFx7sqA";

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        mainListView = new ListView(this);

        mArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                mNameList);

        mainListView.setAdapter(mArrayAdapter);
        listViewJsonAdapter = new ListViewJsonAdapter(this, getLayoutInflater());
        mainListView.setAdapter(listViewJsonAdapter);

        layout.addView(mainListView);

        doQuery();

        setContentView(layout);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                listPosition = position;

                Intent intent = new Intent(getApplicationContext(), YoutubePlayer.class);
                ListViewJsonAdapter.ViewHolder tag = (ListViewJsonAdapter.ViewHolder) view.getTag();

                // put the videoName a(to be sent to other activity) in intent
                intent.putExtra("VIDEO_ID", (String) tag.titleTextView.getTag());

                // start the YoutubePlayer activity
                startActivity(intent);
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void doQuery() {

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(QUERY_URL,
                new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        listViewJsonAdapter.updateData(jsonObject.optJSONArray("items"));
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error)
                    {
                        Log.e("Query Failure", statusCode + " " + throwable.getMessage());
                    }
                });
    }


    public static  void forward(View forward ,View backward ) {


        listPosition++;
        backward.setEnabled(true);
        if (!(listPosition < listViewJsonAdapter.getCount())) {
            showMessage("End of list");
            forward.setEnabled(false);

        } else {
            try {
                JSONObject object = (JSONObject) listViewJsonAdapter.getItem(listPosition);
                if (object.has("id")) {
                    JSONObject id = object.getJSONObject("id");
                    video_Id = id.getString("videoId");
                    Intent intent = new Intent(context , YoutubePlayer.class);
                    intent.putExtra("VIDEO_ID", video_Id);
                   context.startActivity(intent);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }



    public static void backward(View backward ,View forward ){
        listPosition--;
        forward.setEnabled(true);
        if (!(listPosition >=0)) {
            showMessage("End of list");
            backward.setEnabled(false);
        } else {
            try {
                JSONObject object = (JSONObject) listViewJsonAdapter.getItem(listPosition);
                if (object.has("id")) {
                    JSONObject id = object.getJSONObject("id");
                    video_Id = id.getString("videoId");
                    Intent intent = new Intent(context, YoutubePlayer.class);
                    intent.putExtra("VIDEO_ID", video_Id);
                    context.startActivity(intent);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    private static  void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}



