module com.example.javafxtutorial {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.javafxtutorial to javafx.fxml;
    exports com.example.javafxtutorial;
}