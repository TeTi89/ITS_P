package edu.thkoeln.itsec.passwords.passwordhashing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

/**
 * Simple demonstrator on how to use the argon2 algorithm to hash, store, and
 * verify passwords.
 *
 * @author
 */
public class PasswordHashing {

    /**
     * Path to store the dummy user "database".
     */
    private static final Path DB_PATH = Paths.get(
            System.getProperty("user.dir"),"ITS_P","P2_password","resources", "user_db.txt");

    /**
     * Creates three dummy users.
     *
     * @throws Exception Depends on the implementation of the User class.
     */
    public static void generateDummyUsers() throws Exception {
        /* Construct dummy users.*/
        AbstractUser userAlice = new User("Alice", "c4F3w_U6£mkeNygv%&N£fK0wq");
        AbstractUser userBob = new User("Bob", "secretPassword");
        AbstractUser userMalory = new User("Malory", "123456789");

        HashSet<AbstractUser> userDB = new HashSet<>();
        userDB.add(userAlice);
        userDB.add(userBob);
        userDB.add(userMalory);

        /*Store user "database." */
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userDB);
        Files.write(DB_PATH, jsonStr.getBytes());
    }

    /**
     * Verify if the storing and loading of the passwords worked
     *
     * @throws Exception Depends on the implementation of the User class.
     */
    public static void testPasswordsforUsers() throws Exception {
        /* Read the user "database." */
        String userDbString = Files.readString(DB_PATH);

        /* Verify that the passwords were not stored in plaintext.*/
        if (userDbString.contains("123456789")) {
            throw new IllegalStateException("ERROR! The user database contains "
                    + "the cleartext passwords.");
        }
        if (!userDbString.matches("(?s)(.*?)\\$argon2i\\$v=[0-9]+\\$m=[0-9]+,t=[0-9]+,p=(.*?)(?s)")) {
            throw new IllegalStateException("It seems that no argon2 hash is"
                    + " present in the file.");
        }


        /* Deserialize the HashSet object.*/
        ObjectMapper mapper = new ObjectMapper();
        HashSet<User> userDB = mapper.readValue(userDbString,
                new TypeReference<HashSet<User>>() {
        });
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userDB));

        /* Check if the password works for user Alice but for no other users. */
        for (AbstractUser user : userDB) {
            System.out.println("user:" + user.getUsername());
            boolean passwordCorrect = user.verifyPassword(
                    "c4F3w_U6£mkeNygv%&N£fK0wq");
            System.out.println("Testing Password: " + passwordCorrect);

            if (user.getUsername().equals("Alice") && !passwordCorrect) {
                throw new IllegalStateException("ERROR! Password verification "
                        + "failed.");
            }
            if (!user.getUsername().equals("Alice") && passwordCorrect) {
                throw new IllegalStateException("ERROR! Password verification "
                        + "failed.");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        /* Generate some dummy users to test the password hashing.*/
        System.out.println("Constructing and storing dummy users...");
        generateDummyUsers();

        /*Test password hashing.*/
        System.out.println("\nTesting passwords");
        testPasswordsforUsers();

    }
}
