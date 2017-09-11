package com.intelligence.androidlibrary.slidesidemenu;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

class Utils {

    public static int convertDPtoPixels(Context context, int dp) {
        Resources resource = context.getResources();
        return(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resource.getDisplayMetrics());
    }

}
