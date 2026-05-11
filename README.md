# Library Management Software

A robust, console-based Library Management System written in Java. This application allows users to manage books and library patrons, facilitating operations such as adding, updating, deleting, searching, 
and handling the check-out and return processes of books.

## Features
* **Book Management:** Add, update, delete, and list books. Advanced search capabilities using Lambdas (Search by Title, Author, or Status).
* **Patron Management:** Register, update, delete, and list library patrons.
* **Library Operations:** Secure check-out and return system. Prevents double-checkouts and automatically handles inventory logic.
* **Data Persistence:** All data is safely stored locally via JSON (`books.json` and `patrons.json`). The application uses an in-memory caching mechanism for fast reads and syncs data to the disk upon changes or safe application shutdown.
* **Relational Safety:** Deleting a patron automatically returns their checked-out books to the library pool. Deleting a book removes it from the patron's current inventory.

## Technical Stack & Design Decisions
* **Language:** Java
* **Libraries Used:**
  * `Gson` (for JSON Serialization/Deserialization)
  * `Lombok` (to reduce boilerplate code like Getters, Setters, and Constructors)
* **Architecture:** Divided into Models, Repositories (Data Layer), Services (Business Logic), and Console/Menus (UI Layer).

## Prerequisites
* Java Development Kit (JDK) 17 or higher installed.
* Maven or Gradle (if used for dependency management to fetch Gson and Lombok), or ensure the `.jar` files are in your classpath.

## Compilation and Execution Instructions

**Using an IDE (Recommended due to dependencies):**
1. Open the project folder in IntelliJ IDEA, Eclipse, or VS Code.
2. Ensure Maven/Gradle has downloaded the `Gson` and `Lombok` dependencies.
3. Locate `src/main/java/org/pytenix/Main.java`.
4. Run the `main` method.

**Using Command Line (Standard Java):**
*(Note: Replace the classpath separator `:` with `;` if you are on Windows)*
1. Navigate to the project root directory.
2. Compile the source code (assuming dependencies are in a `lib` folder):
   ```bash
   javac -cp "lib/*" -d bin src/main/java/org/pytenix/**/*.java
