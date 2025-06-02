package com.example.bookjournal;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.bookjournal.Models.Books;
import com.example.bookjournal.Models.MyBooks;
import com.example.bookjournal.Models.User;
import com.example.bookjournal.Models.Event;
import com.example.bookjournal.Models.EventParticipant;

public class DatabaseHelper extends SQLiteOpenHelper{

    // Database Name and Version
    private static final String DATABASE_NAME = "BookJournal.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_BOOKS = "books";
    private static final String TABLE_MYBOOKS = "mybooks";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_EVENT = "event";
    private static final String TABLE_EVENTPARTICIPANT = "eventparticipant";



    // Books Table Columns
    private static final String COLUMN_BOOKS_ID = "id";
    private static final String COLUMN_BOOKS_TITLE = "title";
    private static final String COLUMN_BOOKS_AUTHOR = "author";
    private static final String COLUMN_BOOKS_GENRE = "genre";
    private static final String COLUMN_BOOKS_PUBLISHER = "publisher";
    private static final String COLUMN_BOOKS_RATING = "rating";
    private static final String COLUMN_BOOKS_ADDED = "added"; // Boolean (0 or 1)

    // MyBooks Table Columns
    private static final String COLUMN_MYBOOKS_ID = "id";
    private static final String COLUMN_MYBOOKS_BOOK_ID = "book_id"; // Foreign key to Books
    private static final String COLUMN_MYBOOKS_USER_ID = "user_id"; // Foreign key to Users
    private static final String COLUMN_MYBOOKS_TITLE = "title";
    private static final String COLUMN_MYBOOKS_AUTHOR = "author";
    private static final String COLUMN_MYBOOKS_PUBLISHER = "publisher";
    private static final String COLUMN_MYBOOKS_GLOBAL_RATING = "globalRating";
    private static final String COLUMN_MYBOOKS_MY_RATING = "myRating";
    private static final String COLUMN_MYBOOKS_LIKES = "likes";
    private static final String COLUMN_MYBOOKS_DATE_ADDED = "dateAdded";
    private static final String COLUMN_MYBOOKS_REVIEW = "review";
    private static final String COLUMN_MYBOOKS_IS_READ = "isRead";
    private static final String COLUMN_MYBOOKS_IS_BORROWED = "isBorrowed";
    private static final String COLUMN_MYBOOKS_IS_MINE_BORROWED = "isMineBorrowed";
    private static final String COLUMN_MYBOOKS_IS_FAVE = "isFave";
    private static final String COLUMN_MYBOOKS_CURRENTLY_READING = "currentlyReading";
    private static final String COLUMN_MYBOOKS_WANT_TO_READ = "wantToRead";

    // Users Table Columns
    private static final String COLUMN_USERS_ID = "id";
    private static final String COLUMN_USERS_USER_ID = "UserId";

    private static final String COLUMN_USERS_FULL_NAME = "fullName";
    private static final String COLUMN_USERS_AGE = "age";
    // Events Table Columns
    private static final String COLUMN_EVENT_ID = "id";
    private static final String COLUMN_EVENT_ORGANIZER_ID = "organizer_id";
    private static final String COLUMN_EVENT_PLACE = "place";
    private static final String COLUMN_EVENT_DATE_TIME = "dateTime";
    private static final String COLUMN_EVENT_LAT_START = "latStart";
    private static final String COLUMN_EVENT_LON_START = "lonStart";

    // EventParticipant Table Columns
    private static final String COLUMN_EVENT_PARTICIPANT_ID = "id";
    private static final String COLUMN_EVENT_PARTICIPANT_USER_ID = "user_id";
    private static final String COLUMN_EVENT_PARTICIPANT_EVENT_ID = "event_id";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Books table
        db.execSQL("CREATE TABLE " + TABLE_BOOKS + " (" +
                COLUMN_BOOKS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOOKS_TITLE + " TEXT, " +
                COLUMN_BOOKS_AUTHOR + " TEXT, " +
                COLUMN_BOOKS_GENRE + " TEXT, " +
                COLUMN_BOOKS_PUBLISHER + " TEXT, " +
                COLUMN_BOOKS_RATING + " FLOAT, " +
                COLUMN_BOOKS_ADDED + " INTEGER)");

        // Create MyBooks table
        db.execSQL("CREATE TABLE " + TABLE_MYBOOKS + " (" +
                COLUMN_MYBOOKS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MYBOOKS_BOOK_ID + " INTEGER, " +
                COLUMN_MYBOOKS_USER_ID + " INTEGER, " +
                COLUMN_MYBOOKS_TITLE + " TEXT, " +
                COLUMN_MYBOOKS_AUTHOR + " TEXT, " +
                COLUMN_MYBOOKS_PUBLISHER + " TEXT, " +
                COLUMN_MYBOOKS_GLOBAL_RATING + " FLOAT, " +
                COLUMN_MYBOOKS_MY_RATING + " FLOAT, " +
                COLUMN_MYBOOKS_LIKES + " INTEGER, " +
                COLUMN_MYBOOKS_DATE_ADDED + " DATE, " +
                COLUMN_MYBOOKS_REVIEW + " TEXT, " +
                COLUMN_MYBOOKS_IS_READ + " INTEGER, " +
                COLUMN_MYBOOKS_IS_BORROWED + " INTEGER, " +
                COLUMN_MYBOOKS_IS_MINE_BORROWED + " INTEGER, " +
                COLUMN_MYBOOKS_IS_FAVE + " INTEGER, " +
                COLUMN_MYBOOKS_CURRENTLY_READING + " INTEGER, " +
                COLUMN_MYBOOKS_WANT_TO_READ + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_MYBOOKS_BOOK_ID + ") REFERENCES " + TABLE_BOOKS + "(" + COLUMN_BOOKS_ID + "), " +
                "FOREIGN KEY(" + COLUMN_MYBOOKS_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USERS_ID + "))");

        // Create Users table
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERS_USER_ID + " TEXT UNIQUE, " +
                COLUMN_USERS_FULL_NAME + " TEXT, " +
                COLUMN_USERS_AGE + " INTEGER)");

        // Create Event table
        db.execSQL("CREATE TABLE " + TABLE_EVENT + " (" +
                COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EVENT_ORGANIZER_ID + " INTEGER, " +
                COLUMN_EVENT_PLACE + " TEXT, " +
                COLUMN_EVENT_DATE_TIME + " TEXT, " +
                COLUMN_EVENT_LAT_START + " DOUBLE, " +
                COLUMN_EVENT_LON_START + " DOUBLE, " +
                "FOREIGN KEY(" + COLUMN_EVENT_ORGANIZER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USERS_ID + "))");

        // Create EventParticipant table
        db.execSQL("CREATE TABLE " + TABLE_EVENTPARTICIPANT + " (" +
                COLUMN_EVENT_PARTICIPANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EVENT_PARTICIPANT_USER_ID + " INTEGER, " +
                COLUMN_EVENT_PARTICIPANT_EVENT_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_EVENT_PARTICIPANT_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USERS_ID + "), " +
                "FOREIGN KEY(" + COLUMN_EVENT_PARTICIPANT_EVENT_ID + ") REFERENCES " + TABLE_EVENT + "(" + COLUMN_EVENT_ID + "))");
    }
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYBOOKS);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTPARTICIPANT);

    onCreate(db);
}

//Insert example data
public void insertExampleData() {
    SQLiteDatabase db = this.getWritableDatabase();

    // Insert data into Books table
    ContentValues booksValues = new ContentValues();
    booksValues.put(COLUMN_BOOKS_TITLE, "The Great Gatsby");
    booksValues.put(COLUMN_BOOKS_AUTHOR, "F. Scott Fitzgerald");
    booksValues.put(COLUMN_BOOKS_GENRE, "Fiction");
    booksValues.put(COLUMN_BOOKS_PUBLISHER, "Scribner");
    booksValues.put(COLUMN_BOOKS_RATING, 4.5);
    booksValues.put(COLUMN_BOOKS_ADDED, 0); // 1 for true, 0 for false
    db.insert(TABLE_BOOKS, null, booksValues);

    // Insert data into Users table
    ContentValues usersValues = new ContentValues();
    usersValues.put(COLUMN_USERS_FULL_NAME, "John Doe");
    usersValues.put(COLUMN_USERS_AGE, 30);
    db.insert(TABLE_USERS, null, usersValues);

    // Insert data into MyBooks table
    ContentValues myBooksValues = new ContentValues();
    myBooksValues.put(COLUMN_MYBOOKS_BOOK_ID, 1); // Reference to the first book inserted
    myBooksValues.put(COLUMN_MYBOOKS_USER_ID, 1); // Reference to the first user inserted
    myBooksValues.put(COLUMN_MYBOOKS_TITLE, "The Great Gatsby");
    myBooksValues.put(COLUMN_MYBOOKS_AUTHOR, "F. Scott Fitzgerald");
    myBooksValues.put(COLUMN_MYBOOKS_PUBLISHER, "Scribner");
    myBooksValues.put(COLUMN_MYBOOKS_GLOBAL_RATING, 4.5);
    myBooksValues.put(COLUMN_MYBOOKS_MY_RATING, 5.0);
    myBooksValues.put(COLUMN_MYBOOKS_LIKES, 10);
    myBooksValues.put(COLUMN_MYBOOKS_DATE_ADDED, "2025-02-03");
    myBooksValues.put(COLUMN_MYBOOKS_REVIEW, "Amazing classic!");
    myBooksValues.put(COLUMN_MYBOOKS_IS_READ, 1);
    myBooksValues.put(COLUMN_MYBOOKS_IS_BORROWED, 0);
    myBooksValues.put(COLUMN_MYBOOKS_IS_MINE_BORROWED, 0);
    myBooksValues.put(COLUMN_MYBOOKS_IS_FAVE, 1);
    myBooksValues.put(COLUMN_MYBOOKS_CURRENTLY_READING, 0);
    myBooksValues.put(COLUMN_MYBOOKS_WANT_TO_READ, 0);
    db.insert(TABLE_MYBOOKS, null, myBooksValues);

    // Insert data into Event table
    ContentValues eventValues = new ContentValues();
    eventValues.put(COLUMN_EVENT_ORGANIZER_ID, 1); // Reference to the first user inserted
    eventValues.put(COLUMN_EVENT_PLACE, "New York City");
    eventValues.put(COLUMN_EVENT_DATE_TIME, "2025-03-15 18:00");
    eventValues.put(COLUMN_EVENT_LAT_START, 40.7128);
    eventValues.put(COLUMN_EVENT_LON_START, -74.0060);
    db.insert(TABLE_EVENT, null, eventValues);

    // Insert data into EventParticipant table
    ContentValues eventParticipantValues = new ContentValues();
    eventParticipantValues.put(COLUMN_EVENT_PARTICIPANT_USER_ID, 1); // Reference to the first user inserted
    eventParticipantValues.put(COLUMN_EVENT_PARTICIPANT_EVENT_ID, 1); // Reference to the first event inserted
    db.insert(TABLE_EVENTPARTICIPANT, null, eventParticipantValues);

    db.close();
    }

    //CRUD BOOKS
    // Create a new book
    public long addBook(String title, String author, String genre, String publisher, float rating, int added) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BOOKS_TITLE, title);
        values.put(COLUMN_BOOKS_AUTHOR, author);
        values.put(COLUMN_BOOKS_GENRE, genre);
        values.put(COLUMN_BOOKS_PUBLISHER, publisher);
        values.put(COLUMN_BOOKS_RATING, rating);
        values.put(COLUMN_BOOKS_ADDED, added);

        long result = db.insert(TABLE_BOOKS, null, values);
        db.close();
        return result;
    }

    // Get a book by ID
    public Books getBookById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKS, null, COLUMN_BOOKS_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int bookId = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKS_ID));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKS_TITLE));
            @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKS_AUTHOR));
            @SuppressLint("Range") String publisher = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKS_PUBLISHER));
            @SuppressLint("Range") float globalRating = cursor.getFloat(cursor.getColumnIndex(COLUMN_BOOKS_RATING));
            @SuppressLint("Range") boolean added = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKS_ADDED)) == 1;
            @SuppressLint("Range") String genre = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKS_GENRE));


            // Create and return the Book object
            Books book = new Books(bookId, title, author, publisher, globalRating, added, genre);
            cursor.close(); // Don't forget to close the cursor
            return book;
        }

        cursor.close(); // Close the cursor if no data is found
        return null; // No data found, return null
    }


    // Update a book
    public int updateBook(int id, String title, String author, String genre, String publisher, float rating, int added) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKS_TITLE, title);
        values.put(COLUMN_BOOKS_AUTHOR, author);
        values.put(COLUMN_BOOKS_GENRE, genre);
        values.put(COLUMN_BOOKS_PUBLISHER, publisher);
        values.put(COLUMN_BOOKS_RATING, rating);
        values.put(COLUMN_BOOKS_ADDED, added);
        int rowsAffected =  db.update(TABLE_BOOKS, values, COLUMN_BOOKS_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    // Delete a book
    public void deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKS, COLUMN_BOOKS_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    //Get book by id

    //CRUD MYBOOKS
    //add a new book
    public long addMyBook(int bookId, int userId, String title, String author, String publisher,
                          float globalRating, float myRating, Integer likes, String dateAdded,
                          String review, Boolean isRead, Boolean isBorrowed, Boolean isMineBorrowed,
                          Boolean isFave, Boolean currentlyReading, Boolean wantToRead) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Set default values for fields if they are null
        values.put(COLUMN_MYBOOKS_BOOK_ID, bookId);
        values.put(COLUMN_MYBOOKS_USER_ID, userId);
        values.put(COLUMN_MYBOOKS_TITLE, title);
        values.put(COLUMN_MYBOOKS_AUTHOR, author);
        values.put(COLUMN_MYBOOKS_PUBLISHER, publisher);
        values.put(COLUMN_MYBOOKS_GLOBAL_RATING, globalRating);
        values.put(COLUMN_MYBOOKS_MY_RATING, myRating);

        // Set default value for 'likes' if it is null
        values.put(COLUMN_MYBOOKS_LIKES, likes != null ? likes : 0);

        // Set default value for 'dateAdded' and 'review' if they are null
        values.put(COLUMN_MYBOOKS_DATE_ADDED, dateAdded != null ? dateAdded : "");
        values.put(COLUMN_MYBOOKS_REVIEW, review != null ? review : "");

        // Set default boolean values (false) for the Boolean fields if they are null
        values.put(COLUMN_MYBOOKS_IS_READ, isRead != null ? isRead : false);
        values.put(COLUMN_MYBOOKS_IS_BORROWED, isBorrowed != null ? isBorrowed : false);
        values.put(COLUMN_MYBOOKS_IS_MINE_BORROWED, isMineBorrowed != null ? isMineBorrowed : false);
        values.put(COLUMN_MYBOOKS_IS_FAVE, isFave != null ? isFave : false);
        values.put(COLUMN_MYBOOKS_CURRENTLY_READING, currentlyReading != null ? currentlyReading : false);
        values.put(COLUMN_MYBOOKS_WANT_TO_READ, wantToRead != null ? wantToRead : false);

        long result = db.insert(TABLE_MYBOOKS, null, values);
        db.close();
        return result;
    }

    // Update Review
    public int updateReview(SQLiteDatabase db, int id, String review) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MYBOOKS_REVIEW, review);
        return db.update(TABLE_MYBOOKS, values, COLUMN_MYBOOKS_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Delete MyBooks entry
    public void deleteMyBook(SQLiteDatabase db, int id) {
        db.delete(TABLE_MYBOOKS, COLUMN_MYBOOKS_ID + " = ?", new String[]{String.valueOf(id)});
    }
    //Get my book by id
    public MyBooks getMyBookById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Specify only the columns you need (e.g., title, author, etc.)
        String[] projection = {
                COLUMN_MYBOOKS_ID,
                COLUMN_MYBOOKS_TITLE,
                COLUMN_MYBOOKS_AUTHOR,
                COLUMN_MYBOOKS_PUBLISHER,
                COLUMN_MYBOOKS_GLOBAL_RATING,
                COLUMN_MYBOOKS_MY_RATING,
                COLUMN_MYBOOKS_LIKES,
                COLUMN_MYBOOKS_DATE_ADDED,
                COLUMN_MYBOOKS_REVIEW,
                COLUMN_MYBOOKS_IS_READ,
                COLUMN_MYBOOKS_IS_BORROWED,
                COLUMN_MYBOOKS_IS_MINE_BORROWED,
                COLUMN_MYBOOKS_IS_FAVE,
                COLUMN_MYBOOKS_CURRENTLY_READING,
                COLUMN_MYBOOKS_WANT_TO_READ
        };

        // Query the MyBooks table with the specified columns
        Cursor cursor = db.query(TABLE_MYBOOKS, projection, COLUMN_MYBOOKS_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int myBookId = cursor.getInt(cursor.getColumnIndex(COLUMN_MYBOOKS_ID));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_MYBOOKS_TITLE));
            @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(COLUMN_MYBOOKS_AUTHOR));
            @SuppressLint("Range") String publisher = cursor.getString(cursor.getColumnIndex(COLUMN_MYBOOKS_PUBLISHER));
            @SuppressLint("Range") float globalRating = cursor.getFloat(cursor.getColumnIndex(COLUMN_MYBOOKS_GLOBAL_RATING));
            @SuppressLint("Range") float myRating = cursor.getFloat(cursor.getColumnIndex(COLUMN_MYBOOKS_MY_RATING));
            @SuppressLint("Range") int likes = cursor.getInt(cursor.getColumnIndex(COLUMN_MYBOOKS_LIKES));
            @SuppressLint("Range") String dateAdded = cursor.getString(cursor.getColumnIndex(COLUMN_MYBOOKS_DATE_ADDED));
            @SuppressLint("Range") String review = cursor.getString(cursor.getColumnIndex(COLUMN_MYBOOKS_REVIEW));

            // Convert integer values to boolean
            @SuppressLint("Range") boolean isRead = cursor.getInt(cursor.getColumnIndex(COLUMN_MYBOOKS_IS_READ)) == 1;
            @SuppressLint("Range") boolean isBorrowed = cursor.getInt(cursor.getColumnIndex(COLUMN_MYBOOKS_IS_BORROWED)) == 1;
            @SuppressLint("Range") boolean isMineBorrowed = cursor.getInt(cursor.getColumnIndex(COLUMN_MYBOOKS_IS_MINE_BORROWED)) == 1;
            @SuppressLint("Range") boolean isFave = cursor.getInt(cursor.getColumnIndex(COLUMN_MYBOOKS_IS_FAVE)) == 1;
            @SuppressLint("Range") boolean currentlyReading = cursor.getInt(cursor.getColumnIndex(COLUMN_MYBOOKS_CURRENTLY_READING)) == 1;
            @SuppressLint("Range") boolean wantToRead = cursor.getInt(cursor.getColumnIndex(COLUMN_MYBOOKS_WANT_TO_READ)) == 1;

            // Create the MyBooks object with only necessary columns
            MyBooks myBook = new MyBooks(myBookId, title, author, publisher, globalRating, myRating, likes, dateAdded, review, isRead, isBorrowed, isMineBorrowed, isFave, currentlyReading, wantToRead);

            cursor.close(); // Don't forget to close the cursor
            return myBook;
        }

        cursor.close(); // Close the cursor if no data is found
        return null; // No data found, return null
    }


    //CRUD User
    // Create a new user

    public long addUser(String userId, String fullName, Integer age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERS_USER_ID, userId);
        values.put(COLUMN_USERS_FULL_NAME, fullName);
        values.put(COLUMN_USERS_AGE, age);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result;
    }

    // Update a user
    public int updateUser(SQLiteDatabase db, int id, String fullName, int age) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERS_FULL_NAME, fullName);
        values.put(COLUMN_USERS_AGE, age);
        return db.update(TABLE_USERS, values, COLUMN_USERS_ID + " = ?", new String[]{String.valueOf(id)});
    }
    //get a user by firebase id
    public User getUserByFirebaseUid(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERS_USER_ID + " = ?", new String[]{userId});

        User user = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USERS_ID));
            String fullName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERS_FULL_NAME));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USERS_AGE));

            user = new User(id, fullName, age);
        }
        cursor.close();
        db.close();
        return user;
    }
    public Integer getUserIdByFirebaseUid(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USERS_ID + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USERS_USER_ID + " = ?", new String[]{userId});

        Integer id = null;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USERS_ID));
        }
        cursor.close();
        db.close();
        return id; // Returns user ID or null if not found
    }


    // Delete a user
    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, COLUMN_USERS_ID + " = ?", new String[]{String.valueOf(id)});
    }
    public int deleteUserF(String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_USERS, COLUMN_USERS_USER_ID + " = ?", new String[]{userId});
        db.close();
        return rowsDeleted;
    }

    //Get all users
    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        if (cursor.moveToFirst()) {
            do {
                users.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERS_FULL_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }


    //CRUD Event
    // Create a new event
    public long addEvent(int organizerId, String place, String dateTime, double latStart, double lonStart) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_ORGANIZER_ID, organizerId);
        values.put(COLUMN_EVENT_PLACE, place);
        values.put(COLUMN_EVENT_DATE_TIME, dateTime);
        values.put(COLUMN_EVENT_LAT_START, latStart);
        values.put(COLUMN_EVENT_LON_START, lonStart);
        long result = db.insert(TABLE_EVENT, null, values);
        db.close();
        return result;
    }

    // Update event
    public int updateEvent(SQLiteDatabase db, int id, String place, String dateTime, double latStart, double lonStart) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_PLACE, place);
        values.put(COLUMN_EVENT_DATE_TIME, dateTime);
        values.put(COLUMN_EVENT_LAT_START, latStart);
        values.put(COLUMN_EVENT_LON_START, lonStart);
        return db.update(TABLE_EVENT, values, COLUMN_EVENT_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Delete event
    public void deleteEvent(SQLiteDatabase db, int id) {
        db.delete(TABLE_EVENT, COLUMN_EVENT_ID + " = ?", new String[]{String.valueOf(id)});
    }

    //CRUD eventparticipant
    // Add participant to event
    public long addEventParticipant( int userId, int eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_PARTICIPANT_USER_ID, userId);
        values.put(COLUMN_EVENT_PARTICIPANT_EVENT_ID, eventId);
        long result = db.insert(TABLE_EVENTPARTICIPANT, null, values);
        return result;
    }

    // Delete event participant
    public void deleteEventParticipant(SQLiteDatabase db, int userId, int eventId) {
        db.delete(TABLE_EVENTPARTICIPANT, COLUMN_EVENT_PARTICIPANT_USER_ID + " = ? AND " + COLUMN_EVENT_PARTICIPANT_EVENT_ID + " = ?", new String[]{String.valueOf(userId), String.valueOf(eventId)});
    }

    public void insertSampleBooks() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Sample books
        ContentValues book1 = new ContentValues();
        book1.put(COLUMN_BOOKS_TITLE, "Бели ноќи");
        book1.put(COLUMN_BOOKS_AUTHOR, "Фјодор М. Достоевски");
        book1.put(COLUMN_BOOKS_GENRE, "Класици");
        book1.put(COLUMN_BOOKS_PUBLISHER, "Антолог");
        book1.put(COLUMN_BOOKS_RATING, 5);
        book1.put(COLUMN_BOOKS_ADDED, 0); // Not added to MyBooks yet

        ContentValues book2 = new ContentValues();
        book2.put(COLUMN_BOOKS_TITLE, "Град во облаците");
        book2.put(COLUMN_BOOKS_AUTHOR, "Ентони Дор");
        book2.put(COLUMN_BOOKS_GENRE, "Фикција");
        book2.put(COLUMN_BOOKS_PUBLISHER, "Матица");
        book2.put(COLUMN_BOOKS_RATING, 5);
        book2.put(COLUMN_BOOKS_ADDED, 0);

        ContentValues book3 = new ContentValues();
        book3.put(COLUMN_BOOKS_TITLE, "Физика на тагата");
        book3.put(COLUMN_BOOKS_AUTHOR, "Георги Господинов");
        book3.put(COLUMN_BOOKS_GENRE, "Фикција");
        book3.put(COLUMN_BOOKS_PUBLISHER, "Или Или");
        book3.put(COLUMN_BOOKS_RATING, 5);
        book3.put(COLUMN_BOOKS_ADDED, 0);

        // Insert books only if they don't already exist
        db.insert("Books", null, book1);
        db.insert("Books", null, book2);
        db.insert("Books", null, book3);

        db.close();
    }

    public List<Books> getAllBooks() {
        List<Books> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM books", null);

        if (cursor.moveToFirst()) {
            do {
                Books book = new Books();
                book.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOKS_ID)));
                book.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKS_TITLE)));
                book.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKS_AUTHOR)));
                book.setGenre(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKS_GENRE)));
                book.setPublisher(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKS_PUBLISHER)));
                book.setRating((float) cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOKS_RATING)));
                book.setAdded(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOKS_ADDED)) == 1); // Convert int to boolean

                bookList.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookList;
    }
    public boolean isBookAlreadyAdded(int bookId, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_MYBOOKS + " WHERE " + COLUMN_MYBOOKS_BOOK_ID + " = ? AND " + COLUMN_MYBOOKS_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(bookId), String.valueOf(userId)});

        boolean exists = cursor.getCount() > 0; // If a matching record is found
        cursor.close();
        db.close();

        return exists;
    }

    public boolean isBookAlreadyFave(int bookId, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to check if the book is marked as a favorite for a specific user
        String query = "SELECT * FROM " + TABLE_MYBOOKS +
                " WHERE " + COLUMN_MYBOOKS_BOOK_ID + " = ? " +
                " AND " + COLUMN_MYBOOKS_USER_ID + " = ? " +
                " AND " + COLUMN_MYBOOKS_IS_FAVE + " = 1"; // Check if it's marked as favorite

        // Execute the query
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(bookId), String.valueOf(userId)});

        // Check if any matching records exist
        boolean isFave = cursor.getCount() > 0; // If a matching favorite record is found
        cursor.close();  // Always close the cursor
        db.close();      // Close the database when done

        return isFave;  // Return whether the book is marked as favorite
    }


    public List<MyBooks> getAllMyBooks() {
        List<MyBooks> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM mybooks", null);

        if (cursor.moveToFirst()) {
            do {
                MyBooks book = new MyBooks();
                book.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_ID)));
                book.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_TITLE)));
                book.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_AUTHOR)));
                book.setPublisher(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_PUBLISHER)));
                book.setMyRating(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_MY_RATING)));
                book.setReview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_REVIEW)));

                // Setting boolean fields
                book.setRead(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_READ)) == 1);
                book.setBorrowed(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_BORROWED)) == 1);
                book.setMineBorrowes(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_MINE_BORROWED)) == 1);
                book.setFave(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_FAVE)) == 1);
                book.setCurrentlyReading(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_CURRENTLY_READING)) == 1);
                book.setWantToRead(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_WANT_TO_READ)) == 1);

                // Adding book to the list
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookList;
    }

    public void setMyBookFave(int userId, int bookId, boolean isFave) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MYBOOKS_IS_FAVE, isFave ? 1 : 0); // Convert boolean to integer

        db.update("mybooks", values,
                COLUMN_MYBOOKS_ID + " = ? AND " + COLUMN_MYBOOKS_USER_ID + " = ?",
                new String[]{String.valueOf(bookId), String.valueOf(userId)});
        db.close();
    }


    public List<MyBooks> getAllMyBooksForUser(int userId) {
        List<MyBooks> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to fetch books only for the logged-in user
        Cursor cursor = db.rawQuery("SELECT * FROM mybooks WHERE " + COLUMN_MYBOOKS_USER_ID + " = ?",
                new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                MyBooks book = new MyBooks();
                book.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_ID)));
                book.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_TITLE)));
                book.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_AUTHOR)));
                book.setPublisher(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_PUBLISHER)));
                book.setMyRating(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_MY_RATING)));
                book.setReview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_REVIEW)));

                // Setting boolean fields
                book.setRead(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_READ)) == 1);
                book.setBorrowed(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_BORROWED)) == 1);
                book.setMineBorrowes(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_MINE_BORROWED)) == 1);
                book.setFave(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_FAVE)) == 1);
                book.setCurrentlyReading(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_CURRENTLY_READING)) == 1);
                book.setWantToRead(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_WANT_TO_READ)) == 1);

                // Adding book to the list
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookList;
    }

    public MyBooks getBookForUser(int userId, int bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        MyBooks book = null;

        // Query to fetch a specific book for the given user
        Cursor cursor = db.rawQuery("SELECT * FROM mybooks WHERE " + COLUMN_MYBOOKS_USER_ID + " = ? AND " + COLUMN_MYBOOKS_ID + " = ?",
                new String[]{String.valueOf(userId), String.valueOf(bookId)});

        if (cursor.moveToFirst()) {
            book = new MyBooks();
            book.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_ID)));
            book.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_TITLE)));
            book.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_AUTHOR)));
            book.setPublisher(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_PUBLISHER)));
            book.setMyRating(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_MY_RATING)));
            book.setReview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_REVIEW)));

            // Setting boolean fields
            book.setRead(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_READ)) == 1);
            book.setBorrowed(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_BORROWED)) == 1);
            book.setMineBorrowes(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_MINE_BORROWED)) == 1);
            book.setFave(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_FAVE)) == 1);
            book.setCurrentlyReading(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_CURRENTLY_READING)) == 1);
            book.setWantToRead(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_WANT_TO_READ)) == 1);
        }

        cursor.close();
        db.close();
        return book;
    }
    public void updateBookReviewAndRating(int userId, int bookId, String review, float rating, String currentDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a ContentValues object to store the new values
        ContentValues values = new ContentValues();
        values.put(COLUMN_MYBOOKS_REVIEW, review);  // Update the review
        values.put(COLUMN_MYBOOKS_MY_RATING, rating);  // Update the rating
        values.put(COLUMN_MYBOOKS_DATE_ADDED, currentDate);  // Update the date

        // Update the row in the database where the book ID and user ID match
        db.update("mybooks", values,
                COLUMN_MYBOOKS_ID + " = ? AND " + COLUMN_MYBOOKS_USER_ID + " = ?",
                new String[]{String.valueOf(bookId), String.valueOf(userId)});

        db.close();
    }


    public List<MyBooks> getAllFavoriteBooksForUser(int userId) {
        List<MyBooks> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to fetch books only for the logged-in user where isFave = 1
        Cursor cursor = db.rawQuery("SELECT * FROM mybooks WHERE " + COLUMN_MYBOOKS_USER_ID + " = ? AND " + COLUMN_MYBOOKS_IS_FAVE + " = ?",
                new String[]{String.valueOf(userId), "1"}); // "1" represents true in SQLite

        if (cursor.moveToFirst()) {
            do {
                MyBooks book = new MyBooks();
                book.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_ID)));
                book.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_TITLE)));
                book.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_AUTHOR)));
                book.setPublisher(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_PUBLISHER)));
                book.setMyRating(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_MY_RATING)));
                book.setReview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_REVIEW)));

                // Setting boolean fields
                book.setRead(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_READ)) == 1);
                book.setBorrowed(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_BORROWED)) == 1);
                book.setMineBorrowes(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_MINE_BORROWED)) == 1);
                book.setFave(true); // Since we're filtering for favorite books, this is always true
                book.setCurrentlyReading(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_CURRENTLY_READING)) == 1);
                book.setWantToRead(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_WANT_TO_READ)) == 1);

                // Adding book to the list
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookList;
    }
    public List<MyBooks> getAllBorrowedBooksForUser(int userId) {
        List<MyBooks> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to fetch books only for the logged-in user where isFave = 1
        Cursor cursor = db.rawQuery("SELECT * FROM mybooks WHERE " + COLUMN_MYBOOKS_USER_ID + " = ? AND " + COLUMN_MYBOOKS_IS_FAVE + " = ?",
                new String[]{String.valueOf(userId), "1"}); // "1" represents true in SQLite

        if (cursor.moveToFirst()) {
            do {
                MyBooks book = new MyBooks();
                book.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_ID)));
                book.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_TITLE)));
                book.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_AUTHOR)));
                book.setPublisher(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_PUBLISHER)));
                book.setMyRating(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_MY_RATING)));
                book.setReview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_REVIEW)));

                // Setting boolean fields
                book.setRead(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_READ)) == 1);
                book.setBorrowed(true);
                book.setMineBorrowes(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_MINE_BORROWED)) == 1);
                book.setFave(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_FAVE)) == 1);
                book.setCurrentlyReading(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_CURRENTLY_READING)) == 1);
                book.setWantToRead(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_WANT_TO_READ)) == 1);

                // Adding book to the list
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookList;
    }
    public List<MyBooks> getAllMyBorrowedBooksForUser(int userId) {
        List<MyBooks> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to fetch books only for the logged-in user where isFave = 1
        Cursor cursor = db.rawQuery("SELECT * FROM mybooks WHERE " + COLUMN_MYBOOKS_USER_ID + " = ? AND " + COLUMN_MYBOOKS_IS_FAVE + " = ?",
                new String[]{String.valueOf(userId), "1"}); // "1" represents true in SQLite

        if (cursor.moveToFirst()) {
            do {
                MyBooks book = new MyBooks();
                book.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_ID)));
                book.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_TITLE)));
                book.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_AUTHOR)));
                book.setPublisher(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_PUBLISHER)));
                book.setMyRating(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_MY_RATING)));
                book.setReview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_REVIEW)));

                // Setting boolean fields
                book.setRead(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_READ)) == 1);
                book.setBorrowed(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_BORROWED)) == 1);
                book.setMineBorrowes(true);
                book.setFave(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_IS_FAVE)) == 1);
                book.setCurrentlyReading(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_CURRENTLY_READING)) == 1);
                book.setWantToRead(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MYBOOKS_WANT_TO_READ)) == 1);

                // Adding book to the list
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookList;
    }
    public String getDateAdded(int userId, int bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String dateAdded = null;

        // Query to select the date based on userId and bookId
        String query = "SELECT " + COLUMN_MYBOOKS_DATE_ADDED +
                " FROM mybooks WHERE " + COLUMN_MYBOOKS_ID + " = ? AND " + COLUMN_MYBOOKS_USER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(bookId), String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            dateAdded = cursor.getString(0);  // Get the first column value (date)
        }

        cursor.close();
        db.close();
        return dateAdded;  // Returns null if not found
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_EVENT;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EVENT_ID));
                String place = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_PLACE));
                String dateTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_DATE_TIME));
                double latStart = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_EVENT_LAT_START));
                double lonStart = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_EVENT_LON_START));
                LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
                Event event = new Event(id, place, localDateTime, latStart, lonStart);
                eventList.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return eventList;
    }
    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERS_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            String fullName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERS_FULL_NAME));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USERS_AGE));

            user = new User(userId, fullName, age);
        }

        cursor.close();
        db.close();
        return user;
    }


    public boolean isUserAlreadyParticipant(int eventId, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to check if the user is already a participant in the event
        String query = "SELECT * FROM " + TABLE_EVENTPARTICIPANT +
                " WHERE " + COLUMN_EVENT_PARTICIPANT_EVENT_ID + " = ? " +
                " AND " + COLUMN_EVENT_PARTICIPANT_USER_ID + " = ?";

        // Execute the query
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(eventId), String.valueOf(userId)});

        // Check if any matching records exist
        boolean isParticipant = cursor.getCount() > 0; // If a matching record is found
        cursor.close();  // Always close the cursor
        db.close();      // Close the database when done

        return isParticipant;  // Return whether the user is already a participant
    }

    public void markEvent(int userId, int eventId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Prepare ContentValues for inserting data
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_PARTICIPANT_USER_ID, userId);
        values.put(COLUMN_EVENT_PARTICIPANT_EVENT_ID, eventId);

        // Insert the data into the eventparticipant table
        db.insert(TABLE_EVENTPARTICIPANT, null, values);

        // Close the database
        db.close();
    }

}
