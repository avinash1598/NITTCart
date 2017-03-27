package com.example.avinash.nittcart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by AVINASH on 3/25/2017.
 */
public class RecyclerFoldingCell extends RecyclerView.Adapter<RecyclerFoldingCell.ViewHolder> {
    private List<Item> ItemList=Collections.emptyList();
    Context mContext;
    Item item;
    private Typeface mTf;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_CELL = 1;
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    int lastPosition = -1;
    static Animation animation,animation2;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        FoldingCell fc;
        TextView price;
        TextView contentRequestBtn;
        TextView pledgePrice;
        TextView fromAddress;
        TextView toAddress;
        TextView requestsCount;
        TextView date;
        TextView time;

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

                       //cv = (CardView) itemView.findViewById(R.id.card_view);
                        fc = (FoldingCell)itemView.findViewById(R.id.folding_cell);
                       fc.initialize(1000, Color.WHITE, 2);
                       fc.setTag(cv);

                       fc.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               fc.toggle(false);
                           }
                       });

               }
    }

    public RecyclerFoldingCell(List<Item> ItemList , Context mContext,Animation animation, Animation animation2) {
        this.ItemList = ItemList;
        this.mContext = mContext;
        this.animation = animation;
        this.animation2 = animation2;
    }


    @Override
    public RecyclerFoldingCell.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {

            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cell, parent, false);
                ViewHolder holder = new ViewHolder(view);
                return new ViewHolder(view) ;//{
            }
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cell, parent, false);
                ViewHolder holder = new ViewHolder(view);
                return new ViewHolder(view) ;//{
            }
        }
        return null;
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

    @Override
    public void onBindViewHolder(RecyclerFoldingCell.ViewHolder viewHolder, int position) {
        viewHolder.fc.fold(true);

                if (!ItemList.isEmpty()) {
                    Log.d("price at pos", "" + ItemList.get(position).getPrice());
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
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
            return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
        // return (dataCursor == null) ? 0 : dataCursor.getCount();
    }

    public List<Item> getContents() {
        return ItemList;
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    }
