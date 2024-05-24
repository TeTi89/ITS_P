package symmetrisch;

import java.io.File;

/**
 * Abstract class for en- and decrypting a file.
 *
 * @author Tobias Urban
 */
public abstract class AbstractSymmetricEncryptor {

    public final String AES_CIPHER_MODE;

    /**
     * Initializes the AES cipher mode.
     *
     * @param aesCipherMode The algorithm used for encryption (e.g.,
     * AES/ECB/NoPadding")
     * @param loadConfig Can be used in the realizing class.
     */
    public AbstractSymmetricEncryptor(String aesCipherMode, boolean loadConfig) {
        this.AES_CIPHER_MODE = aesCipherMode;
    }

    /**
     * Encrypts a given file and writes the cipher text to an output file.
     *
     * @param inputFile The file that that should be encrypted.
     * @param outputFile The file to which the cipher text is written.
     * @throws Exception Depends on the implementation...
     */
    public abstract void encryptFile(File inputFile, File outputFile)
            throws Exception;

    /**
     * Decrypts a given file and writes the plain text to an output file.
     *
     * @param inputFile The file that holds the cipher text.
     * @param outputFile The file to which the decrypted file is written.
     * @throws Exception Depends on the implementation...
     */
    public abstract void decryptFile(File inputFile, File outputFile)
            throws Exception;

}
