package edu.bennett.tachyons.vms;

/**
 * Created by veeki on 8/19/2017.
 */

public class DataObject {
    private String mid;
    private String mname;
    private String mmobile;
    private String mvisitee_id;
    private String mimageuri;

    // Class constructor
    DataObject(String id, String name, String mobile, String visitee_id, String imageuri) {
        mid = id;
        mname = name;
        mmobile = mobile;
        mvisitee_id = visitee_id;
        mimageuri = imageuri;
    }

    public String getid() {
        return mid;
    }

    public String getname() {
        return mname;
    }

    public String getmobile() {
        return mmobile;
    }

    public String getvisiteeid() {
        return mvisitee_id;
    }

    public String getmimageuri() {
        return mimageuri;
    }

}
