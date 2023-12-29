
# Hotel Management System

- [About the project](#intro)
- [Features](#features)
- [Demo](#demo)
- [Installation](#installation)
- [Dependencies](#dependencies)
- [Documentation](#documentation)
    - [Data Base Diagram](#database-diagram)
- [License](#license)
- [Authors](#authors)
- [Attributions](#attributions)

Para ver este archivo en español, haz click [aquí](/README.ESP.md).


## About the project

This project is a desktop application with Data Base connection. It's a hotel management system to make and manage reservations, developed as part of a challenge from the Oracle Next Education program.

I developed the App GUI from scratch using JavaFx and CSS for styling.
## Features

**Login**

When the app starts, a login view is shown requesting administrador credentials such as username and password. These credentials are hardcoded in the database and the passwored is stored as a hash and not in plain text.

**Make a reservation**

To make a reservation, some data is required such as check-in and checkout and payment method.

The total price for the reservation is calculated based on the number of days from check-in to check-out and a daily rate.


**Register guest**

After making a reservation, the register view is shown so the data related to the guest is requested.

**Search guests and reservations**

You can see a table of guests and reservations and search between them by guest's last name or reservation number respectively.

## Demo

### Login

- To login to the app credentials are required.
- The system validates the introduced credentials are in the data base.
- Shows an alert message if the credentials are wrong.
- Shows a home menu if the credentials are correct.

![App Screenshot](/demo/login.gif)

### Make a reservation

- The system asks for check-in and check-out dates.
- Check-in must be before check-out and both must be after the current date.
- The total price is calculated based on the days between both dates and a daily rate.
- There are four payment options to choose using a combo box.
- After making a reservation the reservation number is shown.

![App Screenshot](/demo/reservation.gif)

 ### Register guest

- Guest's information is required and the customer must be 18 years or older.
- The system validates if the phone number is in the right format.
- The system asks for confirmation to cancel the operation or to return to reservation.

![App Screenshot](/demo/register.gif)

### Search guest

- A table of guests is shown and the administrator can edit the guest's information from there or delete the selected register.
- The fields are validated like phone number format or birthdate.
- To look for a guest, the administrator has to introduce the complete or partially guest's last name and a table with the results is displayed.

![App Screenshot](/demo/search-guest.gif)

### Search reservation

- A table with all the reservations in the system is shown and the administrator can edit or delete a selected register.
- Check-in and check-out dates are validated before the update is send to the database.
- A new total price is calculated when the dates are changed.
- To look for a reservation, the administrator provides the reservation number and a result with the same number or results with a similar number are displayed.

![App Screenshot](/demo/search-reservation.gif)

### Logout

- The system asks for confirmation befor logging out.
- The administrator has to introduce the credentials again.

![App Screenshot](/demo/logout.gif)

## Installation

- Clone this repository to your local machine.

```bash
  https://github.com/Diegohrp/challenge-ONE-hotel.git
```

- Make sure you have Java 11 or higher installed and the JDK.

- Open the project in your preferred IDE.

- Build the project using Maven to resolve dependencies.

- Create your local database as shown in the [diagram](#database-diagram)
## Dependencies
- Java 11
- JavaFx for GUI
- MySQL JDBC
- c3p0
- dotenv-java
- bcrypt

## Documentation

### Database Diagram
![App Screenshot](/demo/db.PNG)

## License

This project is under the MIT License

[MIT](https://choosealicense.com/licenses/mit/)


## Authors
This project was developed by [Diego Herrera Prado](https://www.linkedin.com/in/diego-hp/) as part of a challenge from the Oracle Next Education program.

Feel free to reach out if you have any suggestion or if you want to work with me 😀.


## Attributions

- Icons by [Flaticon](https://www.flaticon.es/iconos-gratis)
- Illustrations by [Storyset](https://storyset.com/search?q=user)
- Background image by [Manuel Barros](https://www.pexels.com/es-es/foto/fotografia-aerea-de-tres-personas-en-una-piscina-2403017/)
