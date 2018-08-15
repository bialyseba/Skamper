package com.workspaceapp.skamper.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashHelper {
    public static String hashString(String string) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance( "SHA-256" );
        md.update(string.getBytes( StandardCharsets.UTF_8 ) );
        byte[] digest = md.digest();
        return String.format( "%064x", new BigInteger( 1, digest ) );
    }
}
