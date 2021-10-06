package com.codepath.rkpandey.swap;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Util {
    static boolean isLandscapeOrientation(Activity act)
    {
        int currentOrientation = act.getResources().getConfiguration().orientation;

        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE)
            return true;
        else
            return false;
    }
    static void setFullScreen(Activity act)
    {
//		System.out.println("Util / _setFullScreen");
        Window win = act.getWindow();

        //ref: https://stackoverflow.com/questions/28983621/detect-soft-navigation-bar-availability-in-android-device-progmatically
        Resources res = act.getResources();
        int id = res.getIdentifier("config_showNavigationBar", "bool", "android");
        boolean hasNavBar = ( id > 0 && res.getBoolean(id));

//			System.out.println("Util / _setFullScreen / hasNavBar = " + hasNavBar);

        // flags
        int uiOptions = //View.SYSTEM_UI_FLAG_LAYOUT_STABLE | //??? why this flag will add bottom offset
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        //has navigation bar
        if(hasNavBar)
        {
            uiOptions = uiOptions
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        View decorView = act.getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiOptions);
    }

}
