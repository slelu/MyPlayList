package com.example.nina.myplaylist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by nina on 12/8/2015.
 */
public class FrontGridAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater layoutInflater;
    private int i=0;

        private Integer[] images = {R.drawable.adam_levine,R.drawable.ariana_grande,R.drawable.beyonce,R.drawable.bruno_mars,
                 R.drawable.carrie_underwood ,R.drawable.celine_dion,R.drawable.demi_lovato,R.drawable.drake,R.drawable.enrique_iglesias,
        R.drawable.jason_derlo ,R.drawable.john_legend,R.drawable.katy_perry,R.drawable.miley_cyrus,R.drawable.rihanna,
                R.drawable.sam_smith,R.drawable.selena_gomez,R.drawable.taylor_swift,R.drawable.the_weeknd};

        private String[] tags  ={"adam_levine","ariana_grande","beyonce","bruno_mars",
                "carrie_underwood" ,"celine_dion","demi_lovato","drake","enrique_iglesias",
                "jason_derlo","john_legend","katy_perry","miley_cyrus","rihanna",
                "sam_smith","selena_gomez","taylor_swift","the_weeknd"};


    public FrontGridAdapter(Context context , LayoutInflater inflater){
        this.context =context;
        this.layoutInflater = inflater;

    }

    @Override
    public int getCount() {
       return images.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       ViewHolder view;

        if(convertView==null)
        {
            view = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.gridview_layout, null);

            view.txtView = (TextView) convertView.findViewById(R.id.textView);
            view.imgView = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }
        view.imgView.setScaleType(ImageView.ScaleType.CENTER);
        view.imgView.setAdjustViewBounds(true);
        view.imgView.setPadding(8, 8, 8, 8);
        view.imgView.setTag(tags[position]);
        view.imgView.setImageResource(images[position]);
        view.txtView.setText(tags[position]);

        return convertView;
    }

    public static class ViewHolder
    {
        public ImageView imgView;
        public TextView txtView;
    }
}
