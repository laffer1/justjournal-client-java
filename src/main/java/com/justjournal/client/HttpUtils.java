package com.justjournal.client;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Lucas Holt
 */
public class HttpUtils {

    public static final String FORM_URLENCODED = "application/x-www-form-urlencoded";
    private static final String USER_AGENT = "JustJournal";
    public static final String HTTP_POST = "POST";
    public static final String USER_AGENT_HEADER = "User-Agent";

    private HttpUtils() {
    }

    public static HttpsURLConnection getSSLConnection(final String url) throws IOException {
        final URL jj = new URL(url);
        final HttpsURLConnection sslConn = (HttpsURLConnection) jj.openConnection();
        sslConn.setRequestProperty(USER_AGENT_HEADER, USER_AGENT);

        sslConn.setRequestMethod(HTTP_POST);
        sslConn.setDoOutput(true);
        sslConn.setDoInput(true);
        return sslConn;
    }

    public static HttpURLConnection getConnection(final String url) throws IOException {
        final URL jj = new URL(url);
        final HttpURLConnection conn = (HttpURLConnection) jj.openConnection();

        // set user-agent header in POST request
        conn.setRequestProperty(USER_AGENT_HEADER, USER_AGENT);

        conn.setRequestMethod(HTTP_POST);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        return conn;
    }
}
