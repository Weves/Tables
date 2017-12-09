package com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses;

import com.msushanth.tablesapp.DataAccessLayer.ProfileDAO;
import com.msushanth.tablesapp.Interfaces.Profile.ProfileInterface;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Account.LogInForm;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Invitation.ProfileViewer;
import com.msushanth.tablesapp.User;

/**
 * Created by Sushanth on 11/10/17.
 */

public class ProfileController implements ProfileInterface {

    public ProfileController() {}

    ProfileDAO profileDAO = new ProfileDAO();

    // send new users profile information to the database
    @Override
    public void setProfile(User user) {
        profileDAO.setProfile(user);
    }

    // edit existing users profile information in the database
    @Override
    public void editProfile(User user) {
        profileDAO.editProfile(user);
    }

    // get fields from the current user class
    public User getFields() {
        return profileDAO.getFields();
    }

    // set fields in the current user class
    public void setFields() {
        profileDAO.setFields();
    }

    // get a specific users info and set the display elements on the screen with that users info
    public void getUser(String ID, ProfileViewer viewClass) {
        profileDAO.getUser(ID, viewClass);
    }


}
