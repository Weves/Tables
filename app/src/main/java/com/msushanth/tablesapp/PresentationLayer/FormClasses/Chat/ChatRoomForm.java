package com.msushanth.tablesapp.PresentationLayer.FormClasses.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.msushanth.tablesapp.ChatMessage;
import com.msushanth.tablesapp.MainActivity;
import com.msushanth.tablesapp.R;
import com.msushanth.tablesapp.Room;

/**
 * Created by Sushanth on 11/27/17.
 */

public class ChatRoomForm extends AppCompatActivity {

    private Toolbar mToolbar;

    private FirebaseListAdapter<ChatMessage> adapter;

    EditText messageEditText;
    ImageView submitButton;


    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    String currentUsersEmail;


    String chatRoomID;
    String user1name;
    String user1id;
    String user2name;
    String user2id;

    String time;
    String date;
    String location;


    boolean user1SentInvite;
    boolean user2SentInvite;
    String user1Accepted;
    String user2Accepted;

    Room thisChatRoom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room_layout);

        Intent intent = getIntent();
        chatRoomID = intent.getStringExtra("chatRoomID");
        user1name = intent.getStringExtra("user1name");
        user1id = intent.getStringExtra("user1ID");
        user2name = intent.getStringExtra("user2name");
        user2id = intent.getStringExtra("user2ID");
        time = intent.getStringExtra("time");
        date = intent.getStringExtra("date");
        location = intent.getStringExtra("location");
        user1SentInvite = intent.getBooleanExtra("user1SentInvite", true);
        user2SentInvite = intent.getBooleanExtra("user2SentInvite", false);
        user1Accepted = intent.getStringExtra("user1Accepted");
        user2Accepted = intent.getStringExtra("user2Accepted");

        thisChatRoom = new Room();
        thisChatRoom.setRoomID(chatRoomID);
        thisChatRoom.setUser1Name(user1name);
        thisChatRoom.setUser1ID(user1id);
        thisChatRoom.setUser2Name(user2name);
        thisChatRoom.setUser2ID(user2id);
        thisChatRoom.setTime(time);
        thisChatRoom.setDate(date);
        thisChatRoom.setLocation(location);
        thisChatRoom.setUser1SentInvite(user1SentInvite);
        thisChatRoom.setUser2SentInvite(user2SentInvite);
        thisChatRoom.setUser1Accepted(user1Accepted);
        thisChatRoom.setUser2Accepted(user2Accepted);


        mToolbar = (Toolbar) findViewById(R.id.navigationActionbar);
        TextView pageTitle = (TextView) mToolbar.findViewById(R.id.pageTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // add the back button to the toolbar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().setLogo(R.mipmap.ic_toolbar_more_white);
            //getSupportActionBar().setDisplayUseLogoEnabled(true);
        }



        // Change the *** in child(***) the the unique ID of the chatroom
        databaseReference = FirebaseDatabase.getInstance().getReference().child(chatRoomID+"CHAT");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        currentUsersEmail = firebaseUser.getEmail();

        // Set the chat title to the other users name
        //TextView otherUsersNameTextView = findViewById(R.id.otherUsersNameTextView);
        if(firebaseAuth.getUid().equals(user1id)) {
            pageTitle.setText(user2name);
        }
        else {
            pageTitle.setText(user1name);
        }

        messageEditText = (EditText) findViewById(R.id.messageEditText);
        submitButton = (ImageView) findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!messageEditText.getText().toString().equals("")) {
                    databaseReference.push().setValue(new ChatMessage(messageEditText.getText().toString(),
                            FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                    messageEditText.setText("");
                    messageEditText.requestFocus();
                }
            }
        });

        // Display the messages
        displayChatMessage();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =  getMenuInflater();
        menuInflater.inflate(R.menu.chat_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // implement the back button to the toolbar
    // implement the options in the menu on the toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_set_time_location_chat:
                //Toast.makeText(getApplicationContext(), "Set Time and Location Selected", Toast.LENGTH_SHORT).show();
                Intent setWhenAndWhereToMeetIntent = new Intent(this, SetWhenAndWhereToMeetForm.class);

                // Pass in the entire chat
                setWhenAndWhereToMeetIntent.putExtra("chatRoomID", chatRoomID);
                setWhenAndWhereToMeetIntent.putExtra("user1name", user1name);
                setWhenAndWhereToMeetIntent.putExtra("user1ID", user1id);
                setWhenAndWhereToMeetIntent.putExtra("user2name", user2name);
                setWhenAndWhereToMeetIntent.putExtra("user2ID", user2id);
                setWhenAndWhereToMeetIntent.putExtra("time", time);
                setWhenAndWhereToMeetIntent.putExtra("date", date);
                setWhenAndWhereToMeetIntent.putExtra("location", location);
                setWhenAndWhereToMeetIntent.putExtra("user1SentInvite", user1SentInvite);
                setWhenAndWhereToMeetIntent.putExtra("user2SentInvite", user2SentInvite);
                setWhenAndWhereToMeetIntent.putExtra("user1Accepted", user1Accepted);
                setWhenAndWhereToMeetIntent.putExtra("user2Accepted", user2Accepted);


                startActivity(setWhenAndWhereToMeetIntent);
                break;
            case R.id.menu_leave_chat:
                Toast.makeText(getApplicationContext(), "Leaving Chat", Toast.LENGTH_SHORT).show();
                // Set the chat title to the other users name
                //TextView otherUsersNameTextView = findViewById(R.id.otherUsersNameTextView);
                Intent leaveChatIntent = new Intent(this, MainActivity.class);
                if(firebaseAuth.getUid().equals(user1id)) {
                    thisChatRoom.setUser1Accepted("LEFT");
                    databaseReference.getParent().child(thisChatRoom.getRoomID()).setValue(thisChatRoom);
                    startActivity(leaveChatIntent);
                }
                else {
                    thisChatRoom.setUser2Accepted("LEFT");
                    databaseReference.getParent().child(thisChatRoom.getRoomID()).setValue(thisChatRoom);
                    startActivity(leaveChatIntent);
                }
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayChatMessage() {
        ListView listOfMessage = (ListView) findViewById(R.id.messagesListView);
        adapter = new FirebaseListAdapter<ChatMessage>(
                this,
                ChatMessage.class,
                R.layout.chat_message_item,
                databaseReference
        ) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {

                //Get references to the views of list_item.xml
                RelativeLayout user1Layout, user2Layout;
                TextView message_text1, message_text2;
                //ImageView imageView1, imageView2;

                user1Layout = (RelativeLayout) v.findViewById(R.id.User1Layout);
                user2Layout = (RelativeLayout) v.findViewById(R.id.User2Layout);

                //imageView1 = (ImageView) v.findViewById(R.id.imageView1);
                //imageView2 = (ImageView) v.findViewById(R.id.imageView2);

                message_text1 = (TextView) v.findViewById(R.id.message_text1);
                message_text2 = (TextView) v.findViewById(R.id.message_text2);

                if(model.getMessageUser().equals(currentUsersEmail)) {
                    user1Layout.setVisibility(View.VISIBLE);
                    message_text1.setText(model.getMessageText());
                    user2Layout.setVisibility(View.GONE);
                }
                else {
                    user2Layout.setVisibility(View.VISIBLE);
                    message_text2.setText(model.getMessageText());
                    user1Layout.setVisibility(View.GONE);
                }

            }
        };
        listOfMessage.setAdapter(adapter);
    }


    /*
    public void backButtonClicked(View view) {
        finish();
    }
    */
}
