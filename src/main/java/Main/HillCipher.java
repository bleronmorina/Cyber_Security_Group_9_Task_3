package Main;

import java.util.Arrays;

public class HillCipher {
    public static String encrypt(String plainText, int[][] key) {
        // Remove all non-alphabetic characters and convert to upper case
        plainText = plainText.replaceAll("[^A-Za-z]", "").toUpperCase();

        // Add padding if necessary
        int padding = (key.length - (plainText.length() % key.length)) % key.length;
        if (padding > 0) {
            plainText += "Y".repeat(padding);
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

    public static String decrypt(String ciphertext, int[][] key) {
        // Convert ciphertext to matrix of numbers
        int[] ciphertextNumbers = new int[ciphertext.length()];
        for (int i = 0; i < ciphertext.length(); i++) {
            ciphertextNumbers[i] = (int) ciphertext.charAt(i) - 65;
        }
        int n = key.length;
        int[][] ciphertextMatrix = new int[n][ciphertextNumbers.length / n];
        int index = 0;
        for (int j = 0; j < ciphertextMatrix[0].length; j++) {
            for (int i = 0; i < ciphertextMatrix.length; i++) {
                ciphertextMatrix[i][j] = ciphertextNumbers[index++];
            }
        }

        // Find the inverse of the key matrix
        int[][] inverseKey = getInverse(key);

        // Multiply inverse key matrix by ciphertext matrix
        int[][] plaintextMatrix = new int[n][ciphertextMatrix[0].length];
        for (int i = 0; i < inverseKey.length; i++) {
            for (int j = 0; j < ciphertextMatrix[0].length; j++) {
                int sum = 0;
                for (int k = 0; k < inverseKey.length; k++) {
                    sum += inverseKey[i][k] * ciphertextMatrix[k][j];
                }
                plaintextMatrix[i][j] = sum % 26;
            }
        }

        // Convert plaintext matrix to string
        StringBuilder plaintextBuilder = new StringBuilder();
        for (int j = 0; j < plaintextMatrix[0].length; j++) {
            for (int i = 0; i < plaintextMatrix.length; i++) {
                plaintextBuilder.append((char) (plaintextMatrix[i][j] + 65));
            }
        }

        // Remove padding if necessary
        int padding = (n - (ciphertext.length() % n)) % n;
        if (padding > 0) {
            plaintextBuilder.delete(plaintextBuilder.length() - padding, plaintextBuilder.length());
        }

        return plaintextBuilder.toString();
    }

    public static int[][] getInverse(int[][] key) {
        int n = key.length;
        int[][] augMatrix = new int[n][2 * n];

        // Create augmented matrix by concatenating identity matrix
        // of same size to the right of key matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augMatrix[i][j] = key[i][j];
            }
            for (int j = n; j < 2 * n; j++) {
                augMatrix[i][j] = (i == (j - n)) ? 1 : 0;
            }
        }

        // Perform row operations to transform left side of augmented
        // matrix into identity matrix and right side into inverse matrix
        for (int i = 0; i < n; i++) {
            // Swap rows to get non-zero pivot
            if (augMatrix[i][i] == 0) {
                for (int j = i + 1; j < n; j++) {
                    if (augMatrix[j][i] != 0) {
                        int[] temp = augMatrix[i];
                        augMatrix[i] = augMatrix[j];
                        augMatrix[j] = temp;
                        break;
                    }
                }
            }
            // Scale row to make pivot equal to 1
            int pivot = augMatrix[i][i];
            for (int j = i; j < 2 * n; j++) {
                augMatrix[i][j] = (augMatrix[i][j] * modInverse(pivot, 26)) % 26;
            }
            // Use row operations to eliminate other elements in column
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                int factor = augMatrix[j][i];
                for (int k = i; k < 2 * n; k++) {
                    augMatrix[j][k] = (augMatrix[j][k] - factor * augMatrix[i][k] + 26) % 26;
                }
            }
        }

        // Extract right side of augmented matrix as inverse matrix
        int[][] inverse = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = n; j < 2 * n; j++) {
                inverse[i][j - n] = augMatrix[i][j];
            }
        }
        return inverse;
    }

    // Helper function to compute modular inverse of a number
    public static int modInverse(int a, int m) {
        int m0 = m;
        int y = 0, x = 1;
        if (m == 1) {
            return 0;
        }
        while (a > 1) {
            int q = a / m;
            int t = m;
            m = a % m;
            a = t;
            t = y;
            y = x - q * y;
            x = t;
        }
        if (x < 0) {
            x += m0;
        }
        return x;
    }


}