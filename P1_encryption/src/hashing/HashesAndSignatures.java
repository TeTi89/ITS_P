package edu.thkoeln.itsec.verschluesselung.hashing;

import edu.thkoeln.itsec.verschluesselung.asymmetrisch.AbstractRsaCommunicationPartner;
import edu.thkoeln.itsec.verschluesselung.asymmetrisch.RsaCommunicationPartner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 *
 * @author Tobias Urban
 */
public class HashesAndSignatures {
        // Path to the input image file. Change this path if necessary to fit your file structure.
    private static final Path INPUT_IMAGE = Paths.get(
            System.getProperty("user.dir"), "ITS_P","P1_encryption","resources", "TH-Koeln-logo.png");

    public static void main(String[] args) throws Exception {
        System.out.println("Constructing Alice...");
        AbstractRsaCommunicationPartner alice
                = new RsaCommunicationPartner();
        System.out.println("Alice is now online!");        
        System.out.println("Constructing Bob...");
        AbstractRsaCommunicationPartner bob
                = new RsaCommunicationPartner();
        System.out.println("Bob is now online!");
        /*Assigment 3.1*/
        /* Get the data to hash and sign.*/
        byte[] inputData = Files.readAllBytes(INPUT_IMAGE);

        /* Compute a cryptographic hash. */
        System.out.println("\n*** Hash Value");
        byte[] hash = alice.comupteCryptographicHash(inputData);
        //System.out.println("Input data:\t" + Arrays.toString(inputData));
        System.out.println("Size of input data:\t" + inputData.length);
        System.out.println("Hash value:\t" + Arrays.toString(hash));
        System.out.println("Size of hash value:\t" + hash.length);
        
        /*Assigment 3.2*/
        /* Share keys bewteen the partners. */
        alice.setPartnerKey(bob.getPublicKey());
        bob.setPartnerKey(alice.getPublicKey());
        
        /* Compute, sign, and verify a digital signature for the input data. */
        System.out.println("\n*** Digital Signature");
        byte[] digitalSignature = alice.signData(inputData);
        boolean isCorrectSignature = bob.verifySignature(digitalSignature,
                inputData); // Should be true
        boolean isCorrectSignature1 = alice.verifySignature(digitalSignature,
                inputData); // Should be false
        System.out.println("Digital signature:\t" + Arrays.toString(
                digitalSignature));
        System.out.println("Size of signature:\t" + digitalSignature.length);
        if (!isCorrectSignature) {
            throw new RuntimeException("Teh signutre and key material was "
                    + "correct but the verification failed. ");
        }
        if (isCorrectSignature1) {
            throw new RuntimeException("The wrong key was used to check the "
                    + "signature but the verification was still correct.");
        }
    }
}
