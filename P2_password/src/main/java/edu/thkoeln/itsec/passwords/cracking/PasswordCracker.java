package edu.thkoeln.itsec.passwords.cracking;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//extra imports
import java.io.BufferedReader;
//import java.io.File;
import java.io.FileReader;

/**
 *
 * @author Tobias Urban
 */
public class PasswordCracker {

    /**
     * The Map holds the users and their hashed passwords.
     */
    public static final Map<String, byte[]> users;

    /**
     * The *known* alphabet used in assignment 1.3.
     */
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz1234567890#*!&?".toCharArray();

    /**
     * Generate dummy users with fixed passwords for the three assignments.
     */
    static {
        users = new HashMap<>();
        users.put("tobiasurban", new byte[]{123, -69, 96, -55, -69, -24, 25, -22, 47, 39, 89, -9, -112, 6, -119, -84, -75, -11, -80, 97, 91, 35, 88, 56, -47, 105, 7, 112, -82, -80, 53, 127, -70, 123, 5, -39, -115, 116, 26, 29, 11, -11, 114, 123, -21, -4, 55, -20, -20, -92, 108, 52, -2, 61, 102, 110, 91, -126, 43, 77, -56, -73, 80, 59});
        users.put("johndoe", new byte[]{-32, 17, -35, -24, 99, 79, 16, -80, 71, 3, 44, -33, 23, -81, 86, 15, -94, -6, 125, -40, 112, 55, 84, -8, -14, 28, 9, 94, 18, 45, -77, -126, -34, -35, -71, -60, 100, 70, -114, -119, -114, -105, 67, 43, 66, 71, -113, 87, -61, 13, 103, 68, 58, 106, 96, -123, 93, -101, 54, -63, 126, 104, 41, -90});
        users.put("martinamusterfrau", new byte[]{24, -125, 102, -127, 86, -22, 112, 17, -57, -104, -102, -65, -57, -90, 123, 56, 82, 30, 99, 19, -116, 108, -60, 114, -114, 96, 9, -12, 47, -72, -90, -1, -76, 2, -102, -106, 116, 71, -21, 56, -108, -84, 78, -120, -17, 89, 89, -22, -89, -78, 70, 7, -4, -28, 120, 96, 67, -100, 115, 32, 102, -63, -123, -54});
    }

    /**
     * The method computes of the provided combination of user name and password
     * is correct.
     *
     * @param username The username (in this assignment either "tobiasurban",
     * "johndoe", or "martinamusterfrau")
     * @param password The password that shall be tested.
     * @return True if the password is correct; false otherwise.
     * @throws NoSuchAlgorithmException If the JVM does not support the
     * computation of SHA3-512 hashes.
     */
    private static boolean login(String username, String password) throws NoSuchAlgorithmException {
        byte[] correctHash = users.get(username);
        MessageDigest digest = MessageDigest.getInstance("SHA3-512");
        byte[] computedHash = digest.digest(password.getBytes());
        return (Arrays.equals(correctHash, computedHash));
    }

    /**
     * Assignment 1: "Häufiges Passwort raten". Use the username tobiasurban for
     * this assignment.
     *
     * @throws NoSuchAlgorithmException If the JVM does not support the
     * computation of SHA3-512 hashes.
     */
    public static void crackSimple() throws NoSuchAlgorithmException {
        if (login("tobiasurban", "passwort")) {
            System.out.println("Assignment 1: Yay! The password 'passwort' worked.");
        } else {
            System.out.println("The password is wrong.");
        }
    }

    /**
     * Assignment 2: "Passwortlisten". Use the username johndoe for this
     * assignment.
     *
     * @throws NoSuchAlgorithmException If the JVM does not support the
     * computation of SHA3-512 hashes.
     */
    public static void crackAdvanced() throws NoSuchAlgorithmException {
        // The password is in the list of the 100000 most common passwords.
        // The list is available at ./resources/10_million_password_list_top_100000.txt
        /*
        if (login("johndoe", "passwort")) {
            System.out.println("Yay! The password 'passwort' worked.");
        } else {
            System.out.println("The password is wrong.");
        }
        */

        //String filePath = Paths.get(System.getProperty("user.dir"), "ITS_P","P2_password","resources","10_million_password_list_top_100000.txt").toString();
        String filePath = Paths.get(System.getProperty("user.dir"), "P2_password","resources","3,000,000_combo_list.txt").toString();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (login("johndoe", line)) {
                    System.out.println("Assignment 2: Yay! The password '" + line + "' worked.");
                    return;
                }
            }
            System.out.println("The password not found in the list.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Assignment 3: "Brute Force Attacks". Use the username martinamusterfrau
     * for this assignment.
     *
     * @throws NoSuchAlgorithmException If the JVM does not support the
     * computation of SHA3-512 hashes.
     */
    public static void crackBruteForce() throws NoSuchAlgorithmException {
        /*
        if (login("martinamusterfrau", "TH Koeln")) {
            System.out.println("Yay! The password 'passwort' worked.");
        } else {
            System.out.println("The password is wrong.");
        }
        */
        String password;
        for (int i = 0; i < ALPHABET.length; i++) {
            System.out.println("Assignment 3: Working hard to find the password...");
            for (int j = 0; j < ALPHABET.length; j++) {
                for (int k = 0; k < ALPHABET.length; k++) {
                    for (int l = 0; l < ALPHABET.length; l++) {
                        for (int m = 0; m < ALPHABET.length; m++) {
                          password = ""+ ALPHABET[i] + ALPHABET[j] + ALPHABET[k] + ALPHABET[l] + ALPHABET[m];
                          if(login("martinamusterfrau", password)){
                            System.out.println("Assignment 3: Yay! The password '" + password + "' worked.");
                            return;
                          };
                        }
                    }
                }
            }
        }
        System.out.println("The password is not found.");
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        crackSimple();
        crackAdvanced();
        crackBruteForce();
    }
}
