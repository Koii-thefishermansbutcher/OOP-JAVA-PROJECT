# Animal Shelter Management System v2.7

A beginner-friendly Java console application for managing animal shelter records using Java, JDBC, and MySQL.

## 1. About the Project

The Animal Shelter Management System is designed to help shelter staff manage animal information and adoption records through a menu-based system.

The application uses Java for its main operations and MySQL for storing information.

### Main Features

- Staff Login and Logout
- Register New Animals
- View the Shelter Directory
- Process Animal Adoptions
- Update Animal Profiles
- Search Animals by ID or Name
- View Adoption History
- View Shelter Statistics
- Store and retrieve records using MySQL

## 2. Technologies Used

| Technology | Purpose |
|---|---|
| Java | Main programming language |
| MySQL | Database management |
| JDBC | Connects Java to MySQL |
| MySQL Connector/J | JDBC driver |
| MySQL Workbench | Manage and view the database |
| XAMPP | Provides a local MySQL server |
| Git and GitHub | Version control and project hosting |

## 3. Project Files

| File | Description |
|---|---|
| `App.java` | Main program, login flow, and menu |
| `Login.java` | Checks staff login credentials |
| `Database.java` | Handles the MySQL connection |
| `AnimalAction.java` | Shared methods and abstract parent class |
| `Register.java` | Registers new animals |
| `View.java` | Displays animal records |
| `Adoption.java` | Processes animal adoptions |
| `Update.java` | Updates animal information |
| `SearchAnimals.java` | Searches animal records |
| `AdoptionHistory.java` | Displays adoption records |
| `ShelterStatistics.java` | Displays shelter statistics |
| `staff_login_setup.sql` | Creates the staff login table |
| `animal_shelter_dates_update.sql` | Adds date columns to an existing database |

**Database note:** The complete database creation script should also be included in the GitHub repository. It creates the initial `animal_info` and `adopter_info` tables. The date-update script is only for an older database that does not yet have the required date columns.

## 4. Requirements Before Installation

Install the following:

1. Java Development Kit (JDK).
2. MySQL Server.
3. MySQL Workbench (recommended for database management).
4. MySQL Connector/J.
5. A Java IDE or editor, such as NetBeans, IntelliJ IDEA, Eclipse, or VS Code with Java support.

You may also use XAMPP to run the local MySQL server.

**Important:** MySQL Workbench and XAMPP have different purposes. MySQL Workbench is a database management application. XAMPP provides the local MySQL server used by the Java program.

## 5. Database Setup Using MySQL Workbench

### Step 1: Start MySQL Server

Make sure your MySQL Server is running.

If you use XAMPP, open the XAMPP Control Panel and start MySQL.

If you installed MySQL Server separately, make sure its service is running.

### Step 2: Open MySQL Workbench

1. Open MySQL Workbench.
2. Connect to your local MySQL Server.
3. Open a new SQL tab.

### Step 3: Create the Database

Run the complete database creation SQL script included in the repository.

The database name must be:

`animal_shelter_managementsystem`

The initial database should contain these tables:

- `animal_info`
- `adopter_info`

### Step 4: Add the Required Date Columns

If you are updating an older database that does not have the date columns, run:

`animal_shelter_dates_update.sql`

Run this migration only once on a database that is missing those columns. Do not run it again if the columns already exist.

### Step 5: Set Up Staff Login

Open and execute:

`staff_login_setup.sql`

This creates the `staff_info` table and inserts the starter administrator account.

### Step 6: Check the Database

Refresh the Schemas panel in MySQL Workbench.

Expand:

`animal_shelter_managementsystem`

Check that the required tables are visible.

## 6. Java Database Connection Setup

Open `Database.java`.

The default connection settings are:

```java
private String url =
    "jdbc:mysql://localhost:3306/animal_shelter_managementsystem";

private String user = "root";
private String password = "";
```

These settings assume a local MySQL server using:

- Host: `localhost`
- Port: `3306`
- Database: `animal_shelter_managementsystem`
- Username: `root`
- Password: blank

If your MySQL installation uses a different username, password, or port, update the connection settings.

**Security warning:** Do not upload real database passwords or private credentials to GitHub.

## 7. MySQL Connector/J Setup

Java needs MySQL Connector/J to communicate with MySQL.

1. Download MySQL Connector/J from the official MySQL website.
2. Extract the downloaded package if necessary.
3. Add the Connector/J JAR file to your Java project's libraries or classpath.
4. Make sure the driver is available when running `App.java`.

Official download:

https://dev.mysql.com/downloads/connector/j/

Without the JDBC driver, the application may fail to connect to MySQL.

## 8. How to Run the Program

1. Start your MySQL Server.
2. Open the Java project in your preferred IDE.
3. Confirm that all Java files are in the same project.
4. Add MySQL Connector/J.
5. Check the database settings in `Database.java`.
6. Run `App.java`.

The program should display the staff login screen.

### Starter Administrator Account

| Field | Value |
|---|---|
| Username | `admin` |
| Password | `shelter123` |

These are starter classroom credentials. Change the password before using real shelter records.

### Login and Logout Behavior

1. The program starts with the staff login screen.
2. Correct credentials allow access to the main menu.
3. Incorrect credentials are rejected.
4. Menu option 8 logs out and returns to the login screen.
5. Menu option 9 shuts down the application.
6. Entering `0` at the username prompt exits before login.

Logging out does not delete animal or adopter records.

## 9. Object-Oriented Programming Concepts

The project demonstrates the four main OOP concepts:

### Encapsulation
Database connection information is kept inside the `Database` class using private fields.

### Inheritance
Classes reuse functionality from parent classes, such as `AnimalAction` and `Database`.

### Abstraction
`AnimalAction` is an abstract class that provides shared behavior for animal operations.

### Polymorphism
Different operation classes provide their own implementation of the `run()` method.

## 10. Database and Security Information

The application uses JDBC to communicate with MySQL.

It uses:

- `Connection` to establish a database connection.
- `PreparedStatement` to execute parameterized SQL queries.
- `ResultSet` to read data returned by SELECT statements.

The login system uses MySQL `SHA2()` to compare the entered password with its stored hash.

**Security limitation:** SHA-256 by itself is not recommended for production password storage. A real deployment should use a password-specific hashing algorithm such as Argon2id or bcrypt, along with appropriate account-management and access-control features.

This project is intended as an educational demonstration, not a production-ready shelter administration system.

## 11. Troubleshooting

### Problem: Unable to connect to MySQL

Check:
- Is MySQL Server running?
- Is the database name correct?
- Is the port correct?
- Are the username and password correct?
- Is MySQL Connector/J added to the Java project?

### Problem: Login does not work

Check:
- Has `staff_login_setup.sql` been executed?
- Does the `staff_info` table exist?
- Are the username and password correct?
- Is the application connected to the correct database?

### Problem: Table does not exist

Make sure the complete database creation SQL script has been executed before running the Java application.

### Problem: Unknown column or missing date column

Check whether the database has the required `accepted_date` and `adopted_date` columns.

For an older database, run the date migration script once.

## 12. GitHub Installation

To download the project:

1. Open the repository on GitHub.
2. Click the green **Code** button.
3. Select **Download ZIP**.
4. Extract the project folder.
5. Follow the database and Java setup instructions above.

Alternatively, use Git:

```bash
git clone YOUR_REPOSITORY_URL
```

Replace `YOUR_REPOSITORY_URL` with the actual GitHub repository URL.

## 13. Licensing

**License: Not yet selected.**

Unless a license is added to this repository, the project remains under its author's default copyright. Other users should not assume they have permission to redistribute, modify, or use it beyond what applicable law allows.

For a student project that you want others to learn from, consider the MIT License.

The MIT License allows people to use, copy, modify, merge, publish, distribute, sublicense, and sell copies of the software, provided they retain the copyright and license notice.

Before choosing a license:

- Make sure you have permission to license all code included in the repository.
- Check the licenses of third-party libraries, including MySQL Connector/J.
- Add a `LICENSE` file to the repository if you decide to use one.
- Replace the copyright holder placeholder with the appropriate author or project owner.

Official MIT License information:

https://opensource.org/license/mit

## 14. Project Limitations

- This is a Java console-based application.
- It requires a working MySQL database connection.
- The starter administrator account is intended for initial testing.
- Staff account management is not included.
- The login implementation is educational and should be strengthened before real-world deployment.
- The application has not been validated as a production system.

## 15. Project Purpose

This project was developed as an educational application to demonstrate Java programming, object-oriented programming, JDBC database connectivity, SQL operations, and basic staff access control.

The goal is to provide a simple example of how a Java application can manage animal shelter records using a MySQL database.

---

**Animal Shelter Management System v2.7**  
Java | JDBC | MySQL  
Educational Project
