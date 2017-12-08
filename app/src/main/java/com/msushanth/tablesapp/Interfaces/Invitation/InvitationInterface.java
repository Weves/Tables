package com.msushanth.tablesapp.Interfaces.Invitation;

import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Invitation.ListUser;

import java.util.ArrayList;

/**
 * Created by Sushanth on 11/19/17.
 */

public interface InvitationInterface extends SelectMatchedUsersInterface, SendInvitationInterface, ReceiveInvitationInterface {

    public void sendInvitations(final ArrayList<ListUser> selectedUsers);
    public ArrayList<ListUser> getUsersToShow(ArrayList<String> names, ArrayList<String> tags, ArrayList<String> IDs);

}
