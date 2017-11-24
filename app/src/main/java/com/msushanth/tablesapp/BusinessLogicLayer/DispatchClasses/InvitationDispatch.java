package com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses;

import com.msushanth.tablesapp.BusinessLogicLayer.ManagerClasses.InvitationManager;
import com.msushanth.tablesapp.Interfaces.Invitation.InvitationInterface;

/**
 * Created by Sushanth on 11/10/17.
 */

public class InvitationDispatch implements InvitationInterface{

    InvitationManager manager = new InvitationManager();

    public void viewProfile() {
        manager.viewProfile();
    }

}
