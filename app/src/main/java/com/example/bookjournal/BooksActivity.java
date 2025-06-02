package com.example.bookjournal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookjournal.Models.Books;
import com.example.bookjournal.Models.User;
import com.google.android.material.badge.BadgeUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Books> items;
    private Integer userId;
    private LinearLayout addBook;
    private EditText searchBooks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_books);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Toolbar toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("BookJournal");
        toolbar.setOnClickListener(v -> {
            Intent intent = new Intent(BooksActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: Closes the current activity
        });

        items = new ArrayList<Books>();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        for (Books book : dbHelper.getAllBooks()){
            items.add(book);
        }
        userId = getIntent().getIntExtra("KEY_INT", -1);

        if (userId == -1) {
            Toast.makeText(this, "User ID not received!", Toast.LENGTH_SHORT).show();
        }

        addBook = findViewById(R.id.addBookForm);
        if(userId != 1){
            addBook.setVisibility(View.GONE);
        }
        EditText titleInput = findViewById(R.id.titleInput);
        EditText authorInput = findViewById(R.id.authorInput);
        EditText genreInput = findViewById(R.id.genreInput);
        EditText publisherInput = findViewById(R.id.publisherInput);
        Button addBook = findViewById(R.id.addBook);

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleInput.getText().toString().trim();
                String author = authorInput.getText().toString().trim();
                String genre = genreInput.getText().toString().trim();
                String publisher = publisherInput.getText().toString().trim();

                if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || publisher.isEmpty()) {
                    Toast.makeText(BooksActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a new book and add it to the database
                Books newBook = new Books(title, author, genre, publisher, 0, 0);
                dbHelper.addBook(title, author, genre, publisher, 0, 0);

                // Add the new book directly to the items list
                items.add(newBook);
                titleInput.setText("");
                authorInput.setText("");
                genreInput.setText("");
                publisherInput.setText("");


                // Notify the adapter that a new item has been added
                adapter.notifyItemInserted(items.size() - 1);

                // Scroll to the last item in the RecyclerView
                recyclerView.scrollToPosition(items.size() - 1);

                Toast.makeText(BooksActivity.this, "Book added!", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();  // Get the current activity's Intent
                finish();  // Finish the current activity
                startActivity(intent);
            }
        });



        searchBooks = findViewById(R.id.search_books);
        searchBooks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString()); // Call filter method
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter = new MyAdapter(getApplicationContext(), items, userId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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
            return true;
        } else if (id == R.id.user) {
            Intent userIntent = new Intent(this, UserProfileActivity.class);
            startActivity(userIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}