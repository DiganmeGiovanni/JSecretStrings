/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jsecretstrings.core;

import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * This class contains methods to encrypt and decrypt
 * strings using AES Algorithm with SHA-1 key hashes
 * 
 * @author giovanni
 * @Created on: Aug 27, 2015
 */
public class Crypter {
    
    private static final Random rand = new Random();
    private static final String ALGORITHM = "AES";

    /**
     * Takes a string and creates a hash through SHA-1
     * using only the first 128 bits
     * 
     * @param keyStr : Keyword to generate the hash
     * @return <code>java.security.Key</code>
     * 
     * @throws Exception is thrown if fatal error occurs
     */
    private static Key generateKey(String keyStr) throws Exception {
        
        byte[] keyValue   = keyStr.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        
        keyValue = sha.digest(keyValue);
        keyValue = Arrays.copyOf(keyValue, 16); // Solo los primeros 128 bits    
        
        return new SecretKeySpec(keyValue, ALGORITHM);
    }

    /**
     * Encrypts <code>data</code> using <code>keyWord</code> 
     * to generate a hash
     * 
     * @param data String to encrypt
     * @param keyWord Super secret word to create hash
     * @return Encrypted resulting string
     */
    public static String encrypt(String data, String keyWord) {
        try {
            Key key = generateKey(keyWord);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);

            byte[] encVal = c.doFinal(data.getBytes());
            String encryptedValue = DatatypeConverter.printBase64Binary(encVal);
            return encryptedValue;
        }
        catch(Exception err) {
            System.err.println("ERROR: Maybe the keyWord is invalid.");
            return null;
        }
    }

    /**
     * Takes an encrypted string and decrypts using inverse process than
     * <code>encrypt</code>
     * 
     * @param encryptedData string generated by <code>encrypt</code> method
     * @param keyWord The same used with <code>encrypt</code> method 
     *                to generate <code>encryptedData</code>
     * 
     * @return decrypted string
     */
    public static String decrypt(String encryptedData, String keyWord) {
        try {
            encryptedData = encryptedData.replace(" ", "+");
            
            Key key = generateKey(keyWord);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);

            byte[] decordedValue = DatatypeConverter.parseBase64Binary(encryptedData);
            byte[] decValue = c.doFinal(decordedValue);
            String decryptedValue = new String(decValue);
            return decryptedValue;
        }
        catch(Exception err) {
            System.err.println("ERROR: Maybe the keyWord is invalid.");
            return null;
        }
    }
    
    /**
     * Uses a custom high security encryption algorithm to encrypt words into
     * a single String
     * 
     * @param wordsToEncrypt
     * @param keyword super secret word to encrypt words
     * @return 
     */
    public static String ittEncrypt(ArrayList<String> wordsToEncrypt, String keyword) {
        StringBuilder result = new StringBuilder();
        for(String word : wordsToEncrypt) {
            
            int falseCharsLength = randomInt(10, 15);
            String falseChars = generateRandomNumericString(falseCharsLength);
            
            result.append(falseChars);
            result.append(encrypt(word, keyword));
            result.append(falseCharsLength);
            result.append("@-@-@");
        }
        
        result.delete(result.length() - 5, result.length());
        return result.toString();
    }
    
    /**
     * Uses the custom high security decryption algorithm to decrypt and separate
     * the encrypted string into multiple original words.
     * 
     * @param encrypted An String generated by <code>ittEncrypt</code>
     * @param keyword Super secret word to decrypt words
     * @return An String array identical to array passed to <code>ittEncrypt</code>
     *         in encryption phase
     */
    public static ArrayList<String> ittDecrypt(String encrypted, String keyword) {
        encrypted = encrypted.replace(" ", "+");
        String[] splited = encrypted.split("@-@-@");
        ArrayList<String> decrypteds = new ArrayList<>();
        
        for(String enWord : splited) {
            int falseCharsLength = Integer.parseInt(enWord.substring(enWord.length() - 2, enWord.length()));
            String pureEncrypted = enWord.substring(falseCharsLength, enWord.length() - 2);
            String decryptedWord = decrypt(pureEncrypted, keyword);
            decrypteds.add(decryptedWord);
        }
        
        return decrypteds;
    }
    
    /**
     * Generates a random numerical string
     * @param length Length of expected string
     * @return 
     */
    private static String generateRandomNumericString(int length) {
        StringBuilder randomString = new StringBuilder();
        for (int i=0; i<length; i++) {
            randomString.append(randomInt(1, 9));
        }
        
        return randomString.toString();
    }
    
    /**
     * Generates a random integer between min and max
     * 
     * @param min Minimal acceptable integer
     * @param max Maximal acceptable integer
     * @return Generated integer
     */
    private static int randomInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

}