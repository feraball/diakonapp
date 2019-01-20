package com.diakonia.diakonapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diakonia.diakonapp.R;
import com.diakonia.diakonapp.models.Reward;

import java.util.List;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.MyViewHolder>{

    private Context mContext;
    private static List<Reward> mData;
    private OnCardListener mOnCardListener;


    //CONSTRUCTOR
    public RewardsAdapter(Context mContext, List<Reward> mData, OnCardListener onCardListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mOnCardListener = onCardListener;
    }

    public RewardsAdapter() {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_rewards_item, viewGroup, false);

        return new MyViewHolder(view, mOnCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(mData.get(i).getTitle());
        myViewHolder.location.setText(mData.get(i).getLocation());
        myViewHolder.expDate.setText(mData.get(i).getExpirationDate());
        myViewHolder.points.setText(String.valueOf(mData.get(i).getPoints()));

        Glide.with(mContext)
                .load(mData.get(i).getUrlPhoto())
                .into(myViewHolder.photo);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, location, expDate, points;
        ImageView photo;
        OnCardListener onCardListener;

        public MyViewHolder(@NonNull View itemView, OnCardListener onCardListener) {
            super(itemView);

            title       = itemView.findViewById(R.id.textView_rewardcard_title);
            location    = itemView.findViewById(R.id.textView_rewardcard_location);
            expDate     = itemView.findViewById(R.id.textView_rewardcard_expdate);
            points      = itemView.findViewById(R.id.textView_rewardcard_points);
            photo       = itemView.findViewById(R.id.imageView_rewardcard_photo);

            this.onCardListener = onCardListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCardListener.onCardClick(getAdapterPosition());
        }
    }

    public interface OnCardListener{
        void onCardClick(int position);
    }
}
