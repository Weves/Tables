package com.msushanth.tablesapp.PresentationLayer.ActionClasses.Profile;

import com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses.ProfileDispatch;
import com.msushanth.tablesapp.Interfaces.Profile.EditPersonalProfileInterface;
import com.msushanth.tablesapp.User;

/**
 * Created by Sushanth on 11/9/17.
 */

public class EditPersonalProfileAction implements EditPersonalProfileInterface {

    public EditPersonalProfileAction() {}

    @Override
    public void editProfile(User user) {
        ProfileDispatch editProfileDispatch = new ProfileDispatch();
        editProfileDispatch.editProfile(user);
    }
}
