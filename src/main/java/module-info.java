module com.example.javafxtutorial {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;


    opens com.example.javafxtutorial to javafx.fxml;
    exports com.example.javafxtutorial;
}