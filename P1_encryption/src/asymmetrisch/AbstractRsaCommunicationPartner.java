package edu.thkoeln.itsec.verschluesselung.asymmetrisch;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.KeyGenerator;

/**
 * Abstract class defining a communication partner for a RSA-based
 * communication.
 *
 * @author Tobias Urban
 */
public abstract class AbstractRsaCommunicationPartner {

    /**
     * The public key of this communication partner (can be shared).
     */
    protected final PublicKey publicKey;

    /**
     * The private key of this communication partner (keep it as a secret!).
     */
    protected final PrivateKey privateKey;

    /**
     * The cipher mode used for the communication
     */
    protected final String RSA_CIPHER_MODE = "RSA/ECB/PKCS1Padding";

    /**
     * The length of the RSA keys.
     */
    protected final int KEY_LENGTH = 2048;

    /**
     * The (public) key of the communication partner.
     */
    protected PublicKey partnerKey = null;
    
    /**
     * The used cryptographic hash function.
     */
    protected final String HASH_ALGORITHM = "SHA-256";
    
    /**
     * The used function to digitally sign data.
     */
    protected final String SIGANTURE_ALGORITHM = "SHA512withRSA";

    /**
     * Initializes the RSA keys of this communication partner.
     *
     * @throws Exception If something goes wrong.
     */
    public AbstractRsaCommunicationPartner() throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
        
        //Erstelle eine neuen RSA Key-Generator
        SecureRandom rand = new SecureRandom(); //Sicherer Zufallszahlengenerator
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        /* Initialisier den Key-Generator mit einer Zufallszahl in der Größe der Schlüssellänge (hier 2048). */
        keyPairGenerator.initialize(KEY_LENGTH, rand);
        //Erezuge das Schüsselpaar (Privater und Öffentlicher Schlüssel)
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //Sichere das Schlüsselpaar
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
        //Files.write(Paths.get(System.getProperty("user.dir"), "ITS_P","P1_encryption","resources","private_key.key"), keyPair.getPrivate().getEncoded());
        //Files.write(Paths.get(System.getProperty("user.dir"), "ITS_P","P1_encryption","resources","public_key.key"), keyPair.getPublic().getEncoded());
        //System.out.println("\t Private key: \t" + Arrays.toString(this.privateKey.getEncoded()));
        //System.out.println("\t Public key: \t" + Arrays.toString(this.publicKey.getEncoded()));
    }

    /**
     * Encrypts the given message with the provided private key of the
     * communication partner (see setPartnerKey(PublicKey partnerKey)).
     *
     * @param message A message that should be encrypted.
     * @return The bytes of the cipher text.
     * @throws Exception Depends on the implementation.
     */
    public abstract byte[] encryptMessage(String message) throws Exception;

    /**
     * Decrypts the provided cipher text using the private key of this
     * communication partner.
     *
     * @param ciphertext The cipher text.
     * @return The decrypted string
     * @throws Exception Depends on the implementation.
     */
    public abstract String decryptMessage(byte[] ciphertext) throws Exception;

    /**
     * Sets the public key of the partner used to encrypt messages.
     *
     * @param partnerKey The key of the partner.
     */
    public abstract void setPartnerKey(PublicKey partnerKey);

    /**
     * Returns the public key of this communication partner.
     *
     * @return The public key of this communication partner.
     */
    public abstract PublicKey getPublicKey();

    /**
     * Computes a cryptographic hash value for the provided data.
     *
     * @param bytes The data for which a cryptographic hash is computed.
     * @return The hash value
     * @throws Exception Depends on the implementation.
     */
    public abstract byte[] comupteCryptographicHash(byte[] bytes)
            throws Exception;
    
    /**
     * Signs the provided data using the private key of this class.
     * @param bytes The data that will be digitally signed.
     * @return The digital signature.
     * @throws Exception Depends on the implementation.
     */
    public abstract byte[] signData(byte[] bytes) throws Exception;

    /**
     * Verifies if the provided data was signed by the communication partner (i.e., the partnerKey stored by the class).
     * @param signatureBytes The digital Signature.
     * @param messageBytes The data that was signed
     * @return true if the signature fits to the provided data and was signed by the communication partner. False otherwise.
     * @throws Exception Depends on the implementation.
     */
    public abstract boolean verifySignature(byte[] signatureBytes,
            byte[] messageBytes) throws Exception;

}
