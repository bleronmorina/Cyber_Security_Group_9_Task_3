package Main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SideNavController {

    @FXML
    private VBox navbar;
    private Stage stage;
    private Scene scene;
    private Parent root;

        @FXML
        private void goToDecrypt() throws IOException {
            goToFXML("/Main/HillCipherDecrypt.fxml", (Stage) navbar.getScene().getWindow());

        }

        @FXML
        private void goToEncrypt() throws IOException {
            goToFXML("/Main/HillCipherEncrypt.fxml", (Stage) navbar.getScene().getWindow());
        }



    public static void goToFXML(String location, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(SideNavController.class.getResource(location));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 420);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
