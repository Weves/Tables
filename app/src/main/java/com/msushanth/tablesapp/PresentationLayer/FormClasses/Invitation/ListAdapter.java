package com.msushanth.tablesapp.PresentationLayer.FormClasses.Invitation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msushanth.tablesapp.R;

import java.util.List;

/**
 * Created by chris on 11/22/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListUser> listUsers;
    private Context context;
    private View parent;

    public ListAdapter(List<ListUser> listUsers, Context context) {
        this.listUsers = listUsers;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_users_item, parent, false);
        this.parent = v;
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListUser listUser = listUsers.get(position);

        holder.userNameItem.setText(listUser.getName());
        holder.tagsItem.setText(listUser.getTags());

        holder.usersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You clicked " + listUser.getName() + "'s profile!", Toast.LENGTH_LONG).show();
                Intent selectMatchedUsersIntent = new Intent(context, ProfileViewer.class);
                selectMatchedUsersIntent.putExtra("matchedUsersID", listUser.getID());
                context.startActivity(selectMatchedUsersIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView profilePicture;
        public TextView userNameItem;
        public TextView tagsItem;
        public LinearLayout usersList;

        public ViewHolder(View itemView) {
            super(itemView);

            userNameItem = (TextView) itemView.findViewById(R.id.UserNameItem);
            tagsItem = (TextView) itemView.findViewById(R.id.TagsItem);
            usersList = (LinearLayout) itemView.findViewById(R.id.usersList);
            profilePicture = (ImageView) itemView.findViewById(R.id.profilePicture);
        }
    }
}
