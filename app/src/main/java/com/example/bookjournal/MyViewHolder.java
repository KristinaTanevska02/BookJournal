package com.example.bookjournal;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView titleAuthor;
    TextView publisher;
    TextView rating;
    Button btnChooseBook;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        titleAuthor = itemView.findViewById(R.id.titleAuthor);
        publisher = itemView.findViewById(R.id.publisher);
        rating = itemView.findViewById(R.id.rating);
        btnChooseBook = itemView.findViewById(R.id.btnChooseBook);

    }
}
