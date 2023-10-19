module com.example.hotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires c3p0;
    requires io.github.cdimascio.dotenv.java;
    requires bcrypt;
    requires java.naming;
            
                            
    opens com.example.hotel to javafx.fxml;
    opens com.example.hotel.controllers to javafx.fxml;
    exports com.example.hotel;
    exports com.example.hotel.controllers;
}