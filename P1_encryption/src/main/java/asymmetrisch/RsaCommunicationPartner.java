package asymmetrisch;

import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import java.time.format.SignStyle;

import javax.crypto.Cipher;

/**
 *
 * @author 
 */
public class RsaCommunicationPartner extends AbstractRsaCommunicationPartner {
    
    public RsaCommunicationPartner() throws Exception {
        super();
    }

    @Override
    public byte[] encryptMessage(String message) throws Exception {
        byte[] nachricht = message.getBytes("UTF-8");
        //Nutze den öffentliche Schüssel zum VERschlüsseln
        Cipher cipher = Cipher.getInstance(RSA_CIPHER_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, partnerKey);
        byte[] verschluesselt = cipher.doFinal(nachricht);
        return verschluesselt;
        //throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public String decryptMessage(byte[] ciphertext) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_CIPHER_MODE);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] entschluesselt = cipher.doFinal(ciphertext);
        String message = new String(entschluesselt, "UTF-8");
        return message;
        //throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void setPartnerKey(PublicKey partnerKey) {
        //throw new UnsupportedOperationException("Not supported yet."); 
        this.partnerKey = partnerKey;
    }

    @Override
    public PublicKey getPublicKey() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return publicKey;
    }

    @Override
    public byte[] comupteCryptographicHash(byte[] bytes) throws Exception {
        // Needed for assignment 3.
        //throw new UnsupportedOperationException("Not supported yet.");
        MessageDigest md= MessageDigest.getInstance(HASH_ALGORITHM);
        md.update(bytes);
        byte[] hashwert = md.digest();
        return hashwert;
    }

    @Override
    public byte[] signData(byte[] bytes) throws Exception {
        // Needed for assignment 3.
        //throw new UnsupportedOperationException("Not supported yet."); 
        Signature signature = Signature.getInstance(SIGANTURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(bytes);
        byte[] digitalSignatur = signature.sign();
        return digitalSignatur;

    }

    @Override
    public boolean verifySignature(byte[] signatureBytes, byte[] messageBytes) throws Exception {
        // Needed for assignment 3.
        //throw new UnsupportedOperationException("Not supported yet."); 
        Signature signature = Signature.getInstance(SIGANTURE_ALGORITHM);
        signature.initVerify(partnerKey);
        signature.update(messageBytes);
        boolean isCorrect = signature.verify(signatureBytes);
        return isCorrect;
    }
    
}
