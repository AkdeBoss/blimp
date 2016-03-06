package ak.happinessinc.com.blimp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Ananthakrishna on 06-03-2016.
 */
public class NetworkUtils {
    public static final String TAG = NetworkUtils.class.getSimpleName();

    public static boolean isConnected(Context mContext) {
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }
        }catch (Exception ex){
            aLog.w(TAG, ex.getMessage());
        }
        return false;
    }


}
