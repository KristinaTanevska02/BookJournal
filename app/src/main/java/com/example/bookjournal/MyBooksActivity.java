package com.example.bookjournal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.example.bookjournal.Models.MyBooks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyBooksActivity extends AppCompatActivity implements RecyclerViewInterface {
    private RecyclerView recyclerView;
    private MyAdapter2 adapter;
    private List<MyBooks> items;
    private Integer userId;
    FirebaseUser firebaseUser;
    DatabaseHelper dbHelper;
    private TextView numbooks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_books);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        dbHelper = new DatabaseHelper(getApplicationContext());
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            Toast.makeText(getApplicationContext(), "User is not authenticated.", Toast.LENGTH_SHORT).show();
            return; // Exit if user is not authenticated
        }
        String userIdF = firebaseUser.getUid();
        userId = dbHelper.getUserIdByFirebaseUid(userIdF);
        items = new ArrayList<MyBooks>();
        for (MyBooks book : dbHelper.getAllMyBooksForUser(userId)){
            items.add(book);
        }
        numbooks = findViewById(R.id.numbooks);
        numbooks.setText("Мои книги: "+items.size());

        recyclerView = findViewById(R.id.recyclerview2);
        recyclerView.setHasFixedSize(true);
        adapter = new MyAdapter2(getApplicationContext(), items, userId, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Enable clicking on the toolbar title
        toolbar.setTitle("BookJournal");
        toolbar.setOnClickListener(v -> {
            Intent intent = new Intent(MyBooksActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: Closes the current activity
        });

    }


    @Override
    public void onItemClick(int position) {
       MyBooks book = items.get(position);
       Intent intent = new Intent(getApplicationContext(), BooksDetailsActivity2.class);
       intent.putExtra("bookid", book.getId());
       intent.putExtra("userid", userId);
       intent.putExtra("title", book.getTitle());
       intent.putExtra("author", book.getAuthor());
       intent.putExtra("review", book.getReview());
       intent.putExtra("rating", book.getMyRating());
       startActivity(intent);
       finish();
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