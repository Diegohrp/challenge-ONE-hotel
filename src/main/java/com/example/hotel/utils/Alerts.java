package com.example.hotel.utils;


import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Alerts {

    public static Alert createAlert(String title, String header, String content,
                                    Alert.AlertType type){

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    public static Alert createAlert(String title, String header, String content,
                                    Alert.AlertType type, ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initOwner(stage);
        return alert;
    }

    /* Success alerts */
    public static void successReservationAlert(long num, ActionEvent event){
        createAlert("Reservación exitosa", "El código de la reservación es: " + num,
            "Se ha realizado la reservación de manera exitosa",
            Alert.AlertType.INFORMATION, event).show();

    }

    public static void successRegisterAlert(ActionEvent event){
        createAlert("Registro exitoso", "Se ha realizado el registro de manera exitosa.",
            "Proceso de reservación completado", Alert.AlertType.INFORMATION,
            event).show();

    }

    public static void successUpdateAlert(ActionEvent event){
        createAlert("Operación exitosa", "Registro editado con éxito",
            "Se ha guardado la información que has modificado",
            Alert.AlertType.INFORMATION, event).show();
    }

    public static void successDeleteAlert(ActionEvent event){
        createAlert("Operación exitosa", "Registro eliminado con éxito",
            "Se ha eliminado el registro seleccionado", Alert.AlertType.INFORMATION,
            event).show();
    }


    /* Warning alerts */
    public static Alert goBackAlert(ActionEvent event){
        Alert goBakAlert = createAlert("Regresar",
            "¿Estás seguro de que quieres regresar?",
            "Si regresas, se perderá la información que no hayas guardado.",
            Alert.AlertType.WARNING, event);
        goBakAlert.getButtonTypes().add(ButtonType.CANCEL);
        return goBakAlert;
    }

    public static Alert cancelRegisterAlert(ActionEvent event){
        Alert alert = createAlert("Regresar", "¿Estás seguro de que quieres regresar?",
            "Se perderá la información actual y se eliminará la reservación.",
            Alert.AlertType.WARNING, event);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        return alert;
    }

    /*Confirmation alerts*/
    public static Alert wantToDeleteAlert(ActionEvent event){
        return createAlert("Eliminar registro", "¿Estás seguro de eliminar?",
            "Se borrará la información tanto de la reserva como la del huésped",
            Alert.AlertType.CONFIRMATION, event);
    }


    /* Error alerts */
    public static void wrongIdAlert(ActionEvent event){
        createAlert("Error", "El id debe ser un número",
            "Para buscar entre las reservaciones debes colocar el id",
            Alert.AlertType.ERROR, event).show();
    }

    public static void loginErrorAlert(ActionEvent event){
        createAlert("Error", "Usuario y/o contraseña incorrectos",
            "Por favor, asegúrate de que las credenciales sean correctas y vuelve a " +
                "intentarlo.", Alert.AlertType.ERROR, event).show();
    }

    public static void wrongReservationDataAlert(ActionEvent event){
        createAlert("Error al reservar", "Datos incorrectos",
            "Por favor completa todos los campos de manera correcta",
            Alert.AlertType.ERROR, event).show();
    }

    public static Alert wrongRegisterDataAlert(ActionEvent event){
        return createAlert("Error al registrar al huésped", "Datos incorrectos",
            "Por favor completa todos los campos de manera correcta",
            Alert.AlertType.ERROR, event);
    }

    public static void internalErrorAlert(ActionEvent event){
        createAlert("Algo salió mal", "Hubo un error al realizar la " + "operación",
            "Por favor inténtelo más tarde", Alert.AlertType.ERROR, event).show();
    }

    public static void showNoSelectedFieldAlert(ActionEvent event){
        createAlert("Error", "No has seleccionado ningún registro", null,
            Alert.AlertType.ERROR, event).show();
    }

    public static void wrongDatesAlert(ActionEvent event){
        createAlert("Datos incorrectos", "Error al asignar las fechas",
            "Check-in no debe ser posterior a Chek-out y ambos deben ser " +
                "posteriores a hoy", Alert.AlertType.ERROR, event).show();
    }


    /* Confirmation alerts */
    public static void exitAppAndAlert(Stage stage){
        Alert alert = createAlert("Salir",
            "¿Estás seguro de que deseas salir de la aplicación?",
            "Si sales de la aplicación, perderás todos los cambios no guardados. " +
                "¿Deseas continuar?", Alert.AlertType.CONFIRMATION);
        alert.initOwner(stage);

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }

    public static Alert logoutAlert(ActionEvent event){
        return createAlert("Logout", "¿Estás seguro de que deseas cerrar sesión?",
            "Si cierras sesión, deberás volver a ingresar las credenciales para acceder" +
                " al sistema.", Alert.AlertType.CONFIRMATION, event);
    }
}
