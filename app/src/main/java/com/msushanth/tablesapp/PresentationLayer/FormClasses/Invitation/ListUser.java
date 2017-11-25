package com.msushanth.tablesapp.PresentationLayer.FormClasses.Invitation;


/**
 * Created by chris on 11/22/2017.
 */

public class ListUser {



    private String name;
    private String tags;
    private String ID;
    private Boolean selected;

    public ListUser(String name, String tags, String ID) {
        this.name = name;
        this.tags = tags;
        this.ID = ID;
        selected = false;
    }

    public String getName() {
        return name;
    }


    public String getTags() {
        return tags;
    }

    public String getID() {
        return ID;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
