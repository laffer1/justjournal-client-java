package com.justjournal.client;

import com.justjournal.client.Auth;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * @author Lucas Holt
 */
public class jj_authTest {

    @Test
    public void testCheckAccountFail() {
        Auth jjAuth = new Auth("test", "test");

        assertFalse(jjAuth.checkAccount()); // non ssl
    }

    @Test
    public void testSecureCheckAccountFail() {
        Auth jjAuth = new Auth("test", "test");
        assertFalse(jjAuth.SecureCheckAccount());
    }
}
