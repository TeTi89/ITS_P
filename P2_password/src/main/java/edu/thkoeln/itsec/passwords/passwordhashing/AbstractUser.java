package edu.thkoeln.itsec.passwords.passwordhashing;
// src/main/java is the root directory for your Java source files.
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Simple abstract for a user. The user consists of a username and a (hashed)
 * password.
 *
 * @author Tobias Urban
 */
public abstract class AbstractUser {

    /**
     * The user name of the user.
     */
    protected String username;

    /**
     * The encoded hash code including the salt.
     */
    protected String passwordHash;

    protected abstract void hashPassword(String password) throws Exception;
    
    public AbstractUser(){
        super();
    }

    /**
     * Checks if the provided password is correct (i.e., if the hash values
     * match).
     *
     * @param password The password for the user.
     * @return true if the password is correct
     */
    public abstract boolean verifyPassword(String password);

    /**
     * Helper method to convert the object into a JSON string.
     *
     * @return A JSON representation of the Object.
     * @throws JsonProcessingException If the JSON conversion fails.
     */
    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(this);
        return (jsonStr);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String password) throws Exception {
        this.hashPassword(password);
    }

    public String getEncodedArgonHash() {
        return passwordHash;
    }

    public void setEncodedArgonHash(String encodedArgonHash) {
        this.passwordHash = encodedArgonHash;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", encodedArgonHash=" + passwordHash + '}';
    }

}
