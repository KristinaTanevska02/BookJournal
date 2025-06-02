package com.example.bookjournal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookjournal.Models.Books;
import com.example.bookjournal.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
    Context context;
    private List<Books> items;
    private List<Books> filtered;
    private DatabaseHelper dbHelper;
    private Integer loggedUserId;

    public MyAdapter(Context context, List<Books> items, Integer loggedUserId ) {
        this.context = context;
        this.items = items;
        this.loggedUserId = loggedUserId;
        this.filtered = new ArrayList<>(items);
        this.dbHelper = new DatabaseHelper(context);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Books book = filtered.get(position);

        // Set text for title, author, publisher, and rating
        holder.titleAuthor.setText(book.getTitle() + " - " + book.getAuthor());
        holder.publisher.setText(book.getPublisher());
        holder.rating.setText(String.valueOf(book.getRating()));


        // Check if the book is already added by the user before clicking the button
        boolean isBookAlreadyAdded = dbHelper.isBookAlreadyAdded(book.getId(), loggedUserId);
        if (isBookAlreadyAdded) {
            holder.btnChooseBook.setVisibility(View.GONE); // Hide the button if already added
        } else {
            // Set onClickListener for the "Choose" button
            holder.btnChooseBook.setOnClickListener(v -> {
                // Add book to the "MyBooks" table if it's not already added
                long result = dbHelper.addMyBook(
                        book.getId(),
                        loggedUserId,
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPublisher(),
                        book.getRating(),
                        0, // My rating (0 if no rating yet)
                        0, // Likes (default 0)
                        "", // Date added (empty or set a real date)
                        "", // Review (empty if no review)
                        false, // Is read (false by default)
                        false, // Is borrowed (false by default)
                        false, // Is mine borrowed (false by default)
                        false, // Is favorite (false by default)
                        false, // Currently reading (false by default)
                        false  // Want to read (false by default)
                );

                if (result > 0) {
                    Toast.makeText(context, "Book added to My Books!", Toast.LENGTH_SHORT).show();
                    holder.btnChooseBook.setVisibility(View.GONE); // Hide button after adding
                } else {
                    Toast.makeText(context, "Failed to add book.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return filtered.size();
    }
    public void filter(String text) {
        filtered.clear(); // Clear the list before filtering

        if (text.isEmpty()) {
            filtered.addAll(items); // Show all books when search is empty
        } else {


            text = text.toLowerCase();
            for (Books book : items) {
                if (book.getTitle().toLowerCase().contains(text)) {
                    filtered.add(book);
                }
            }
        }
        notifyDataSetChanged(); // Refresh the RecyclerView
    }
}
