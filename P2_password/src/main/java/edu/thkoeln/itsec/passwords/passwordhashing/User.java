package edu.thkoeln.itsec.passwords.passwordhashing;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

/**
 *
 * @author 
 */
public class User extends AbstractUser {

    /**
     * Empty constructor needed to use Jackson.
     */
    public User() {
        super();
    }

    /**
     * Constructor creates a new user with a hashes the provided password.
     *
     * @param name The user name
     * @param password The password that will be hashed using argon2.
     * @throws java.lang.Exception
     */
    public User(String name, String password) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
        setUsername(name);
        setPasswordHash(password);

    }

    @SuppressWarnings("deprecation")
    @Override
    protected void hashPassword(String password) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
        //saltLen = 512, hashLen = 512;
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i, 512, 512);
        String encodedArgonHash = argon2.hash(10, 65536, 1, password);
        setEncodedArgonHash(encodedArgonHash);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean verifyPassword(String password) {
        //throw new UnsupportedOperationException("Not supported yet.");
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i, 512, 512);
        return argon2.verify(getEncodedArgonHash(), password);
    }

}
