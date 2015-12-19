package com.example.nina.myplaylist;

/**
 * Created by nina on 12/12/2015.
 */
       import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.squareup.picasso.Picasso;

        import org.json.JSONArray;
       import org.json.JSONException;
       import org.json.JSONObject;

public class ListViewJsonAdapter extends BaseAdapter
{

    Context myContext;
    LayoutInflater myInflater;
    JSONArray myJsonArray;

    String video_Id;

    public ListViewJsonAdapter(Context context, LayoutInflater inflater) {
        myContext = context;
        myInflater = inflater;
        myJsonArray = new JSONArray();
    }

    @Override
    public int getCount() {

        return myJsonArray.length();
    }

    @Override
    public Object getItem(int position) {

        return myJsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        // your particular dataset uses String IDs
        // but you have to put something in this method
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        // check if the view already exists
        // if so, no need to inflate and findViewById again!
        if (convertView == null) {

            // Inflate the custom playlist layout from your XML.
            convertView = myInflater.inflate(R.layout.playlist, null);

            // create a new "Holder" with subviews
            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.video_thumbnail);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.video_title);

            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        } else {

            // skip all the expensive inflation/findViewById
            // and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            // Get the current book's data in JSON form
            JSONObject jsonObject = (JSONObject) getItem(position);

            if (jsonObject.has("snippet")) {

                JSONObject image = jsonObject.getJSONObject("snippet");
                JSONObject image1 = image.getJSONObject("thumbnails");
                JSONObject image2 = image1.getJSONObject("medium");
                String imageURL = image2.getString("url");


                // Temporarily have a placeholder in case it's slow to load
                Picasso.with(myContext).load(imageURL).placeholder(R.drawable.video).into(holder.thumbnailImageView);
            } else {

                // If there is no cover ID in the object, use a placeholder
                holder.thumbnailImageView.setImageResource(R.drawable.video);
            }

            // Grab the title and author from the JSON
            String videoTitle = "";


            if (jsonObject.has("snippet")) {

                JSONObject title = jsonObject.getJSONObject("snippet");
                videoTitle= title.getString("title");

            }

            if (jsonObject.has("id")) {
           JSONObject id = jsonObject.getJSONObject("id");
                 video_Id = id.getString("videoId");
            }

        // Send these Strings to the TextViews for display
            holder.titleTextView.setText(videoTitle);
            holder.titleTextView.setTag(video_Id);
        }
        catch (JSONException e) {
        e.printStackTrace();
    }

        return convertView;
    }

    public void updateData(JSONArray jsonArray) {
        // update the adapter's dataset
        myJsonArray = jsonArray;
        notifyDataSetChanged();
    }

    // this is used so you only ever have to do
    // inflation and finding by ID once ever per View
    public static class ViewHolder {
        public ImageView thumbnailImageView;
        public TextView titleTextView;
    }
}
