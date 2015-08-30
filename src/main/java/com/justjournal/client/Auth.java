package com.justjournal.client;/*
 * jj_auth.java
 *
 * Created on October 10, 2005, 11:38 AM
 */

import javax.net.ssl.HttpsURLConnection;
import java.net.*;
import java.io.*;

/**
 * @author caryn
 */
public class Auth {

    private static final String JJ_LOGIN_OK = "JJ.LOGIN.OK";
    private String userName;
    private String passWord;

    /**
     * Creates instance of jj_auth
     *
     * @param username justjournal.com username
     * @param password justjournal.com password
     */
    public Auth(final String username, final String password) {
        userName = username;
        passWord = password;
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
            String data = URLEncoder.encode("username", "US-ASCII") + "=" +
                    URLEncoder.encode(userName, "US-ASCII");
            data += "&" + URLEncoder.encode("password", "US-ASCII") + "=" +
                    URLEncoder.encode(passWord, "US-ASCII");
            data += "&" + URLEncoder.encode("password_hash", "US-ASCII") + "=" +
                    URLEncoder.encode("", "US-ASCII");

            final HttpURLConnection conn = HttpUtils.getConnection("http://www.justjournal.com/loginAccount");
            conn.setRequestProperty("Content-Type", type);

            final OutputStreamWriter writer =
                    new OutputStreamWriter(conn.getOutputStream());

            writer.write(data);
            writer.flush();
            writer.close();
            // getting the response
            final BufferedReader input = new BufferedReader(new InputStreamReader
                    (conn.getInputStream()));
            final char[] returnCode = new char[50];
            int i = 0;
            int tempChar = input.read();
            while (tempChar != -1) {
                returnCode[i] = (char) tempChar;
                tempChar = input.read();
                i++;
            }

            input.close();
            String code = new String(returnCode);
            code = code.trim();

            if (code.equals(JJ_LOGIN_OK))
                return true;

            System.out.println(code);
        } catch (Exception e) {
            System.err.println(e.getMessage());
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
    public boolean SecureCheckAccount() {
        try {
            // sending the post request
            userName = userName.trim();
            String data = "username=" + userName;
            data += "&password=" + passWord;
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
            final char[] returnCode = new char[50];
            int i = 0;
            int tempChar = input.read();
            while (tempChar != -1) {
                returnCode[i] = (char) tempChar;
                tempChar = input.read();
                i++;
            }

            String code = new String(returnCode);
            code = code.trim();
            input.close();

            if (code.equals(JJ_LOGIN_OK))
                return true;

            System.out.println(code);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

}
