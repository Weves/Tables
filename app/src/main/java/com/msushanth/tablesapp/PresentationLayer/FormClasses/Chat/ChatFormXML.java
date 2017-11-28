package com.msushanth.tablesapp.PresentationLayer.FormClasses.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Invitation.ProfileViewer;
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
    CustomAdapter customAdapter;


    List<Room> chatRoomsList;
    String currentUsername;

    View rootView;


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

                customAdapter = new CustomAdapter();
                listOfChatsListView.setAdapter(customAdapter);

                listOfChatsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String roomID = chatRoomsList.get(position).getRoomID();

                        Intent chatRoomIntent = new Intent(getActivity(), ChatRoomForm.class);
                        chatRoomIntent.putExtra("chatRoomID", roomID);
                        chatRoomIntent.putExtra("user1name", chatRoomsList.get(position).getUser1Name());
                        chatRoomIntent.putExtra("user1ID", chatRoomsList.get(position).getUser1ID());
                        chatRoomIntent.putExtra("user2name", chatRoomsList.get(position).getUser2Name());
                        chatRoomIntent.putExtra("user2ID", chatRoomsList.get(position).getUser2ID());

                        // Open the chat only if both users have accepted the invitation
                        if(chatRoomsList.get(position).getUser1Accepted().equals("YES") &&
                                chatRoomsList.get(position).getUser2Accepted().equals("YES")) {
                            startActivity(chatRoomIntent);
                        }
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


        View convertViewOut;
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            this.convertViewOut = getLayoutInflater().inflate(R.layout.chat_item_layout, null);

            Room room = chatRoomsList.get(position);

            // Determine which user you are
            boolean isUser1 = false;
            if(room.getUser1Name().equals(currentUsername)) {
                isUser1 = true;
            }

            if(isUser1) {
                // If you are User 1 in the chat room, you have sent the invite, and User 2 has not yet responded to your invitation
                if(room.getUser2Accepted().equals("NOT_YET")) {
                    convertViewOut = getLayoutInflater().inflate(R.layout.chat_item_invite_pending, null);
                    TextView chatRoomName = (TextView) convertViewOut.findViewById(R.id.ChatNameTextView);
                    TextView changeResponse = (TextView) convertViewOut.findViewById(R.id.WaitingForResponseTextView);

                    chatRoomName.setText(chatRoomsList.get(position).getUser2Name());
                    changeResponse.setText("This user has not responded to your request yet.");
                }

                // If you are User 1 in the chat room, you have sent the invite, and User 2 has declined your invitation
                else if(room.getUser2Accepted().equals("NO")) {
                    convertViewOut = getLayoutInflater().inflate(R.layout.chat_item_invite_pending, null);
                    TextView chatRoomName = (TextView) convertViewOut.findViewById(R.id.ChatNameTextView);
                    TextView changeResponse = (TextView) convertViewOut.findViewById(R.id.WaitingForResponseTextView);

                    chatRoomName.setText(chatRoomsList.get(position).getUser2Name());
                    changeResponse.setText("This user has declined your invitation.");
                }

                // If you are User 1 in the chat room, you have sent the invite, and User 2 has accepted your invitation
                else {
                    convertViewOut = getLayoutInflater().inflate(R.layout.chat_item_layout, null);
                    TextView chatRoomName = (TextView) convertViewOut.findViewById(R.id.ChatNameTextView);
                    TextView whereToMeet = (TextView) convertViewOut.findViewById(R.id.WhereToMeetTextView);
                    TextView whenToMeet = (TextView) convertViewOut.findViewById(R.id.WhenToMeetTextView);

                    chatRoomName.setText(chatRoomsList.get(position).getUser2Name());
                    whereToMeet.setText("Where: " + chatRoomsList.get(position).getLocation());
                    whenToMeet.setText("When: " + chatRoomsList.get(position).getLocation());
                }
            }
            else {

                // If you are User 2 in the chat room, you have received the invite, and you have not yet accepted the invitation
                if(room.getUser2Accepted().equals("NOT_YET")) {
                    convertViewOut = getLayoutInflater().inflate(R.layout.chat_accept_declilne_layout, null);
                    TextView chatRoomName = (TextView) convertViewOut.findViewById(R.id.ChatNameTextView);
                    Button viewProfileButton = (Button) convertViewOut.findViewById(R.id.ViewProfileButton);
                    Button acceptButton = (Button) convertViewOut.findViewById(R.id.AcceptButton);
                    Button declineButton = (Button) convertViewOut.findViewById(R.id.DeclineButton);


                    viewProfileButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Room room = chatRoomsList.get(position);
                            Intent intent = new Intent(getContext(), ProfileViewer.class);
                            intent.putExtra("matchedUsersID", room.getUser1ID());
                            startActivity(intent);
                        }
                    });

                    // TODO Refesh the fragment after accepting the invitation.
                    acceptButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "Invite Accepted", Toast.LENGTH_SHORT).show();
                            Room room = chatRoomsList.get(position);
                            room.setUser2Accepted("YES");
                            DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
                            dbReference.child(chatRoomsList.get(position).getRoomID()).setValue(room);

                            // Update the listview with the changes
                            customAdapter.notifyDataSetChanged();
                        }
                    });

                    // TODO Refesh the fragment after declining the invitation.
                    declineButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "Invite Declined", Toast.LENGTH_SHORT).show();
                            Room room = chatRoomsList.get(position);
                            room.setUser2Accepted("NO");
                            DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
                            dbReference.child(chatRoomsList.get(position).getRoomID()).setValue(room);

                            // Update the listview with the changes
                            customAdapter.notifyDataSetChanged();
                        }
                    });

                    chatRoomName.setText(chatRoomsList.get(position).getUser1Name());
                }

                // If you are User 2 in the chat room, you have received the invite, and you have declined the invitation
                else if(room.getUser2Accepted().equals("NO")) {
                    convertViewOut = getLayoutInflater().inflate(R.layout.chat_item_invite_pending, null);
                    TextView chatRoomName = (TextView) convertViewOut.findViewById(R.id.ChatNameTextView);
                    TextView changeResponse = (TextView) convertViewOut.findViewById(R.id.WaitingForResponseTextView);

                    chatRoomName.setText(chatRoomsList.get(position).getUser1Name());
                    changeResponse.setText("You have declined this invitation.");
                }

                // If you are User 2 in the chat room, you have received the invite, and you have accepted the invitation
                else {
                    convertViewOut = getLayoutInflater().inflate(R.layout.chat_item_layout, null);
                    TextView chatRoomName = (TextView) convertViewOut.findViewById(R.id.ChatNameTextView);
                    TextView whereToMeet = (TextView) convertViewOut.findViewById(R.id.WhereToMeetTextView);
                    TextView whenToMeet = (TextView) convertViewOut.findViewById(R.id.WhenToMeetTextView);

                    chatRoomName.setText(chatRoomsList.get(position).getUser1Name());
                    whereToMeet.setText("Where: " + chatRoomsList.get(position).getLocation());
                    whenToMeet.setText("When: " + chatRoomsList.get(position).getLocation());
                }
            }



            return convertViewOut;
        }
    }
}
