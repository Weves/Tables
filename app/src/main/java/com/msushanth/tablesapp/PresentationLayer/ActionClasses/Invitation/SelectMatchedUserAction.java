package com.msushanth.tablesapp.PresentationLayer.ActionClasses.Invitation;

import com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses.InvitationDispatch;
import com.msushanth.tablesapp.Interfaces.Invitation.SelectMatchedUsersInterface;

/**
 * Created by Sushanth on 11/9/17.
 */

public class SelectMatchedUserAction implements SelectMatchedUsersInterface {

    InvitationDispatch dispatch = new InvitationDispatch();

    public void viewProfile() {
        dispatch.viewProfile();
    }

}
