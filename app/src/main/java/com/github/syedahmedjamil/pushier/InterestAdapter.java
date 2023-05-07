package com.github.syedahmedjamil.pushier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.syedahmedjamil.pushier.R;

import java.util.List;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> {

    List<InterestItem> mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ItemClickListener removeItemClickListener;

    // data is passed into the constructor
    InterestAdapter(Context context, List<InterestItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_interest_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InterestItem interestItem = mData.get(position);
        holder.interestName_editText.setText(interestItem.interestName);
//        byte[] decodedString = Base64.decode(interestItem.icon, Base64.DEFAULT);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        holder.icon_imageView.setImageBitmap(bitmap);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView interestName_editText;
        ImageView removeIcon_imageView;

        ViewHolder(View itemView) {
            super(itemView);
            interestName_editText = itemView.findViewById(R.id.recyclerViewInterestItem_alias_textView);
            removeIcon_imageView = itemView.findViewById(R.id.recyclerViewInterestItem_removeIcon_imageView);

            removeIcon_imageView.setOnClickListener(view -> {
                if (removeItemClickListener != null) removeItemClickListener.onItemClick(view, getAdapterPosition());
            });

            itemView.setOnClickListener(view -> {
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            });
        }
    }

    // convenience method for getting data at click position
    InterestItem getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    void setRemoveItemClickListener(ItemClickListener removeItemClickListener) {
        this.removeItemClickListener = removeItemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}