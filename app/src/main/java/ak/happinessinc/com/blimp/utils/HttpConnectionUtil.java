package ak.happinessinc.com.blimp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ananthakrishna on 06-03-2016.
 */
public abstract class HttpConnectionUtil {

    public static final String REQUEST_METHOD_POST = "POST";
    public static final String REQUEST_METHOD_GET = "GET";

    private String method;
    private String _url;

    private int responseCode;

    private HttpURLConnection connection;


    public HttpConnectionUtil(String url, String method) {
        this._url = url;
        this.method = method;
    }


    public abstract void getRequestHeaders(HttpURLConnection connection);

    public abstract byte[] getRequestBody();

    public int getResponseCode() {
        return responseCode;
    }

    public InputStream getResponse() throws IOException {
        return connection.getInputStream();
    }


    public InputStream getError() throws IOException {
        return connection.getErrorStream();
    }

    public void execute() throws IOException {
        String url = _url;
        while (executeHttp(url)) {
            url = connection.getHeaderField("Location");
            System.out.println(url);
        }
    }

    private boolean executeHttp(String _url) throws IOException {

        URL url = new URL(_url);
        connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);

        getRequestHeaders(connection);

        if (REQUEST_METHOD_POST.equals(method)) {
            connection.setDoOutput(true);
            byte[] reqBody = getRequestBody();

            OutputStream stream = connection.getOutputStream();
            stream.write(reqBody, 0, reqBody.length);
            stream.flush();
            stream.close();
        }

        responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_MOVED_PERM
                || responseCode == HttpURLConnection.HTTP_MOVED_TEMP
                || responseCode == HttpURLConnection.HTTP_SEE_OTHER)
            return true;

        return false;
    }
}
