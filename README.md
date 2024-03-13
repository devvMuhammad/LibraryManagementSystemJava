# Library Management System

This Java application is a simple Library Management System that allows users to manage books and users in a library. The core functionalities include adding new books, adding new users, borrowing books, returning books, searching for books by user ID, and displaying various information about books and users.

## Core Functionalities

### 1. Add New Book
Users can add new books to the library by providing details such as book ID, title, author, and genre. The application generates a random book ID and sets the initial availability status to true.

### 2. Add New User
Users can add new library users by providing details such as user ID, name, contact information, and the ID of the book they want to borrow. The application validates the entered book ID against the available books in the library.

### 3. Borrow Book
This functionality allows users to borrow a book by providing their user ID and the ID of the book they want to borrow. The system updates the availability status of the book and records the borrowed book ID for the user.

### 4. Return Book
Users can return a borrowed book by providing their user ID and the ID of the book they are returning. The system updates the availability status of the book and clears the borrowed book ID for the user.

### 5. Book Search
Users can search for books based on their user ID. The system displays the book details if the user has borrowed a book; otherwise, it notifies the user that no book has been borrowed.

### 6. Display All Books
Users can view details of all books in the library, including book ID, title, author, genre, and availability status.

### 7. Display All Users
Users can view details of all registered library users, including user ID, name, contact information, and the ID of the book they have borrowed.

### 8. Display Only Available Books
This functionality displays only the book IDs of available books in the library.

## Usage
To use the Library Management System, follow these steps:

1. Compile the files using "javc <names of the files>"
2. Run "java Main"
3. Choose from the menu options to perform various library management tasks.

Note: The application uses file-based storage (`BookStorage` and `UserStorage`) to store book and user information.

Feel free to explore and enhance the functionalities of the Library Management System according to your needs.
