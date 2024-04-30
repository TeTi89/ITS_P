package edu.thkoeln.itsec.verschluesselung.symmetrisch;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Example on how to encrypt a local file using the AES algorithm.
 *
 * @author Tobias Urban
 */
public class PerformSymmetricEncryption {

    /**
     * Input file that holds the plain text.
     */
    private static final Path PLAINTEXT_INPUT_FILE_PATH = Paths.get(
            System.getProperty("user.dir"), "ITS_P","P1_encryption","resources", "sensitive_data.txt");

    /**
     * The output file for the cipher text.
     */
    private static final Path SIM_CYPHERTEXT_OUTPUT_FILE_PATH = Paths.get(
            System.getProperty("user.dir"), "ITS_P","P1_encryption","resources", "sensitive_data_sim.enc");
    /**
     * The output file of the decrypted cipher text.
     */
    private static final Path PLAINTEXT_SIM_DEC_FILE_PATH = Paths.get(
            System.getProperty("user.dir"), "ITS_P","P1_encryption","resources",
            "sensitive_data_sim_decrypted.txt");

    /**
     * Defines the AES cipher mode that will be used for encryption and
     * decryption.
     */
    //TODU: Change the AES cipher mode to the one you have implemented.
    //private static final String AES_CIPER_MODE = "CHANGE ME";
    private static final String AES_CIPER_MODE = "AES/CBC/PKCS5Padding";

    /**
     * After correctly implementing the SymmetricEncryptor class this code
 should run without any adjustments..
     *
     * @param args Not used
     * @throws Exception if something goes wrong.
     */
    public static void main(String[] args) throws Exception {

        /* Encrypt file. */
        System.out.println("Encrpyting test file...");
        AbstractSymmetricEncryptor symEncrypter = new SymmetricEncryptor(
                AES_CIPER_MODE, false);
        symEncrypter.encryptFile(PLAINTEXT_INPUT_FILE_PATH.toFile(),
                SIM_CYPHERTEXT_OUTPUT_FILE_PATH.toFile());

        /* Decrypt file. */
        System.out.println("\nDecrpyting test file...");
        AbstractSymmetricEncryptor symEncrypter1 = new SymmetricEncryptor(
                AES_CIPER_MODE, true);
        symEncrypter.decryptFile(SIM_CYPHERTEXT_OUTPUT_FILE_PATH.toFile(),
                PLAINTEXT_SIM_DEC_FILE_PATH.toFile());

        /* Check if encrypting and decrypting worked. */
        byte[] plain_text = Files.readAllBytes(PLAINTEXT_INPUT_FILE_PATH);
        byte[] cipher_text = Files.readAllBytes(SIM_CYPHERTEXT_OUTPUT_FILE_PATH);
        byte[] decrypted_text = Files.readAllBytes(PLAINTEXT_SIM_DEC_FILE_PATH);
        if (!Arrays.equals(plain_text, decrypted_text)) {
            throw new RuntimeException("Input text does not equal decrypted"
                    + " text.");
        } else if (Arrays.equals(plain_text, cipher_text)) {
            throw new RuntimeException("Input text does not equals cipher"
                    + " text.");
        }
    }
}
