package com.example.avinash.nittcart;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by AVINASH on 1/30/2017.
 */
public class itemtype extends BaseAdapter {

    private Context mContext;
    private final String[] tag;
    private final int[] Imageid;
    View rootView;

    public itemtype(Context c, String[] tag, int[] Imageid) {
        mContext = c;
        rootView = ((Activity)mContext).getWindow().getDecorView().findViewById(android.R.id.content);
        this.Imageid = Imageid;
        this.tag = tag;
    }
    @Override
    public int getCount() {
        return tag.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.customgrid, null);
            TextView textView = (TextView) grid.findViewById(R.id.customtext);
            ImageButton imageView = (ImageButton)grid.findViewById(R.id.customimage);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("click", "" + i);

                }
            });

            int[] androidColors = rootView.getResources().getIntArray(R.array.androidcolors);
            int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
             grid.setBackgroundColor(randomAndroidColor);
            //Typeface type = Typeface.createFromAsset(getAssets(), "font/comic_sans.ttf");
            textView.setText(tag[i]);
            imageView.setImageResource(Imageid[i]);
        } else {
            grid = (View) view;
        }

        return grid;
    }
}
