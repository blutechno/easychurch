/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *
 * @author jean pierre
 */
public class MD5 {

    public static String md5(String plainText) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(plainText.getBytes(), 0, plainText.length());
            return new BigInteger(1, m.digest()).toString(16);
        } catch (Exception ex) {
            System.out.println("There is error in your code");
            return null;
        }
    }
}
