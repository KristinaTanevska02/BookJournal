package com.example.bookjournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookjournal.Models.Event;
import com.example.bookjournal.Models.MyBooks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    Button seeMyBooks;
    private ListView listView, listView2, listView3, listView4, listViewEvents;
    private ArrayList<String> booksList,booksList2,booksList3, bookList4,eventList;
    FirebaseUser firebaseUser;
    DatabaseHelper dbHelper;
    Integer userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("BookJournal");
        toolbar.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: Closes the current activity
        });
        seeMyBooks = findViewById(R.id.seeMyBooks);
        listView = findViewById(R.id.listView);
        listView4 = findViewById(R.id.listView4);
//        listViewEvents = findViewById(R.id.listViewEvents);
//        listView2 = findViewById(R.id.listView2);
//        listView3 = findViewById(R.id.listView3);

        dbHelper = new DatabaseHelper(getApplicationContext());
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            Toast.makeText(getApplicationContext(), "User is not authenticated.", Toast.LENGTH_SHORT).show();
            return; // Exit if user is not authenticated
        }
        String userIdF = firebaseUser.getUid();
    userId = dbHelper.getUserIdByFirebaseUid(userIdF);
    booksList = new ArrayList<>();
        bookList4 = new ArrayList<>();
//        eventList = new ArrayList<>();
//        booksList2 = new ArrayList<>();
//        booksList3 = new ArrayList<>();


        for (MyBooks book : dbHelper.getAllFavoriteBooksForUser(userId)){
            booksList.add(book.getTitle()+" - "+book.getAuthor());
        }
        for (MyBooks book : dbHelper.getAllMyBooksForUser(userId)){
            if (book.getReview() != null && !book.getReview().trim().isEmpty()) {
                bookList4.add(book.getTitle() + " \n " + book.getReview());
            }
        }
//        for(Event tinyevent : dbHelper.getAllEvents()){
//            eventList.add("Саем на книга: "+tinyevent.getPlace()+"\n");
//        }
//        for (MyBooks book : dbHelper.getAllBorrowedBooksForUser(userId)){
//            booksList2.add(book.getTitle()+" - "+book.getAuthor());
//        }
//        for (MyBooks book : dbHelper.getAllMyBorrowedBooksForUser(userId)){
//            booksList3.add(book.getTitle()+" - "+book.getAuthor());
//        }
        seeMyBooks.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, MyBooksActivity.class);
            startActivity(intent);
            finish();
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, booksList
        );
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, bookList4
        );
//        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(
//                this, android.R.layout.simple_list_item_1, eventList
//        );


        // Set Adapter to ListView
        listView.setAdapter(adapter);
        listView4.setAdapter(adapter4);
//        listViewEvents.setAdapter(adapter5);

//        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
//                this, android.R.layout.simple_list_item_1, booksList2
//        );
//
//        // Set Adapter to ListView
//        listView.setAdapter(adapter2);
//        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(
//                this, android.R.layout.simple_list_item_1, booksList3
//        );
//
//        // Set Adapter to ListView
//        listView.setAdapter(adapter3);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId(); // Store the item ID in a variable

        if (id == R.id.books) {
            Intent booksIntent = new Intent(this, BooksActivity.class);
            startActivity(booksIntent);
            finish();
            return true;
        } else if (id == R.id.user) {
            Intent userIntent = new Intent(this, UserProfileActivity.class);
            startActivity(userIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}