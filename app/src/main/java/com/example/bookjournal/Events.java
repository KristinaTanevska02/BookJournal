package com.example.bookjournal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import com.example.bookjournal.Models.Event;

import java.util.ArrayList;
import java.util.List;

public class Events extends AppCompatActivity {
private RecyclerView recyclerView;
private EventAdapter adapter;
private List<Event> events;
private Integer userId;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_events);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle("BookJournal");
        toolbar.setOnClickListener(v -> {
            Intent intent = new Intent(Events.this, MainActivity.class);
            startActivity(intent);
        });
        userId = 2;
        //events = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        events = dbHelper.getAllEvents();


        Button addevent = findViewById(R.id.addevent);
        if(userId!=1){
            addevent.setVisibility(View.GONE);}
        else{
            addevent.setVisibility(View.VISIBLE);
            addevent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AddEvent.class);
                    startActivity(intent);
                    finish();
                }
            });
        }



        recyclerView = findViewById(R.id.recyclerviewevent);
        recyclerView.setHasFixedSize(true);
        adapter = new EventAdapter(Events.this, events);
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