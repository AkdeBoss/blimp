package ak.happinessinc.com.blimp.utils;

import android.util.Log;

import ak.happinessinc.com.blimp.Constants;

/**
 * Created by Ananthakrishna on 06-03-2016. reffrence : https://github.com/AKiniyalocts/imgur-android
 */
public class aLog {
    public static void w (String TAG, String msg){
        if(Constants.LOGGING) {
            if (TAG != null && msg != null)
                Log.w(TAG, msg);
        }
    }

}
