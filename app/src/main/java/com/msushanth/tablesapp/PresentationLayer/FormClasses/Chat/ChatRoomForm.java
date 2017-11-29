package com.msushanth.tablesapp.PresentationLayer.FormClasses.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.msushanth.tablesapp.ChatMessage;
import com.msushanth.tablesapp.R;

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room_layout);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        TextView pageTitle = (TextView) mToolbar.findViewById(R.id.pageTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // add the back button to the toolbar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        chatRoomID = intent.getStringExtra("chatRoomID");
        user1name = intent.getStringExtra("user1name");
        user1id = intent.getStringExtra("user1ID");
        user2name = intent.getStringExtra("user2name");
        user2id = intent.getStringExtra("user2ID");



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

    // implement the back button to the toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
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
