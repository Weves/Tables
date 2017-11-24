package com.msushanth.tablesapp.PresentationLayer.FormClasses.Invitation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.msushanth.tablesapp.R;

import java.util.List;

/**
 * Created by chris on 11/22/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListUser> listUsers;
    private Context context;

    public ListAdapter(List<ListUser> listUsers, Context context) {
        this.listUsers = listUsers;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_users_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListUser listUser = listUsers.get(position);

        holder.userNameItem.setText(listUser.getName());
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView userNameItem;

        public ViewHolder(View itemView) {
            super(itemView);

            userNameItem = (TextView) itemView.findViewById(R.id.UserNameItem);
        }
    }
}
