package com.msushanth.tablesapp.PresentationLayer.FormClasses.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

    private FirebaseListAdapter<ChatMessage> adapter;

    EditText messageEditText;
    ImageView submitButton;


    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    String chatRoomID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room_layout);

        Intent intent = getIntent();
        chatRoomID = intent.getStringExtra("chatRoomID");

        // Change the *** in child(***) the the unique ID of the chatroom
        databaseReference = FirebaseDatabase.getInstance().getReference().child(chatRoomID+"CHAT");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        messageEditText = (EditText) findViewById(R.id.messageEditText);
        submitButton = (ImageView) findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.push().setValue(new ChatMessage(messageEditText.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                messageEditText.setText("");
                messageEditText.requestFocus();
            }
        });

        // Display the messages
        displayChatMessage();
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
                TextView messageText, messageUser, messageTime;
                messageText = (TextView) v.findViewById(R.id.message_text);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageTime = (TextView) v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));

            }
        };
        listOfMessage.setAdapter(adapter);
    }


}
