package symmetrisch;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class to symmetrically encrypt and decrypt a file using the AES algorithm.
 *
 * @author
 */
public class SymmetricEncryptor extends AbstractSymmetricEncryptor {
    private SecretKey secretKey;
    private IvParameterSpec ivSpec;
    public SymmetricEncryptor(String algorithm, boolean loadConfig) throws NoSuchAlgorithmException, IOException {
        super(algorithm, loadConfig);
        /* Erstelle einen sicheren geheimen Schlüssel unter Benutzung eines sicheren Zufallszahlengenerators.
         * Der Schlüssel wird in der Instanzvariable secretKey gespeichert.
         */
        if (loadConfig) {
            byte[] key = Files.readAllBytes(Paths.get(System.getProperty("user.dir"), "ITS_P","P1_encryption","resources","secretKey.key"));
            secretKey = new SecretKeySpec(key, "AES");
            byte[] iv = Files.readAllBytes(Paths.get(System.getProperty("user.dir"), "ITS_P","P1_encryption","resources","ivSpec.key"));
            ivSpec = new IvParameterSpec(iv);
            return;
        }
        else {
            SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128, rand);
            secretKey = keyGen.generateKey();
            //Erstelle Initialisierungsvektor (IV)
            byte[] random = new byte[16];
            rand.nextBytes(random);
            ivSpec = new IvParameterSpec(random);
            Files.write(Paths.get(System.getProperty("user.dir"), "ITS_P","P1_encryption","resources","secretKey.key"), secretKey.getEncoded()); //byte[]
            Files.write(Paths.get(System.getProperty("user.dir"), "ITS_P","P1_encryption","resources","ivSpec.key"), ivSpec.getIV()); //byte[]
        }
    }

    @Override
    public void encryptFile(File inputFile, File outputFile) throws Exception {
        byte[] nachricht = Files.readAllBytes(inputFile.toPath());
        //System.out.println("Oringinale Nachricht: ", nachricht.toString());

        //Versclüsseln von Daten
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] verschlusseteNachricht = cipher.doFinal(nachricht);
        //System.out.println("Verschlüsselte Nachricht: ", cipherText.toString());
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(verschlusseteNachricht);
        outputStream.close();

        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void decryptFile(File inputFile, File outputFile) throws Exception {
        //byte[] verschlusseteNachricht = inputFile.toString().getBytes("UTF-8");
        byte[] verschlusseteNachricht = Files.readAllBytes(inputFile.toPath());
        //Versclüsseln von Daten
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte [] entschluesselteNachricht = cipher.doFinal(verschlusseteNachricht);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(entschluesselteNachricht);
        outputStream.close();
        
        //throw new UnsupportedOperationException("Not supported yet.");
    }

}
