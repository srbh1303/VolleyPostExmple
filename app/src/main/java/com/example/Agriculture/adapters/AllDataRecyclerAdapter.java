package com.example.Agriculture.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Agriculture.R;
import com.example.Agriculture.model.State;
import com.example.Agriculture.model.User;

import java.util.List;

public class AllDataRecyclerAdapter extends RecyclerView.Adapter<AllDataRecyclerAdapter.UserViewHolder> {

    private List<User> listUsers;

    public AllDataRecyclerAdapter(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_recycler, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

        holder.textViewCropId.setText(listUsers.get(position).getCropId());
        holder.textViewCrop.setText(listUsers.get(position).getCrop());
        holder.textViewCropVariety.setText(listUsers.get(position).getCropVariety());
        holder.textViewCropStateId.setText(listUsers.get(position).getCropStateId());
        holder.textViewCropStateName.setText(listUsers.get(position).getCropJoinStateName());
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }


    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewCropId;
        public AppCompatTextView textViewCrop;
        public AppCompatTextView textViewCropVariety;
        public AppCompatTextView textViewCropStateId;
        public AppCompatTextView textViewCropStateName;

        public UserViewHolder(View view) {
            super(view);
            textViewCropId = (AppCompatTextView) view.findViewById(R.id.textCropId);
            textViewCrop = (AppCompatTextView) view.findViewById(R.id.textCrop);
            textViewCropVariety = (AppCompatTextView) view.findViewById(R.id.textCropVariety);
            textViewCropStateId = (AppCompatTextView) view.findViewById(R.id.textCropStateId);
            textViewCropStateName = (AppCompatTextView) view.findViewById(R.id.textCropStateName);
        }
    }
}
