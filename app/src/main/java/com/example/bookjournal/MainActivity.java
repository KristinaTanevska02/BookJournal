package com.example.bookjournal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookjournal.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button button;
    FirebaseUser firebaseUser;
    DatabaseHelper dbHelper;
    Integer userId;
    Button btn_explore_books;
    Button btn_explore_events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        btn_explore_books = findViewById(R.id.btn_explore_books);
        btn_explore_events = findViewById(R.id.btn_explore_events);

        dbHelper = new DatabaseHelper(getApplicationContext());
        //INSERT EXAMPLE EVENT AND BOOKS

        Toolbar toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            Toast.makeText(getApplicationContext(), "User is not authenticated.", Toast.LENGTH_SHORT).show();
            return; // Exit if user is not authenticated
        }
        String userIdF = firebaseUser.getUid();
        userId = dbHelper.getUserIdByFirebaseUid(userIdF);

        //dbHelper.insertSampleBooks();
        //dbHelper.addEvent(1,"Saem Skopje","2025-10-10T00:00:00",41.2, 41.2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        btn_explore_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BooksActivity.class);
                intent.putExtra("KEY_INT", userId);
                startActivity(intent);
                finish();
            }
        });
        btn_explore_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Events.class);
                intent.putExtra("KEY_INT", userId);
                startActivity(intent);
                finish();
            }
        });

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
            booksIntent.putExtra("KEY_INT", userId);
            startActivity(booksIntent);
            return true;
        } else if (id == R.id.user) {
            Intent userIntent = new Intent(this, UserProfileActivity.class);
            startActivity(userIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}