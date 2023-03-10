package com.neighborhood.auth.util;

public class PasswordGenerator {
  public static String generatePassword() {
    String password = "";
    String[] characters = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
        "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
        "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
        "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+", "[", "]", "{", "}", "|", ";", ":", "'",
        ",", ".", "<", ">", "/", "?", "~", "`" };

    for (int i = 0; i < 10; i++) {
      int random = (int) (Math.random() * characters.length);
      password += characters[random];
    }

    return password;
  }
}
