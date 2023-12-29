# Sistema de Gestión Hotelera

- [Acerca del proyecto](#acerca-del-proyecto)
- [Características](#caractersticas)
- [Demo](#demo)
- [Instalación](#instalacin)
- [Dependencias](#dependencias)
- [Documentación](#documentacin)
    - [Diagrama de la Base de Datos](#diagrama-de-la-base-de-datos)
- [Licencia](#dependencias)
- [Autores](#autores)
- [Atribuciones](#atribuciones)

## Acerca del proyecto

Este proyecto es una aplicación de escritorio con conexión a la base de datos. Es un
sistema de gestión hotelera para hacer y administrar reservas, desarrollado como parte de
un desafío del programa Oracle Next Education.

Desarrollé la interfaz gráfica de la aplicación desde cero utilizando JavaFx y CSS
para los estilos.

## Características

**Inicio de Sesión**

Cuando la aplicación se inicia, se muestra una vista de inicio de sesión que solicita 
credenciales de administrador, como nombre de usuario y contraseña. Estas credenciales 
están escritas directamente en la base de datos y la contraseña se almacena como hash, 
no en texto plano.

**Hacer una Reserva**

Para hacer una reserva, se requieren algunos datos, como fecha de check-in y check-out y método de pago.

El precio total de la reserva se calcula en función del número de días desde el check-in hasta el check-out y una tarifa diaria.

**Registrar Huésped**

Después de hacer una reserva, se muestra la vista de registro para solicitar los datos relacionados con el huésped.

**Buscar Huéspedes y Reservas**

Puedes ver una tabla de huéspedes y reservas y buscar entre ellas por el apellido del 
huésped o el número de reserva, respectivamente.

## Demo

### Inicio de Sesión

- Para iniciar sesión en la aplicación se requieren credenciales.
- El sistema valida que las credenciales introducidas estén en la base de datos.
- Muestra un mensaje de alerta si las credenciales son incorrectas.
- Muestra un menú principal si las credenciales son correctas.

![App Screenshot](/demo/login.gif)

### Hacer una Reserva

- El sistema solicita las fechas de check-in y check-out.
- El check-in debe ser antes del check-out y ambos deben ser después de la fecha actual.
- El precio total se calcula en función de los días entre ambas fechas y una tarifa 
  diaria.
- Hay cuatro opciones de pago para elegir utilizando un cuadro combinado.
- Después de hacer una reserva, se muestra el número de reserva.

![App Screenshot](/demo/reservation.gif)

### Registrar Huésped

- Se requiere información del huésped y el cliente debe tener 18 años o más.
- El sistema valida si el número de teléfono está en el formato correcto.
- El sistema solicita confirmación para cancelar la operación o para regresar a la 
  reserva.

![App Screenshot](/demo/register.gif)

### Buscar Huésped

- Se muestra una tabla de huéspedes y el administrador puede editar la información del 
huésped desde allí o eliminar el registro seleccionado.
- Se validan campos como el formato del número de teléfono o la fecha de nacimiento.
- Para buscar un huésped, el administrador debe introducir el apellido completo o 
  parcial del huésped y se muestra una tabla con los resultados.

![App Screenshot](/demo/search-guest.gif)

### Buscar Reserva

- Se muestra una tabla con todas las reservas en el sistema y el administrador puede 
editar o eliminar un registro seleccionado.
- Las fechas de check-in y check-out se validan antes de enviar la actualización a la 
  base de datos.
- Se calcula un nuevo precio total cuando se cambian las fechas.
- Para buscar una reserva, el administrador proporciona el número de reserva y se 
  muestra un resultado con el mismo número o resultados con un número similar.

![App Screenshot](/demo/search-reservation.gif)

**Cerrar Sesión**

- El sistema solicita confirmación antes de cerrar sesión.
- El administrador debe introducir las credenciales nuevamente.

![App Screenshot](/demo/logout.gif)

## Instalación

- Clona este repositorio en tu máquina local.

```bash
  https://github.com/Diegohrp/challenge-ONE-hotel.git
```

- Asegúrate de tener Java 11 o superior instalado y el JDK.

- Abre el proyecto en tu IDE preferido.

- Compila el proyecto utilizando Maven para resolver las dependencias.

- Crea tu base de datos local como se muestra en el [diagrama](#database-diagram).

## Dependencias

- Java 11
- JavaFx for GUI
- MySQL JDBC
- c3p0
- dotenv-java
- bcrypt

## Documentación

### Diagrama de la base de datos

![App Screenshot](/demo/db.PNG)

## Licencia

Este proyecto está bajo la Licencia MIT

[MIT](https://choosealicense.com/licenses/mit/)

## Autores

Este proyecto fue desarrollado por [Diego Herrera Prado](https://www.linkedin.com/in/diego-hp/) como parte de un desafío del programa Oracle Next Education.

No dudes en ponerte en contacto si tienes alguna sugerencia o si quieres trabajar conmigo 😀.


## Atribuciones

- Iconos de [Flaticon](https://www.flaticon.es/iconos-gratis)
- Ilustraciones de [Storyset](https://storyset.com/search?q=user)
- Imagen de fondo de [Manuel Barros](https://www.pexels.com)
