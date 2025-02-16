module MediaPlayerFX {

    requires javafx.fxml;
    requires javafx.controls;


    opens com.example.mediaplayerfx to javafx.fxml;
    exports com.example.mediaplayerfx;
}