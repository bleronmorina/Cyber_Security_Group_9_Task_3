package Main;

import java.util.ArrayList;

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

    public static String decrypt(String ciphertext, ArrayList<ArrayList<Integer>> key) {
        // Convert ciphertext to matrix of numbers
        int[] ciphertextNumbers = new int[ciphertext.length()];
        for (int i = 0; i < ciphertext.length(); i++) {
            ciphertextNumbers[i] = (int) ciphertext.charAt(i) - 65;
        }
        int n = key.size();
        int[][] ciphertextMatrix = new int[n][ciphertextNumbers.length / n];
        int index = 0;
        for (int j = 0; j < ciphertextMatrix[0].length; j++) {
            for (int i = 0; i < ciphertextMatrix.length; i++) {
                ciphertextMatrix[i][j] = ciphertextNumbers[index++];
            }
        }

        // Compute the inverse of the key matrix
        int[][] inverseKey = new int[n][n];
        int det = determinantOfMatrix(key, n);
        int detInverse = findDetInverse(mod26(det), 26);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ArrayList<ArrayList<Integer>> cofactor = getCofactor(key, i, j, n);
                inverseKey[j][i] = mod26((int) (detInverse * Math.pow(-1, i + j) * determinantOfMatrix(cofactor, n - 1)));
            }
        }

        // Multiply inverse key matrix by ciphertext matrix
        int[][] plaintextMatrix = new int[n][ciphertextMatrix[0].length];
        for (int i = 0; i < inverseKey.length; i++) {
            for (int j = 0; j < ciphertextMatrix[0].length; j++) {
                int sum = 0;
                for (int k = 0; k < inverseKey.length; k++) {
                    sum += inverseKey[i][k] * ciphertextMatrix[k][j];
                }
                plaintextMatrix[i][j] = mod26(sum);
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
static int mod26(int x) {
    return x >= 0 ? (x%26) : 26-(Math.abs(x)%26) ;
}

static ArrayList<ArrayList<Integer>> getCofactor(ArrayList<ArrayList<Integer>> mat, int p, int q, int n) {
    int i = 0, j = 0;
    ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();

    for (int row = 0; row < n; row++) {
        if(row != p) {
            ArrayList<Integer> tempRow = new ArrayList<Integer>();
            for (int col = 0; col < n; col++) {
                if(col != q) {
                    tempRow.add(mat.get(row).get(col));
                }
            }
            temp.add(tempRow);
        }
    }
    return temp;
}

}
