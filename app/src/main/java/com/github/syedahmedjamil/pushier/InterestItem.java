package com.github.syedahmedjamil.pushier;

import android.graphics.Bitmap;
import android.net.Uri;

public class InterestItem {
    public String interestName;
    public String icon;

    InterestItem(String interestName, String iconUri){
        this.interestName = interestName;
        this.icon = iconUri;
    }

}
