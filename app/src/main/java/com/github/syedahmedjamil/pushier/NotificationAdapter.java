package com.github.syedahmedjamil.pushier;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.syedahmedjamil.pushier.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    List<NotificationItem> mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ItemClickListener removeItemClickListener;
    private SharedPreferences localSP;

    // data is passed into the constructor
    NotificationAdapter(Context context, List<NotificationItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        localSP = context.getSharedPreferences("local",Context.MODE_PRIVATE);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotificationItem notificationItem = mData.get(position);
        holder.title.setText(notificationItem.title);
        holder.date.setText(notificationItem.date);
        String encodedImage = localSP.getString(notificationItem.interest + "-imageEncoded", null);
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.icon.setImageBitmap(bitmap);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView date;
        ImageView icon;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.job_title);
            date = itemView.findViewById(R.id.job_date);
            icon = itemView.findViewById(R.id.job_icon);
            itemView.setOnClickListener(view -> {
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            });
        }
    }

    // convenience method for getting data at click position
    NotificationItem getItem(int id) {
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