package edu.thkoeln.itsec.passwords.passwordhashing;

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

    }

    @Override
    protected void hashPassword(String password) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean verifyPassword(String password) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

}
