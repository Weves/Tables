package com.msushanth.tablesapp.PresentationLayer.FormClasses.Chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msushanth.tablesapp.R;

/**
 * Created by Sushanth on 10/26/17.
 */

public class ChatFormXML extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.chat_fragment, container, false);
        return rootView;
    }
}
