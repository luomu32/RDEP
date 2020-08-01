package xyz.luomu32.rdep.user.service.impl;

import java.util.Random;

public class PasswordGenerator {

    private static final char[] letter = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'r', 'l', 'o', 'p', 'q', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G'};
    private static final char[] number = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private Random random = new Random();

    public String generate(int length, int numberLength) {

        char[] password = new char[length];

        for (int i = 0; i < numberLength; i++) {
            password[i] = number[random.nextInt(number.length)];
        }
        for (int i = numberLength; i < length; i++) {
            password[i] = letter[random.nextInt(letter.length)];
        }
        return String.valueOf(password);
    }
}
