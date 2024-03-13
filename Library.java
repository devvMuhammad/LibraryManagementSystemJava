package LibrarySystem;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.io.BufferedWriter;
import java.util.Random;

public class Library {
    Scanner sc = new Scanner(System.in);
    Random random = new Random();
    static int book = 0;
    static int user = 0;
    static int bookAdded = 0;
    static ArrayList<Book> bookCollection = new ArrayList<>();
    static ArrayList<User> userCollection = new ArrayList<>();

    void addNewBook() {
        Book newBook = new Book();
        while (true) {
            try {
                System.out.println("Please enter the id of the book!");
                int randomnum = random.nextInt(sc.nextInt());
                newBook.bookID = randomnum;
                System.out.println("Please enter the title of the book!");
                newBook.title = sc.next();
                System.out.println("Please enter the author name!");
                newBook.author = sc.next();
                System.out.println("Please enter the genre of the book!");
                newBook.genre = sc.next();
                newBook.availabilityStatus = true;
                break;
            } catch (Exception e) {
                System.out.println("Invalid Input! Try again");
                sc.next();
                continue;
            }
        }
        bookCollection.add(newBook);
        bookFileWrite("BookStorage", newBook);
        book++;
    }

    void addNewUser() {
        User newUser = new User();
        int randomnum;
        int borrowidtemp;
        boolean flag = false;
        while (true) {
            try {
                System.out.println("Please enter the id of the user!");
                randomnum = random.nextInt(sc.nextInt());
                newUser.userID = randomnum;
                System.out.println("Please enter the name of the user!");
                newUser.name = sc.next();
                System.out.println("Please enter the contact information of the user!");
                newUser.contactInformation = sc.next();
                System.out.println("Please enter the id of the borrowed book!");
                borrowidtemp = sc.nextInt();
                fileReadBook();
                for (Book book : bookCollection) {
                    if (borrowidtemp == book.bookID) {
                        newUser.borrowedBooksID = borrowidtemp;
                        flag = true;
                        break;
                    } else {
                        continue;
                    }
                }
                if (!flag) {
                    System.out.println("\n!!!!Borrowed book Id not found in the book storage! Try again!!!!\n");
                    continue;
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Try again");
                sc.next();
                continue;
            }
        }
        userCollection.add(newUser);
        userFileWrite("UserStorage", newUser);
        user++;
    }

    static void displayBooks(final int a) {
        boolean flag = false;
        for (Book book : bookCollection) {
            if (book.bookID == a) {
                if (book.availabilityStatus == true) {
                    System.out.println("Book Id\t\tBook Title\tBook Author\tBook Genre\tBook Availability Status");
                    System.out.printf("%d\t\t%s\t\t%s\t\t%s\t\t%b", book.bookID, book.title, book.author, book.genre,
                            book.availabilityStatus);
                    flag = true;
                    break;
                } else {
                    System.out.println("Sorry the book has already been borrowed!");
                    flag = true;
                }
            } else {
                continue;
            }
        }
        if (!flag) {
            System.out.println("Book not found!");
        }
    }

    void borrowBook(int uID, int bID) {
        try (RandomAccessFile reader = new RandomAccessFile("BookStorage", "r")) {
            String line;
            int i = 1;
            while ((line = reader.readLine()) != null) {
                if (i == 1 || i % 7 == 0) {
                    if (bID == Integer.parseInt(line)) {
                        try {
                            RandomAccessFile randomAccessFile = new RandomAccessFile("BookStorage", "rw");
                            randomAccessFile.seek(0);
                            for (int j = 1; j <= i + 3; j++) {
                                randomAccessFile.readLine();
                            }
                            randomAccessFile.writeBytes("0");
                            randomAccessFile.seek(0);
                            randomAccessFile.close();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }
                i++;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        try (RandomAccessFile reader = new RandomAccessFile("UserStorage", "r")) {
            String line;
            int i = 1;
            while ((line = reader.readLine()) != null) {
                if (i == 1 || i % 6 == 0) {
                    if (uID == Integer.parseInt(line)) {
                        try {
                            RandomAccessFile randomAccessFile = new RandomAccessFile("UserStorage", "rw");
                            randomAccessFile.seek(0);
                            for (int j = 1; j <= 2 + i; j++) {
                                randomAccessFile.readLine();
                            }
                            randomAccessFile.writeBytes(Integer.toString(bID));
                            randomAccessFile.seek(0);
                            randomAccessFile.close();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    void bookReturn(int uId, int bId) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("UserStorage", "rw");
            String line;
            int i = 1;
            while ((line = randomAccessFile.readLine()) != null) {
                if (i == 1 || i % 6 == 0) {
                    if (uId == Integer.parseInt(line)) {
                        randomAccessFile.seek(0);
                        for (int j = 1; j <= 2 + i; j++) {
                            randomAccessFile.readLine();
                        }
                        // int length;
                        // randomAccessFile.writeBytes(new String(new char[k]).replace('\0', ' '));
                        randomAccessFile.writeBytes("0000");
                        randomAccessFile.seek(0);
                        randomAccessFile.close();
                    }
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("BookStorage", "rw");
            String line;
            int i = 1;
            while ((line = randomAccessFile.readLine()) != null) {
                if (i == 1 || i % 7 == 0) {
                    if (bId == Integer.parseInt(line)) {
                        randomAccessFile.seek(0);
                        for (int j = 1; j <= i + 3; j++) {
                            randomAccessFile.readLine();
                        }
                        randomAccessFile.writeBytes("1");
                        randomAccessFile.seek(0);
                        randomAccessFile.close();
                    }
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void bookSearch() {

        System.out.println("Please enter the User Id!");
        int a = sc.nextInt();
        for (User user : userCollection) {
            if (user.userID == a) {
                if (user.borrowedBooksID != 0) {
                    for (Book book : bookCollection) {
                        if (book.bookID == user.borrowedBooksID) {
                            System.out.println("The book borrowed by this user is: ");
                            System.out.println();
                            System.out.println(
                                    "Book Id\t\tBook Title\tBook Author\tBook Genre\tBook Availability Status");
                            System.out.printf("%d\t\t%s\t\t%s\t\t%s\t\t%b", book.bookID, book.title, book.author,
                                    book.genre, book.availabilityStatus);
                            break;
                        }
                    }
                    break;
                } else {
                    System.out.println("No book has been borrowed by this user!");
                }
            } else {
                System.out.println("Such a user does not exist in our database!");
            }
        }
    }

    static void fileCreation(String filename) {
        try {
            File file = new File(filename);
            if (file.createNewFile()) {
                System.out.println("New file created!");
            } else {
                System.out.println("File already exists!");
            }

        } catch (Exception e) {
            System.out.println("Could not create the file");
        }
    }

    static void fileReadBook() {
        String a = null, y = null;
        String b = null, c = null, d = null;
        boolean x = false;

        try (RandomAccessFile reader = new RandomAccessFile("BookStorage", "r")) {
            String line;
            int i = 1, n = 0;
            while ((line = reader.readLine()) != null) {
                if (i % 6 == 0) {
                    Book newBook = new Book();
                    newBook.bookID = Integer.valueOf(a);
                    newBook.title = b;
                    newBook.author = c;
                    newBook.genre = d;
                    if (y.equals("0")) {
                        x = false;
                    } else if (y.equals("1")) {
                        x = true;
                    }
                    newBook.availabilityStatus = x;
                    bookCollection.add(newBook);
                    n += 6;
                } else if (i == 1 + n) {
                    a = line;
                } else if (i == 2 + n) {
                    b = line;
                } else if (i == 3 + n) {
                    c = line;
                } else if (i == 4 + n) {
                    d = line;
                } else if (i == 5 + n) {
                    y = line;
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void bookFileWrite(String filename, Book object) {
        Library.fileCreation(filename);
        String a = null, y = null;
        String b = null, c = null, d = null;
        boolean x = false;
        if (object.getClass() == Book.class) {
            a = Integer.toString(object.bookID);
            b = object.title;
            c = object.author;
            d = object.genre;
            x = object.availabilityStatus;
            if (x == true) {
                y = "1";
            } else {
                y = "0";
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(filename, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(a);
            writer.newLine();
            writer.write(b);
            writer.newLine();
            writer.write(c);
            writer.newLine();
            writer.write(d);
            writer.newLine();
            writer.write(y);
            writer.newLine();
            writer.write("-");
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            System.out.println("Could not write to the file");
        }
    }

    static void displayAllBooks() {
        for (Book bookthis : bookCollection) {
            System.out.println(bookthis.bookID);
            System.out.println(bookthis.author);
            System.out.println(bookthis.title);
            System.out.println(bookthis.genre);
            System.out.println(bookthis.availabilityStatus);
        }
    }

    static void userFileWrite(String filename, User object) {
        Library.fileCreation(filename);
        String a = null, b = null, c = null, d = null;
        if (object.getClass() == User.class) {
            a = Integer.toString(object.userID);
            b = object.name;
            c = object.contactInformation;
            d = Integer.toString(object.borrowedBooksID);
        }
        try {
            FileWriter fileWriter = new FileWriter(filename, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(a);
            writer.newLine();
            writer.write(b);
            writer.newLine();
            writer.write(c);
            writer.newLine();
            writer.write(d);
            writer.newLine();
            writer.write("-");
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            System.out.println("Could not write to the file");
        }
    }

    static void fileReadUser() {
        String a = null, b = null, c = null, d = null;
        try (RandomAccessFile reader = new RandomAccessFile("UserStorage", "r")) {
            String line;
            int i = 1, n = 0;
            while ((line = reader.readLine()) != null) {
                if (i % 5 == 0) {
                    User newUser = new User();
                    newUser.userID = Integer.valueOf(a);
                    newUser.name = b;
                    newUser.contactInformation = c;
                    newUser.borrowedBooksID = Integer.valueOf(d);
                    userCollection.add(newUser);
                    n += 5;
                } else if (i == 1 + n) {
                    a = line;
                } else if (i == 2 + n) {
                    b = line;
                } else if (i == 3 + n) {
                    c = line;
                } else if (i == 4 + n) {
                    d = line;
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void displayAllUsers() {
        for (User users : userCollection) {
            System.out.println("User Id is " + users.userID);
            System.out.println("User name is " + users.name);
            System.out.println("User contact info is " + users.contactInformation);
            System.out.println("User has borrowed the book with the id " + users.borrowedBooksID);
        }
    }

    static void displayOnlyIdsOfBooks() {
        bookCollection.clear();
        Library.fileReadBook();
        System.out.println("The books that are available are: ");
        for (Book book : bookCollection) {
            if (book.availabilityStatus != false) {
                System.out.println(book.bookID);
            }
        }
    }

    static void displayOnlyIdsOfUsers() {
        userCollection.clear();
        Library.fileReadUser();
        System.out.println("The users that are registered are: ");
        for (User user : userCollection) {
            System.out.println(user.userID);
        }
    }
}