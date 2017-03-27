package com.example.avinash.nittcart;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AVINASH on 3/14/2017.
 */
public class FoldingCellActivity extends MainActivity {

    public static GridFoldingCellAdapter mAdapter;
    public FoldingCellActivity(RecyclerView recyclerView, RecyclerView.LayoutManager mLayoutManager,
                               Context mContext , GridLayoutManager lLayout) {
             // get our list view
        //ListView theListView = (ListView) findViewById(R.id.mainListView);
        //theListView.setNestedScrollingEnabled(true);
        // prepare elements to display
        //final ArrayList<Item> items = Item.getTestingList();

        Log.d("size list", "" + movieList.size());
        movieList = Item.getTestingList();

        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        //grid
        Animation animation = AnimationUtils.loadAnimation(mContext,
                R.anim.up_from_bottom);
        Animation animation2 = AnimationUtils.loadAnimation(mContext,
                R.anim.left_recycler);
        GridFoldingCellAdapter gadapter= new GridFoldingCellAdapter(movieList,mContext,animation,animation2);
        mAdapter=gadapter;
        if(recyclerView!=null) {
            recyclerView.setLayoutManager(lLayout);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(gadapter);
            recyclerView.setNestedScrollingEnabled(false);

            //list
       /* Animation animation = AnimationUtils.loadAnimation(mContext,
                      R.anim.up_from_bottom);
        Animation animation2 = AnimationUtils.loadAnimation(mContext,
                R.anim.left_recycler);
                mAdapter = new RecyclerFoldingCell(movieList, this,animation, animation2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
*/
            //movieList = Item.getTestingList();
            //movieList.add(new Item("$23", "$116", "W 36th St, NY, 10015", "W 114th St, NY, 10037", 10, "TODAY", "11:10 AM"));
            //mAdapter.notifyDataSetChanged();
            //movieList.add(new Item("$23", "$116", "W 36th St, NY, 10015", "W 114th St, NY, 10037", 10, "TODAY", "11:10 AM"));
            //mAdapter.notifyDataSetChanged();

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(mContext, recyclerView, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Item movie = movieList.get(position);
                    //Toast.makeText(getApplicationContext(), position + " is selected!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

        }
        // add custom btn handler to first list item
        /*items.get(0).setRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
            }
        });

        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, items);

        // add default btn handler for each request btn on each item if custom handler not found
        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
            }
        });

        // set elements to adapter
        //theListView.setAdapter(adapter);

        // set on click event listener to list view
        /*theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });
*/
    }
}