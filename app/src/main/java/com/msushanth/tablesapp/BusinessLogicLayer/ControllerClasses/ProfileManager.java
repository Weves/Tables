package com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses;

import com.msushanth.tablesapp.DataAccessLayer.ProfileDAO;
import com.msushanth.tablesapp.Interfaces.Profile.ProfileInterface;
import com.msushanth.tablesapp.User;

/**
 * Created by Sushanth on 11/10/17.
 */

public class ProfileManager implements ProfileInterface {

    public ProfileManager() {}


    // TODO: Check if user has the credentials to set the information in the database
    @Override
    public void setProfile(User user) {
        ProfileDAO setProfileDAO = new ProfileDAO(user);
        setProfileDAO.setProfile(user);
    }


    @Override
    public void editProfile(User user) {
        ProfileDAO editProfileDAO = new ProfileDAO(user);
        editProfileDAO.editProfile(user);
    }
}
