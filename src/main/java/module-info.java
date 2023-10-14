module com.example.hotel {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.hotel to javafx.fxml;
    opens com.example.hotel.controllers to javafx.fxml;
    exports com.example.hotel;
    exports com.example.hotel.controllers;
}