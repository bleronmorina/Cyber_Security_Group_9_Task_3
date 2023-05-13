package Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class HillCipherController {

    @FXML
    private Button encryptButton;

    @FXML
    private TextField encryptedTextOutput;

    @FXML
    private TextField matrix00;

    @FXML
    private TextField matrix002;

    @FXML
    private TextField matrix003;

    @FXML
    private TextField matrix01;

    @FXML
    private TextField matrix012;

    @FXML
    private TextField matrix013;

    @FXML
    private TextField matrix022;

    @FXML
    private TextField matrix023;

    @FXML
    private TextField matrix033;

    @FXML
    private TextField matrix10;

    @FXML
    private TextField matrix102;

    @FXML
    private TextField matrix103;

    @FXML
    private TextField matrix11;

    @FXML
    private TextField matrix112;

    @FXML
    private TextField matrix113;

    @FXML
    private TextField matrix122;

    @FXML
    private TextField matrix123;

    @FXML
    private TextField matrix133;

    @FXML
    private TextField matrix202;

    @FXML
    private TextField matrix203;

    @FXML
    private TextField matrix212;

    @FXML
    private TextField matrix213;

    @FXML
    private TextField matrix222;

    @FXML
    private TextField matrix223;

    @FXML
    private TextField matrix233;

    @FXML
    private TextField matrix303;

    @FXML
    private TextField matrix313;

    @FXML
    private TextField matrix323;

    @FXML
    private TextField matrix333;

    @FXML
    private GridPane matrixInput2x2;
    @FXML
    private GridPane matrixInput3x3;
    @FXML
    private GridPane matrixInput4x4;

    @FXML
    private ChoiceBox<Integer> matrixSizeChoiceBox;

    @FXML
    private TextField plainTextInput;


    ObservableList<Integer> sizes = FXCollections.observableArrayList(2, 3, 4);

    public void initialize() {
        matrixSizeChoiceBox.setItems(sizes);
        matrixSizeChoiceBox.setOnAction(e->{
            switch (matrixSizeChoiceBox.getValue()) {
                case 2:
                    setMatrixVisibility(false);
                    matrixInput2x2.setVisible(true);
                    break;
                case 3:
                    setMatrixVisibility(false);
                    matrixInput3x3.setVisible(true);
                    break;
                case 4:
                    setMatrixVisibility(false);
                    matrixInput4x4.setVisible(true);
                    break;
            }
        });
        setMatrixVisibility(false);
    }


    @FXML
    private void handleEncryptButton(ActionEvent event) {
        int[][] matrix = getMatrixFromInput();
        String plainText = plainTextInput.getText();
        String encryptedText = HillCipher.encrypt(plainText, matrix);
        encryptedTextOutput.setText(encryptedText);
    }

    private int[][] getMatrixFromInput() {
        int[][] matrix = null;
        switch (matrixSizeChoiceBox.getValue()) {
            case 2:
                setMatrixVisibility(false);
                matrixInput2x2.setVisible(true);
                matrix = new int[2][2];
                matrix[0][0] = Integer.parseInt(matrix00.getText());
                matrix[0][1] = Integer.parseInt(matrix00.getText());
                matrix[1][0] = Integer.parseInt(matrix01.getText());
                matrix[1][1] = Integer.parseInt(matrix01.getText());
                break;
            case 3:
                matrix = new int[3][3];
                setMatrixVisibility(false);
                matrixInput3x3.setVisible(true);
                matrix[0][0] = Integer.parseInt(matrix002.getText());
                matrix[0][1] = Integer.parseInt(matrix012.getText());
                matrix[0][2] = Integer.parseInt(matrix022.getText());
                matrix[1][0] = Integer.parseInt(matrix102.getText());
                matrix[1][1] = Integer.parseInt(matrix112.getText());
                matrix[1][2] = Integer.parseInt(matrix122.getText());
                matrix[2][0] = Integer.parseInt(matrix202.getText());
                matrix[2][1] = Integer.parseInt(matrix212.getText());
                matrix[2][2] = Integer.parseInt(matrix222.getText());
                break;
            case 4:
                matrix = new int[4][4];
                setMatrixVisibility(false);
                matrixInput4x4.setVisible(true);
                matrix[0][0] = Integer.parseInt(matrix003.getText());
                matrix[0][1] = Integer.parseInt(matrix013.getText());
                matrix[0][2] = Integer.parseInt(matrix023.getText());
                matrix[0][3] = Integer.parseInt(matrix033.getText());
                matrix[1][0] = Integer.parseInt(matrix103.getText());
                matrix[1][1] = Integer.parseInt(matrix113.getText());
                matrix[1][2] = Integer.parseInt(matrix123.getText());
                matrix[1][3] = Integer.parseInt(matrix133.getText());
                matrix[2][0] = Integer.parseInt(matrix203.getText());
                matrix[2][1] = Integer.parseInt(matrix213.getText());
                matrix[2][2] = Integer.parseInt(matrix223.getText());
                matrix[2][3] = Integer.parseInt(matrix233.getText());
                matrix[3][0] = Integer.parseInt(matrix303.getText());
                matrix[3][1] = Integer.parseInt(matrix313.getText());
                matrix[3][2] = Integer.parseInt(matrix323.getText());
                matrix[3][3] = Integer.parseInt(matrix333.getText());
                break;
        }
        return matrix;
    }

    public void setMatrixVisibility(boolean set){
        matrixInput2x2.setVisible(set);
        matrixInput3x3.setVisible(set);
        matrixInput4x4.setVisible(set);
    }


}

