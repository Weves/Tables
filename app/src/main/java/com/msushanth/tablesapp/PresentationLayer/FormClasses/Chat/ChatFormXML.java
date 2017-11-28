package com.msushanth.tablesapp.PresentationLayer.FormClasses.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Account.LogInForm;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Account.PasswordRecoveryForm;
import com.msushanth.tablesapp.R;
import com.msushanth.tablesapp.Room;
import com.msushanth.tablesapp.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sushanth on 10/26/17.
 */

public class ChatFormXML extends android.support.v4.app.Fragment {

    ListView listOfChatsListView;
    TextView addUsersTextView;

    List<Room> chatRoomsList;
    String currentUsername;

    View rootView;

    String [] chatRoomNames = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight" };
    String [] chatRoomDescriptions = {"OneDesc", "TwoDesc", "ThreeDesc", "FourDesc", "FiveDesc", "SixDesc", "SevenDesc", "EightDesc" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.chat_fragment, container, false);

        chatRoomsList = new ArrayList<Room>();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser fireBaseUser = firebaseAuth.getCurrentUser();
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This is the current users profile
                User currentUserProfile = dataSnapshot.child(fireBaseUser.getUid()).getValue(User.class);
                currentUsername = currentUserProfile.getFirst_name() + " " + currentUserProfile.getLast_name();
                List<String> chatListIDs = currentUserProfile.getRoom_ids();

                for(int i=0; i<chatListIDs.size(); i++) {
                    System.out.println("****" + chatListIDs.get(i));
                    Room room = dataSnapshot.child(chatListIDs.get(i)).getValue(Room.class);
                    chatRoomsList.add(room);
                }

                listOfChatsListView = (ListView) rootView.findViewById(R.id.ListOfChats);
                addUsersTextView = (TextView) rootView.findViewById(R.id.AddUsersTextView);


                // If there are no chats the user is in , then display that there are no chats and he should search for other users
                // If there are chats, then display the chats
                if(chatRoomsList.size() == 0) {
                    addUsersTextView.setVisibility(View.VISIBLE);
                } else {
                    listOfChatsListView.setVisibility(View.VISIBLE);
                }

                CustomAdapter customAdapter = new CustomAdapter();
                listOfChatsListView.setAdapter(customAdapter);

                listOfChatsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String roomID = chatRoomsList.get(position).getRoomID();
                        Toast.makeText(getActivity(), roomID, Toast.LENGTH_SHORT).show();

                        Intent chatRoomIntent = new Intent(getActivity(), ChatRoomForm.class);
                        chatRoomIntent.putExtra("chatRoomID", roomID);
                        startActivity(chatRoomIntent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });







        return rootView;
    }




    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return chatRoomsList.size();
            //return chatRoomNames.length;
        }

        @Override
        public Object getItem(int position) {
            return chatRoomsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.chat_item_layout, null);
            TextView chatRoomName = (TextView) convertView.findViewById(R.id.ChatNameTextView);
            TextView chatDescriptionName = (TextView) convertView.findViewById(R.id.ChatDescriptionTextView);


            // Display the chat room id and the other users first and last name
            chatDescriptionName.setText(chatRoomsList.get(position).getRoomID());
            if(!chatRoomsList.get(position).getUser2Name().equals(currentUsername)) {
                chatRoomName.setText(chatRoomsList.get(position).getUser2Name());

            }
            else {
                chatRoomName.setText(chatRoomsList.get(position).getUser1Name());
            }

            return convertView;
        }
    }
}
