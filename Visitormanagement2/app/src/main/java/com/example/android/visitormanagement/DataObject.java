package com.example.android.visitormanagement;

/**
 * Created by NAYA on 8/19/2017.
 */

public class DataObject {
    private String mText1;
    private String mText2;
    private String mText3;
    private String mText4;
    TokenSaver ts = new TokenSaver();
    DataObject (String text1, String text2, String text3, String text4){
        mText1 = text1;
        mText2 = text2;
        mText3 = text3;
        mText4 = text4;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }


    public String getmText3() {
        return mText3;
    }

    public void setmText3(String mText3) {
        this.mText3 = mText3;
    }


    public String getmText4() {
        return mText4;
    }

    public void setmText4(String mText4) {
        this.mText4 = mText4;
    }

}
