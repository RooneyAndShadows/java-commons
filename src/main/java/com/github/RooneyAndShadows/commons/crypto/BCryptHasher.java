package com.github.RooneyAndShadows.commons.crypto;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptHasher {

    public static String getBCrypt(String input) {
        String salt = BCrypt.gensalt(4);
        String hashed = BCrypt.hashpw(input,salt);
        return hashed;
    }
    
    public static boolean checkBCrypt(String input,String hashed){
        return BCrypt.checkpw(input, hashed);
    }
}