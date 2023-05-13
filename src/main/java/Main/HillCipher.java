package Main;

public class HillCipher {
    public static String encrypt(String plainText, int[][] key) {
        // Remove all non-alphabetic characters and convert to upper case
        plainText = plainText.replaceAll("[^A-Za-z]", "").toUpperCase();

        // Add padding if necessary
        int padding = (key.length - (plainText.length() % key.length)) % key.length;
        if (padding > 0) {
            plainText += "X".repeat(padding);
        }

        // Convert plaintext to matrix of numbers
        int[] plainTextNumbers = new int[plainText.length()];
        for (int i = 0; i < plainText.length(); i++) {
            plainTextNumbers[i] = (int) plainText.charAt(i) - 65;
        }
        int[][] plainTextMatrix = new int[key.length][plainTextNumbers.length / key.length];
        int index = 0;
        for (int j = 0; j < plainTextMatrix[0].length; j++) {
            for (int i = 0; i < plainTextMatrix.length; i++) {
                plainTextMatrix[i][j] = plainTextNumbers[index++];
            }
        }

        // Multiply key matrix by plaintext matrix
        int[][] cipherTextMatrix = new int[key.length][plainTextMatrix[0].length];
        for (int i = 0; i < key.length; i++) {
            for (int j = 0; j < plainTextMatrix[0].length; j++) {
                int sum = 0;
                for (int k = 0; k < key.length; k++) {
                    sum += key[i][k] * plainTextMatrix[k][j];
                }
                cipherTextMatrix[i][j] = sum % 26;
            }
        }

        // Convert cipher text matrix to string
        StringBuilder cipherTextBuilder = new StringBuilder();
        for (int j = 0; j < cipherTextMatrix[0].length; j++) {
            for (int i = 0; i < cipherTextMatrix.length; i++) {
                cipherTextBuilder.append((char) (cipherTextMatrix[i][j] + 65));
            }
        }

        return cipherTextBuilder.toString();
    }
}
