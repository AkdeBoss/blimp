package ak.happinessinc.com.blimp;

/**
 * Created by Ananthakrishna on 06-03-2016.
 */
public class Constants {
    /*
      Logging flag
     */
    public static final boolean LOGGING = false;

    /*
      Your imgur client id. You need this to upload to imgur.

      More here: https://api.imgur.com/
     */
    public static final String MY_IMGUR_CLIENT_ID = "ca500324dc1454f";
    public static final String MY_IMGUR_CLIENT_SECRET = "69d39ef627045d8056e5193e99b724ba47beaa56";

    /*
      Redirect URL for android.
     */
    public static final String MY_IMGUR_REDIRECT_URL = "http://android";

    /*
      Client Auth
     */
    public static String getClientAuth() {
        return "Client-ID " + MY_IMGUR_CLIENT_ID;
    }

}
