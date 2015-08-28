/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jsecretstring.frontend;

import java.util.Scanner;
import org.jsecretstrings.core.Crypter;

/**
 * Console client to encrypt and decrypt messages/strings
 * 
 * @author giovanni
 * @Created on: Aug 27, 2015
 */
public class ClientConsole {
    
    Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        ClientConsole consoleClient = new ClientConsole();
        
        System.out.println("--- --- --- --- --- --- --- --- --- --- --- ---");
        System.out.println("--- --- --- --- --- --- --- --- --- --- --- ---");
        System.out.println("--- --- --- --- JSecret Strings --- --- --- ---");
        System.out.println("--- --- --- --- --- --- --- --- --- --- --- ---");
        System.out.println("--- --- --- --- --- --- --- --- --- --- --- ---");
        System.out.println("");
            
        consoleClient.start();
    }
    
    public void start() {
        
        do {
            
            System.out.println("--> SELECT A FUNCTION.");
            System.out.println("--> * 0. Finish JSecretStrings");
            System.out.println("--> * 1. Encrypt String");
            System.out.println("--> * 2. Decrypt String");
            
            System.out.print("\nType your selection: ");
            switch(scanner.nextInt()) {
                case 0:
                    System.out.println("Happy coding !!");
                    System.exit(0);
                    break;
                
                case 1:
                    System.out.println("");
                    System.out.print("Type string to encrypt: ");
                    scanner.nextLine();
                    final String toEncrypt = scanner.nextLine();
                    
                    System.out.print("Super secret Keyword: ");
                    final String keyWord = scanner.nextLine();
                    
                    final String encrypted = Crypter.encrypt(toEncrypt, keyWord);
                    System.out.println("\nEncrypted string: " + encrypted);
                    break;
                
                case 2:
                    System.out.println("");
                    System.out.print("Type string to decrypt: ");
                    scanner.nextLine();
                    final String toDecrypt = scanner.nextLine();
                    
                    System.out.print("Super secret Keyword: ");
                    final String secretKeyWord = scanner.nextLine();
                    
                    final String decrypted = Crypter.decrypt(toDecrypt, secretKeyWord);
                    System.out.println("\nDecrypted string: " + decrypted);
                    break;
                
                default:
                    System.out.println("Please select a valid option");
            }
            
            System.out.print("\n[TYPE A CHAR AND ENTER TO CONTINUE] ");
    	    scanner.next();
            
        } while(true);
    }
    
}
