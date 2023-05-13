module com.example.hillciphergui {
    requires javafx.controls;
    requires javafx.fxml;


    opens Main to javafx.fxml;
    opens Images to javafx.fxml;
    exports Main;
}