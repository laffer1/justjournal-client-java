package com.justjournal.client;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * @author Lucas Holt
 */
public class jj_authTest {

    private final static String FAIL_USER = "test";
    private final static String FAIL_PASS = "test";

    @Test
    public void testCheckAccountFail() {
        final Auth jjAuth = new Auth(FAIL_USER, FAIL_PASS);

        assertFalse(jjAuth.checkAccount()); // non ssl
    }

    @Test
    public void testSecureCheckAccountFail() {
        final Auth jjAuth = new Auth(FAIL_USER, FAIL_PASS);
        assertFalse(jjAuth.SecureCheckAccount());
    }
}
