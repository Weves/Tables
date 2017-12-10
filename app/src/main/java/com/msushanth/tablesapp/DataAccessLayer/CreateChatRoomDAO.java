package com.msushanth.tablesapp.DataAccessLayer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.msushanth.tablesapp.Objects.Room;

/**
 * Created by khoabui on 11/26/17.
 */

public class CreateChatRoomDAO {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private Room room;

    public CreateChatRoomDAO(Room room) {
        this.room = room;

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }


}
