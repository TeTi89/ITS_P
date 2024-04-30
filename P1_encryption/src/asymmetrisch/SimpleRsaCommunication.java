package edu.thkoeln.itsec.verschluesselung.asymmetrisch;

import java.util.Arrays;

/**
 * Demonstration on how two communication partners (Alice and Bob) can encrypt
 * and decrypt a message using the RSA protocol.
 *
 * @author Tobias Urban
 */
public class SimpleRsaCommunication {

    public static void main(String[] args) throws Exception {
        /* Example plaintext*/
        String plainText = "MyVerySecretPasswordString!";
        //String plainText = "MyVerySecretPasswordString!".repeat(999);

        /* Construct the communication partners.*/
        System.out.println("Creating Alice...");
        AbstractRsaCommunicationPartner alice = new RsaCommunicationPartner();
        System.out.println("Creating Bob...");
        AbstractRsaCommunicationPartner bob = new RsaCommunicationPartner();

        /* Share the keys between the partners.*/
        alice.setPartnerKey(bob.getPublicKey());
        bob.setPartnerKey(alice.getPublicKey());

        /* Encrypt and decrypt the plain text.*/
        System.out.println("\n--------------");
        System.out.println("Plain text:\t\t" + plainText);
        byte[] ciphertext = alice.encryptMessage(plainText);
        System.out.println("Cipher text (bytes):\t" + Arrays.toString(ciphertext));
        String decryptedMessage = bob.decryptMessage(ciphertext);
        System.out.println("Decrypted message:\t" + decryptedMessage);
        System.out.println("Is the decrpyted text the plain text? " + (decryptedMessage.equals(plainText)));

    }

}
