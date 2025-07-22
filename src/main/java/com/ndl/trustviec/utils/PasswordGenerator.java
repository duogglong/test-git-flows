package com.ndl.trustviec.utils;

import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {

    // Character sets
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/";

    // Combine all character sets
    private static final String ALL_CHARACTERS = UPPER_CASE + LOWER_CASE + DIGITS + SPECIAL_CHARACTERS;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generatePassword() {
        int length = 8;

        StringBuilder password = new StringBuilder(length);

        // Ensure at least one character from each set
        password.append(randomCharacter(UPPER_CASE));
        password.append(randomCharacter(LOWER_CASE));
        password.append(randomCharacter(DIGITS));
        password.append(randomCharacter(SPECIAL_CHARACTERS));

        // Fill the rest of the password length with random characters from all sets
        for (int i = 4; i < length; i++) {
            password.append(randomCharacter(ALL_CHARACTERS));
        }

        // Shuffle the characters to avoid predictable sequences
        List<Character> passwordChars = new ArrayList<>();
        for (char c : password.toString().toCharArray()) {
            passwordChars.add(c);
        }
        Collections.shuffle(passwordChars);

        // Convert the list back to a string
        StringBuilder shuffledPassword = new StringBuilder();
        for (char c : passwordChars) {
            shuffledPassword.append(c);
        }

        return shuffledPassword.toString();
    }

    private static char randomCharacter(String characters) {
        int index = RANDOM.nextInt(characters.length());
        return characters.charAt(index);
    }

    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(12); // Generate a salt with work factor of 12
        return BCrypt.hashpw(password, salt);
    }
}