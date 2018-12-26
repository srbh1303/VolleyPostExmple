package com.example.Agriculture.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.Agriculture.R;
import com.example.Agriculture.model.State;
import com.example.Agriculture.model.User;

import java.util.List;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<User> listUsers;
    private List<State> listState;
    Boolean flag;

    public UsersRecyclerAdapter(List<User> listUsers, Boolean flag, List<State> listStates) {
        this.listUsers = listUsers;
        this.flag = flag;
        this.listState = listStates;
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
        holder.linearStateNm.setVisibility(View.GONE);

        if (flag) {
            holder.textViewCropId.setText(listState.get(position).getStateId());
            holder.textViewCrop.setText(listState.get(position).getStateName());
            holder.linearCropVareity.setVisibility(View.GONE);
            holder.linearStateId.setVisibility(View.GONE);
        } else {
            holder.textViewCropId.setText(listUsers.get(position).getCropId());
            holder.textViewCrop.setText(listUsers.get(position).getCrop());
            holder.textViewCropVariety.setText(listUsers.get(position).getCropVariety());
            holder.textViewCropStateId.setText(listUsers.get(position).getCropStateId());
        }
    }

    @Override
    public int getItemCount() {
        if (flag) {
            return listState.size();
        } else
            return listUsers.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewCropId;
        public AppCompatTextView textViewCrop;
        public AppCompatTextView textViewCropVariety;
        public AppCompatTextView textViewCropStateId;
        public LinearLayout linearStateNm, linearCropVareity, linearStateId;

        public UserViewHolder(View view) {
            super(view);
            textViewCropId = (AppCompatTextView) view.findViewById(R.id.textCropId);
            textViewCrop = (AppCompatTextView) view.findViewById(R.id.textCrop);
            textViewCropVariety = (AppCompatTextView) view.findViewById(R.id.textCropVariety);
            textViewCropStateId = (AppCompatTextView) view.findViewById(R.id.textCropStateId);
            linearStateNm = (LinearLayout) view.findViewById(R.id.state_nm);
            linearCropVareity = (LinearLayout) view.findViewById(R.id.linear_crop_variety);
            linearStateId = (LinearLayout) view.findViewById(R.id.linear_state_id);
        }
    }
}
