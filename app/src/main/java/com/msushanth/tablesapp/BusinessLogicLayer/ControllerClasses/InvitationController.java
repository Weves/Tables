package com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses;

import com.msushanth.tablesapp.DataAccessLayer.InvitationDAO;
import com.msushanth.tablesapp.Interfaces.Invitation.InvitationInterface;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Invitation.ListUser;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Invitation.SelectMatchedUsersView;

import java.util.ArrayList;

/**
 * Created by Sushanth on 11/10/17.
 */

public class InvitationController implements InvitationInterface{

    InvitationDAO invitationDAO = new InvitationDAO();

    // pass down the fields
    public ArrayList<ListUser> getUsersToShow(ArrayList<String> names, ArrayList<String> tags, ArrayList<String> IDs) {
        return invitationDAO.getUsersToShow(names, tags, IDs);
    }


    // pass down the arraylist
    public void sendInvitations(final ArrayList<ListUser> selectedUsers) {
        invitationDAO.sendInvitations(selectedUsers);
    }

    // called from DAO class to set RecyclerView adapter
    public static void setAdapter(ArrayList<ListUser> users) {
        SelectMatchedUsersView.setAdapter(users);
    }

}
