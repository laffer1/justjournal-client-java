package com.justjournal.client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.net.*;
import java.io.*;

/**
 * @author caryn
 */
public class Auth {

    final Logger log = LoggerFactory.getLogger(Auth.class);

    private static final String JJ_LOGIN_OK = "JJ.LOGIN.OK";
    private static final String ASCII = "US-ASCII";
    private String userName;
    private String password;

    /**
     * Creates instance of jj_auth
     *
     * @param username justjournal.com username
     * @param password justjournal.com password
     */
    public Auth(final String username, final String password) {
        userName = username;
        this.password = password;
    }

    /**
     * Checks account over unsecured channel
     *
     * @return true if login was successful
     */
    public boolean checkAccount() {
        // check the  username and password against
        //  servlet
        try {
            // sending the post request
            final String type = HttpUtils.FORM_URLENCODED;
            String data = URLEncoder.encode("username", ASCII) + "=" +
                    URLEncoder.encode(userName, ASCII);
            data += "&" + URLEncoder.encode("password", ASCII) + "=" +
                    URLEncoder.encode(password, ASCII);
            data += "&" + URLEncoder.encode("password_hash", ASCII) + "=" +
                    URLEncoder.encode("", ASCII);

            final HttpURLConnection conn = HttpUtils.getConnection("http://www.justjournal.com/loginAccount");
            conn.setRequestProperty("Content-Type", type);

            final OutputStreamWriter writer =
                    new OutputStreamWriter(conn.getOutputStream());

            writer.write(data);
            writer.flush();
            writer.close();
            // getting the response
            final BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final char[] returnCode = new char[512];
            int i = 0;
            int tempChar = input.read();
            while (tempChar != -1 && i < 512) {
                returnCode[i] = (char) tempChar;
                tempChar = input.read();
                i++;
            }

            input.close();
            String code = new String(returnCode);
            code = code.trim();

            log.debug("Code is {}", code);

            if (code.equals(JJ_LOGIN_OK))
                return true;
        } catch (Exception e) {
            log.error("Unable to validate login", e);
        }
        // if the function gets this far, an error resulted
        // for GUI testing return true
        return false;
    }

    /**
     * Checks account over secure channel
     *
     * @return true if account is valid
     */
    public boolean secureCheckAccount() {
        try {
            // sending the post request
            userName = userName.trim();
            String data = "username=" + userName;
            data += "&password=" + password;
            data += "&password_hash=";
            final HttpsURLConnection sslConn = HttpUtils.getSSLConnection("https://www.justjournal.com/loginAccount");
            final OutputStreamWriter writer =
                    new OutputStreamWriter(sslConn.getOutputStream());

            writer.write(data);
            writer.flush();
            writer.close();
            // getting the response
            final BufferedReader input = new BufferedReader(new InputStreamReader
                    (sslConn.getInputStream()));
            final char[] returnCode = new char[512];
            int i = 0;
            int tempChar = input.read();
            while (tempChar != -1 && i < 512) {
                returnCode[i] = (char) tempChar;
                tempChar = input.read();
                i++;
            }

            String code = new String(returnCode);
            code = code.trim();
            input.close();

            log.debug("Code is {}", code);

            if (code.equals(JJ_LOGIN_OK))
                return true;
        } catch (Exception e) {
            log.error("Unable to validate secure login", e);
        }
        return false;
    }

}
