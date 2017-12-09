package com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses;

import com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses.ProfileController;
import com.msushanth.tablesapp.Interfaces.Profile.ProfileInterface;
import com.msushanth.tablesapp.User;

/**
 * Created by Sushanth on 11/10/17.
 */

public class ProfileDispatch implements ProfileInterface {

    public ProfileDispatch() {}


    @Override
    public void setProfile(User user) {
        ProfileController setProfileManager = new ProfileController();
        setProfileManager.setProfile(user);
    }


    @Override
    public void editProfile(User user) {
        ProfileController editProfileManager = new ProfileController();
        editProfileManager.editProfile(user);
    }
}
