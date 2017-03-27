package com.example.avinash.nittcart;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.Collections;
import java.util.List;

/**
 * Created by AVINASH on 3/25/2017.
 */
public class GridFoldingCellAdapter extends RecyclerView.Adapter<GridFoldingCellAdapter.ViewHolder> {

    private List<Item> ItemList= Collections.emptyList();
    Context mContext;
    int lastPosition = -1;
    static Animation animation,animation2;

    public GridFoldingCellAdapter(List<Item> ItemList , Context mContext ,Animation animation, Animation animation2) {
        this.ItemList = ItemList;
        this.mContext = mContext;
        this.animation = animation;
        this.animation2 = animation2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(!ItemList.isEmpty()) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cell, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.fc.fold(true);
        if (!ItemList.isEmpty()) {
            //Log.d("price at pos", "" + ItemList.get(position).getPrice());
            viewHolder.price.setText(ItemList.get(position).getPrice());
            viewHolder.time.setText(ItemList.get(position).getTime());
            viewHolder.date.setText(ItemList.get(position).getDate());
            viewHolder.fromAddress.setText(ItemList.get(position).getFromAddress());
            viewHolder.toAddress.setText(ItemList.get(position).getToAddress());
            viewHolder.requestsCount.setText(String.valueOf(ItemList.get(position).getRequestsCount()));
            viewHolder.pledgePrice.setText(ItemList.get(position).getPledgePrice());
        }
        if(position >lastPosition) {

            //Animation animation = AnimationUtils.loadAnimation(mContext,
            //      R.anim.up_from_bottom);
            if(position%2==0)
                viewHolder.itemView.startAnimation(animation);
            else
                viewHolder.itemView.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public void addItemAtIndex(int index, Item item){
        ItemList.add(index, item);
        notifyDataSetChanged();
    }

    public void notifyDataArray(List<Item> item){
        Log.d("notifyData ", item.size() + "");
        this.ItemList = item;
        notifyDataSetChanged();
    }

    public void insert(Item item, int position) {
        ItemList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Item item) {
        int position = ItemList.indexOf(item);
        ItemList.remove(position);
        notifyItemRemoved(position);
    }
    public void addItem(Item item) {
        ItemList.add(item);
        notifyDataSetChanged();
    }

    public void removeAll() {
        ItemList.clear();
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView price;
        TextView contentRequestBtn;
        TextView pledgePrice;
        TextView fromAddress;
        TextView toAddress;
        TextView requestsCount;
        TextView date;
        TextView time;
        FoldingCell fc;

        TextView titlePrice;
        TextView titleDate;
        TextView titleTime;
        public ViewHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.title_price);
            time = (TextView) itemView.findViewById(R.id.title_time_label);
            date = (TextView) itemView.findViewById(R.id.title_date_label);
            fromAddress = (TextView) itemView.findViewById(R.id.title_from_address);
            toAddress = (TextView) itemView.findViewById(R.id.title_to_address);
            requestsCount = (TextView) itemView.findViewById(R.id.title_requests_count);
            pledgePrice = (TextView) itemView.findViewById(R.id.title_pledge);
            contentRequestBtn = (TextView) itemView.findViewById(R.id.content_request_btn);

            fc = (FoldingCell)itemView.findViewById(R.id.folding_cell);
            fc.initialize(1000, Color.WHITE, 2);
            //fc.setTag(cv);

            fc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fc.toggle(false);
                }
            });
        }
    }
}
