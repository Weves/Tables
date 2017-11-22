package com.msushanth.tablesapp.PresentationLayer.ActionClasses.Profile;

import com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses.ProfileDispatch;
import com.msushanth.tablesapp.Interfaces.Profile.PersonalProfileInterface;
import com.msushanth.tablesapp.User;

/**
 * Created by Sushanth on 11/9/17.
 */

public class CreatePersonalProfileAction implements PersonalProfileInterface {

    public CreatePersonalProfileAction() {}


    @Override
    public void setProfile(User user) {
        ProfileDispatch setProfileDispatch = new ProfileDispatch();
        setProfileDispatch.setProfile(user);
    }
}
