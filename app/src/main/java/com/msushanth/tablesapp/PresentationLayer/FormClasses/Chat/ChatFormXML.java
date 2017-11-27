package com.msushanth.tablesapp.PresentationLayer.FormClasses.Chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.msushanth.tablesapp.R;

/**
 * Created by Sushanth on 10/26/17.
 */

public class ChatFormXML extends android.support.v4.app.Fragment {

    ListView listOfChatsListView;

    String [] chatRoomNames = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight" };
    String [] chatRoomDescriptions = {"OneDesc", "TwoDesc", "ThreeDesc", "FourDesc", "FiveDesc", "SixDesc", "SevenDesc", "EightDesc" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.chat_fragment, container, false);

        listOfChatsListView = (ListView) rootView.findViewById(R.id.ListOfChats);

        CustomAdapter customAdapter = new CustomAdapter();
        listOfChatsListView.setAdapter(customAdapter);



        return rootView;
    }




    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return chatRoomNames.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.chat_item_layout, null);

            TextView chatRoomName = (TextView) convertView.findViewById(R.id.ChatNameTextView);
            TextView chatDescriptionName = (TextView) convertView.findViewById(R.id.ChatDescriptionTextView);

            chatRoomName.setText(chatRoomNames[position]);
            chatDescriptionName.setText(chatRoomDescriptions[position]);

            return convertView;
        }
    }
}
